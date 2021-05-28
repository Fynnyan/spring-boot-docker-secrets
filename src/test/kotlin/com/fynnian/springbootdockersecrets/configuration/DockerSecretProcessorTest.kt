package com.fynnian.springbootdockersecrets.configuration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.SpringApplication
import org.springframework.mock.env.MockEnvironment
import java.io.File
import java.nio.file.Files

@DisplayName("The DockerSecretProcessor")
internal class DockerSecretProcessorTest {
    private val dockerSecretProcessor = DockerSecretProcessor()
    private val application = SpringApplication()

    @Test
    fun `can read a docker secret file`() {
        val env = MockEnvironment()
        val testPw = "super-important-secret"
        val file = File.createTempFile("test-secret", "")

        Files.write(file.absoluteFile.toPath(), testPw.toByteArray())
        file.deleteOnExit() // delete tmp file after the tests are completed.

        env.setProperty(DockerSecretProcessor.SECRET_FILE_PATH, file.absolutePath)

        dockerSecretProcessor.postProcessEnvironment(env, application)

        assertThat(env.getProperty("docker.secret")).isEqualTo(testPw)

    }
}
