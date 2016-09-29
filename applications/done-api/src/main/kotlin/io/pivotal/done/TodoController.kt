package io.pivotal.done

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/todo")
class TodoController {

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun create() = ResponseEntity(Todo("hi"), HttpStatus.CREATED)
}