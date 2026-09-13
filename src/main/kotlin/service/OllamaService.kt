package no.marius.coach.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.marius.coach.dto.request.OllamaRequest
import no.marius.coach.dto.response.OllamaResponse
import no.marius.coach.dto.response.XandYPosition
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.time.Duration

@JsonIgnoreProperties
@Service
class OllamaService(
    private val webClient: WebClient,
) {

    val mapper: ObjectMapper = jacksonObjectMapper()

    fun stream(detections: List<XandYPosition>?): Mono<OllamaResponse> {

        val prompt = mapper.writeValueAsString(detections)

       return webClient.post()
            .uri("/api/generate")
            .bodyValue(OllamaRequest(prompt = prompt))
            .retrieve()
            .bodyToMono<OllamaResponse>()
            .onErrorMap {
                    error ->
                Throwable(error)
            }
           .timeout(Duration.ofMinutes(2))
    }
    }



