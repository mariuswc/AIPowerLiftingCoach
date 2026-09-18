package no.marius.coach.util

import ai.djl.modality.cv.output.Joints
import no.marius.coach.model.dto.response.XandYPosition


fun Joints.toXandYPosition(): List<XandYPosition> =
    this.joints.map { joint ->
        XandYPosition(joint.x.toFloat(), joint.y.toFloat())
    }

fun Array<Joints>.toXandYPosition(): List<XandYPosition> =
    this.flatMap { it.toXandYPosition() }