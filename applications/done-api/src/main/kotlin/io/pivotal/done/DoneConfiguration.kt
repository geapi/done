package io.pivotal.done

import io.pivotal.done.todoapi.TodoProvider
import io.pivotal.done.tododatajdbc.RedisTodoProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory

@Configuration
open class DoneConfiguration {
    @Bean
    open fun jedisConnectionFactory() = JedisConnectionFactory()

    @Bean
    open fun todoProvider(jedisConnectionFactory: JedisConnectionFactory):TodoProvider
        = RedisTodoProvider(jedisConnectionFactory)
}