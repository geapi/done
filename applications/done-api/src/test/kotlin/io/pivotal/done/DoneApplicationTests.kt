package io.pivotal.done

import io.damo.aspen.spring.inject
import io.pivotal.done.redistesting.RedisTest
import io.pivotal.done.todoapi.Todo
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DoneApplicationTests : RedisTest({
    val redisTemplate = RedisTemplate<String, Todo>()

    before {
        redisTemplate.connectionFactory = jedisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.afterPropertiesSet()

        redisTemplate.delete("TODO")
    }

    val testRestTemplate: TestRestTemplate = inject("testRestTemplate")

    test("creating a todo") {
        val todoToCreate = Todo(name = "walk the dog")


        val response = testRestTemplate.exchange("/todos", POST, HttpEntity(todoToCreate), Todo::class.java)


        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(response.body.name).isEqualTo("walk the dog")
    }

    test("listing todos") {
        val firstExpectedTodo = Todo(name = "walk the dog")
        val secondExpectedTodo = Todo(name = "feed the dog")

        testRestTemplate.exchange("/todos", POST, HttpEntity(firstExpectedTodo), Todo::class.java)
        testRestTemplate.exchange("/todos", POST, HttpEntity(secondExpectedTodo), Todo::class.java)


        val response = testRestTemplate.exchange("/todos", GET, null, object : ParameterizedTypeReference<List<Todo>>(){})


        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        val returnedTodos = response.body

        val todoNames = returnedTodos.map(Todo::name)
        assertThat(todoNames.size).isEqualTo(2)
        assertThat(todoNames).contains(firstExpectedTodo.name)
        assertThat(todoNames).contains(secondExpectedTodo.name)
    }
})
