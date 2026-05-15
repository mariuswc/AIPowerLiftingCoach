package no.marius.coach.controller

import dto.OllamaChatResponse
import dto.OllamaRequest
import no.marius.coach.service.OllamaService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux


@RestController
class CoachController(
    private val ollamaService: OllamaService,
) {
    @PostMapping("/coach")
    suspend fun coaching(@RequestBody prompt: OllamaRequest): Flux<OllamaChatResponse?> {
        return ollamaService.streamingResponse(prompt)
        //response from the ollama with prompt generated from DJL.
    }
}