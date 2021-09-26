package com.exam.PostmanEcho

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PostmanEchoApplication

fun main(args: Array<String>) {
	runApplication<PostmanEchoApplication>(*args)
}
