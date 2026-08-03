package no.marius.coach.dto.response

import ai.djl.modality.cv.output.Joints

data class DJLResponse(
    val joints: Array<Joints>?,
    val message: String? = null
)

