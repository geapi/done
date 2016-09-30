package io.pivotal.done

import io.damo.aspen.Test
import io.damo.aspen.spring.SpringTestTreeRunner
import io.damo.aspen.spring.inject
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod.POST

import org.springframework.http.HttpStatus

import org.assertj.core.api.Assertions.assertThat

@RunWith(SpringTestTreeRunner::class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DoneApplicationTests : Test({

    val testRestTemplate: TestRestTemplate = inject("testRestTemplate")

    test("creating a todo") {
        val expectedTodo = Todo("walk the dog")

        val response = testRestTemplate.exchange("/todo", POST, HttpEntity(expectedTodo), Todo::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(response.body).isEqualTo(expectedTodo)
    }
})
