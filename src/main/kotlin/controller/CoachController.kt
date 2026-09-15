package no.marius.coach.controller

import no.marius.coach.dto.response.OllamaResponse
import no.marius.coach.service.DjlService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono
import java.io.IOException
import java.time.LocalDateTime


@RestController
class CoachController(
    private val djlService: DjlService
) {
    @PostMapping("/coach")
    fun coaching(@RequestParam("file") image: MultipartFile?): Mono<OllamaResponse> {
        val bytes = image?.bytes
        return djlService.analyzeJoints(bytes)
    }
}






