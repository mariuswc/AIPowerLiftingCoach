package no.marius.coach.service

import dto.OllamaChatResponse
import dto.OllamaRequest
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Service
class OllamaService(
    private val webClient: WebClient,
) {

    fun streamingResponse(prompt: OllamaRequest): Flux<OllamaChatResponse?> {
        return webClient.post()

            .bodyValue(prompt)
            .retrieve()
            .bodyToFlux(OllamaChatResponse::class.java)

    }
}