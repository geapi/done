package io.pivotal.done.tododatajdbc

import io.pivotal.done.todoapi.Todo
import io.pivotal.done.todoapi.TodoProvider
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.util.*

class RedisTodoProvider : TodoProvider {

    private val hashOps: HashOperations<String, String, Todo>
    private val KEY = "TODO"

    constructor(redisConnectionFactory: JedisConnectionFactory) {
        val redisTemplate = RedisTemplate<String, Todo>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.afterPropertiesSet()

        hashOps = redisTemplate.opsForHash<String, Todo>()
    }

    override fun create(todo: Todo): Todo {
        val id = UUID.randomUUID().toString()
        val newTodo = todo.copy(id = id)

        hashOps.put(KEY, id, newTodo)

        return newTodo
    }

    override fun list(): List<Todo> = hashOps.values(KEY)
}