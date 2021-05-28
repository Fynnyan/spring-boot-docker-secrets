package com.fynnian.springbootdockersecrets.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RevealSecretsController {

    @Value("\${docker.secret:NO SECRET FILE READ}")
    private lateinit var secret: String

    @GetMapping("/reveal-your-secrets")
    fun getSecret() = """
        $secret
        ✧ﾟ･:*:･ﾟ✧
          \[T]/
    """.trimIndent()
}
