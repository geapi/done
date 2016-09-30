package io.pivotal.done.redistesting

import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Suppress("SpringFacetCode")
@Configuration
@Import(RedisAutoConfiguration::class)
open class RedisTestConfiguration {

}