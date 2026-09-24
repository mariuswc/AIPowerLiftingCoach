package no.marius.coach.util

import ai.djl.modality.cv.output.Joints
import no.marius.coach.model.dto.response.XandYPosition


fun Joints.toXandYPosition(): List<XandYPosition> =
    this.joints.mapIndexed {index, joint ->
        XandYPosition(JointType.entries[index], joint.x.toFloat(), joint.y.toFloat())
    }


fun Array<Joints>.toXandYPosition(): List<XandYPosition> =
    this.flatMap { it.toXandYPosition() }


//DJL follows COCO standard, so the DJL response will give us the joints in this order.
enum class JointType {
    NOSE,
    LEFT_EYE,
    RIGHT_EYE,
    LEFT_EAR,
    RIGHT_EAR,
    LEFT_SHOULDER,
    RIGHT_SHOULDER,
    LEFT_ELBOW,
    RIGHT_ELBOW,
    LEFT_WRIST,
    RIGHT_WRIST,
    LEFT_HIP,
    RIGHT_HIP,
    LEFT_KNEE,
    RIGHT_KNEE,
    LEFT_ANKLE,
    RIGHT_ANKLE,
}