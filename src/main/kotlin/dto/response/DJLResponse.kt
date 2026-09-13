package no.marius.coach.dto.response

data class DJLResponse(
    val joints: List<XandYPosition>,
    val message: String? = null
)

data class XandYPosition(
    val positionX: Float,
    val positionY: Float
)


