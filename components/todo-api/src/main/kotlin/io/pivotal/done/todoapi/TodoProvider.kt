package io.pivotal.done.todoapi

interface TodoProvider {
    fun create(todo:Todo):Todo
    fun list():List<Todo>
}