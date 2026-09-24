package no.marius.coach.controller

import no.marius.coach.model.dto.request.PowerLiftingRequest
import no.marius.coach.model.dto.response.OllamaResponse
import no.marius.coach.service.DjlService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono


@RestController
class CoachController(
    private val djlService: DjlService
) {

    @PostMapping("/coach")
    fun coaching(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("exercise") exercise: String,
    ): Mono<OllamaResponse> =
        djlService.analyzeJoints(PowerLiftingRequest(file, exercise))
}
