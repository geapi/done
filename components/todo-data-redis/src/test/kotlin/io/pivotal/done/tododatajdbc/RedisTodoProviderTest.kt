package io.pivotal.done.tododatajdbc

import io.pivotal.done.redistesting.RedisTest
import io.pivotal.done.todoapi.Todo
import org.assertj.core.api.Assertions.assertThat
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

class RedisTodoProviderTest : RedisTest({
    val provider = RedisTodoProvider(jedisConnectionFactory)
    val redisTemplate = RedisTemplate<String, Todo>()

    before {
        redisTemplate.connectionFactory = jedisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.afterPropertiesSet()

        redisTemplate.delete("TODO")
    }

    describe("create") {
        test("returns correct object") {
            val returnedTodo = provider.create(Todo(name = "walk the dog"))


            assertThat(returnedTodo.name).isEqualTo("walk the dog")
            assertThat(returnedTodo.id).isNotNull()
        }

        test("saves to database") {
            val returnedTodo = provider.create(Todo(name = "walk the dog"))


            val id = returnedTodo.id

            val createdTodo = redisTemplate.opsForHash<String, Todo>().get("TODO", id)

            assertThat(createdTodo).isEqualTo(returnedTodo)
        }
    }

    describe("getList") {
        test {
            val firstTodo = provider.create(Todo(name = "walk the dog"))
            val secondTodo = provider.create(Todo(name = "run the dog"))


            val todoList = provider.list()


            assertThat(todoList.size).isEqualTo(2)
            assertThat(todoList).contains(firstTodo)
            assertThat(todoList).contains(secondTodo)
        }
    }
})
