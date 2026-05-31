package no.marius.coach.controller

import no.marius.coach.service.DjlService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class TestUpload(
    private val djlService: DjlService
) {

        @PostMapping("/test/upload")
        fun testUpload(@RequestBody image: MultipartFile){
               return djlService.uploadImageToDir(image)
           }

}