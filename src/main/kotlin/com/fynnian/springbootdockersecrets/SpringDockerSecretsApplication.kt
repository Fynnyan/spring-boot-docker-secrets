package com.fynnian.springbootdockersecrets

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringDockerSecretsApplication

fun main(args: Array<String>) {
    runApplication<SpringDockerSecretsApplication>(*args)
}
