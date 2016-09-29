package io.pivotal.done

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class DoneApplication

fun main(args: Array<String>) {
    SpringApplication.run(DoneApplication::class.java, *args)
}
