package no.marius.coach.controller

import ai.djl.modality.cv.output.Joints
import dto.OllamaRequest
import dto.OllamaResponse
import no.marius.coach.service.DjlService
import no.marius.coach.service.OllamaService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono


@RestController
class CoachController(
    private val djlService: DjlService
) {
    @PostMapping("/coach")
    fun coaching(@RequestBody image: MultipartFile): Array<Joints> {
        return djlService.analyzeJoints(image)
        //response from the ollama with prompt generated from DJL.
    }
}