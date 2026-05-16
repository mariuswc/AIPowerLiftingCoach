package no.marius.coach.controller

import dto.OllamaRequest
import dto.OllamaResponse
import no.marius.coach.service.OllamaService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
class CoachController(
    private val ollamaService: OllamaService,
) {
    @PostMapping("/coach")
    suspend fun coaching(@RequestBody request: OllamaRequest): Mono<OllamaResponse?> {
        return ollamaService.stream(request)
        //response from the ollama with prompt generated from DJL.
    }
}