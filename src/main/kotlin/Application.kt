package no.marius.coach

import config.CoachProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableConfigurationProperties(CoachProperties::class)
@EnableAsync
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}