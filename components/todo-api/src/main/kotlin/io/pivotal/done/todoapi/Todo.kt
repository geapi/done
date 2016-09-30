package io.pivotal.done.todoapi

import java.io.Serializable

data class Todo(
        val id: String? = null,
        val name: String
) : Serializable
