package no.marius.coach.controller

import ai.djl.modality.cv.output.Joints
import no.marius.coach.dto.response.DJLResponse
import no.marius.coach.service.DjlService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class CoachController(
    private val djlService: DjlService
) {
    @PostMapping("/coach")
    fun coaching(@RequestBody image: MultipartFile) = djlService.analyzeJoints(image)
}





