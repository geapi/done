package io.pivotal.done.todoapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/todos")
class TodoController @Autowired constructor(val todoProvider: TodoProvider) {

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun create(@RequestBody todo: Todo)
            = ResponseEntity(todoProvider.create(todo), HttpStatus.CREATED)

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun getList() = todoProvider.list()
}