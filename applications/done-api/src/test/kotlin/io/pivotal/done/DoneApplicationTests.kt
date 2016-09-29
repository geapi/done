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

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.springframework.http.HttpStatus

@RunWith(SpringTestTreeRunner::class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DoneApplicationTests : Test({

    val testRestTemplate: TestRestTemplate = inject("testRestTemplate")

    describe("creating a todo") {
        test {
            val response = testRestTemplate.exchange("/todo", POST, HttpEntity(Todo("walk the dog")), Todo::class.java)

            assertThat(response.statusCode, equalTo(HttpStatus.CREATED))
        }
    }

})
