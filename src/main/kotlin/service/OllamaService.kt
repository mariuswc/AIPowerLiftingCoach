package no.marius.coach.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dto.OllamaRequest
import dto.OllamaResponse
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@JsonIgnoreProperties
@Service
class OllamaService(
    private val webClient: WebClient,
) {

    fun stream(request: OllamaRequest)= webClient.post()
        .bodyValue(request)
        .retrieve()
        .bodyToMono<OllamaResponse>()

}