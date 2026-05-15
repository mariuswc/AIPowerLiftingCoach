package no.marius.coach.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class OllamaClient(
    @Value("\${spring.ai.ollama.base-url}")
    private val baseURL:String,
) {


    @Bean
    fun webClient(builder: WebClient.Builder): WebClient =
        builder
            .baseUrl(baseURL)
            .build()
}