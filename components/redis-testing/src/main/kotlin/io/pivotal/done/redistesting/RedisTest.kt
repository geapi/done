package io.pivotal.done.redistesting

import io.damo.aspen.Test
import io.damo.aspen.spring.SpringTestTreeRunner
import io.damo.aspen.spring.inject
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import java.util.*

@RunWith(SpringTestTreeRunner::class)
@SpringBootTest(classes = arrayOf(RedisTestConfiguration::class))
open class RedisTest : Test {
    lateinit var jedisConnectionFactory: JedisConnectionFactory

    private val body: RedisTest.() -> Unit

    constructor(body: RedisTest.() -> Unit) : super({}) {
        this.body = body
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    override fun readTestBody() {
        jedisConnectionFactory = inject(JedisConnectionFactory::class)

        this.body.invoke(this)

        super.readTestBody()
    }
}