package com.fynnian.springbootdockersecrets.configuration

import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.boot.logging.DeferredLog
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.io.FileSystemResource
import org.springframework.util.StreamUtils
import java.io.IOException
import java.util.*

/*
 * When implementing the EnvironmentPostProcessor you must also register it in the META-INF/spring.factories file.
 * In this case we added the line:
 * org.springframework.boot.env.EnvironmentPostProcessor=com.fynnian.springdockersecrets.configuration.DockerSecretProcessor
 */
class DockerSecretProcessor : EnvironmentPostProcessor {

    companion object {
        const val SECRET_FILE_PATH = "SECRET_FILE_PATH"
        // /run/secrets/ is the default path for docker secrets
        private const val DEFAULT_SECRET_FILE = "/run/secrets/super-important-secret"
        private val log = DeferredLog()
    }

    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {

        /*
         * We'll need to initiate a deferred log and pass it to the application or else the logged information will never be published,
         * as the spring context is not jet loaded. So we collect the logs in the deferred log that will be processed once the spring context is ready.
         */
        application.addInitializers(ApplicationContextInitializer { ctx: ConfigurableApplicationContext -> log.replayTo(DockerSecretProcessor::class.java) })

        val secretFile = environment.getProperty(SECRET_FILE_PATH, DEFAULT_SECRET_FILE)
        val file = FileSystemResource(secretFile)
        if (file.exists()) {

            try {
                log.info("Read secret from file")
                log.debug("Use file $file")
                // read the file
                val secret = StreamUtils.copyToString(file.inputStream, Charsets.UTF_8).trimEnd()

                // create a new set of properties, add our secret and then add the property to the sources
                val props = Properties()
                props["docker.secret"] = secret
                environment.propertySources.addLast(PropertiesPropertySource("secrets", props))

                /*
                 * Here an example to set the spring.datasource.password
                 * Properties props = new Properties();
                 * props.put("spring.datasource.password", dbPassword);
                 * environment.getPropertySources().addLast(new PropertiesPropertySource("databaseProperties", props));
                 */

            } catch (exception: IOException) {
                log.error("Could not parse $file due to $exception")
            }
        } else {
            log.error("Specified secret file: $file doesn't exist")
        }
    }
}
