package no.marius.coach.controller

import no.marius.coach.service.djlService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class ImageController(
    private val djlService: djlService
) {

@PostMapping("/picture")
    fun getImage(@RequestBody image: MultipartFile){
        djlService.validateImage(image)
    }

}