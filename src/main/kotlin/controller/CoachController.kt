package no.marius.coach.controller

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
    fun coaching(@RequestParam("file") file: MultipartFile?): Mono<OllamaResponse> {
        if(file == null){
            throw NullPointerException("The image cannot be null")
        }
        return djlService.analyzeJoints(file?.bytes)
    }
}






