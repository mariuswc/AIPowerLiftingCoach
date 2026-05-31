//package no.marius.coach.controller
//
//import no.marius.coach.service.DjlService
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestParam
//import org.springframework.web.bind.annotation.RestController
//import org.springframework.web.multipart.MultipartFile
//
//
//@RestController
//class ImageController(
//    private val djlService: DjlService
//) {

//    @PostMapping("/picture")
//    fun getImage(@RequestParam("image") image: MultipartFile): ResponseEntity<String> {
//        djlService.analyzeJoints(image)
//        return ResponseEntity.ok("Image analyzed")
//    }
//}