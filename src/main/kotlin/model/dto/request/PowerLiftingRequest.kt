package no.marius.coach.model.dto.request

import org.springframework.web.multipart.MultipartFile

data class PowerLiftingRequest(
    val file: MultipartFile,
    val exercise: String
)
