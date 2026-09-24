package no.marius.coach.model.dto.response

import no.marius.coach.util.JointType

data class DJLResponse(
    val joints: List<XandYPosition>,
    val message: String? = null
)

data class XandYPosition(
    val joint: JointType,
    val positionX: Float,
    val positionY: Float
)


