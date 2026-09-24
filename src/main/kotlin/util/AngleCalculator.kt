package no.marius.coach.util

import no.marius.coach.model.dto.response.XandYPosition
import kotlin.math.acos
import kotlin.math.sqrt

/**
 * Calculates the angle at [vertex], formed by the lines vertex-a and vertex-b, in degrees.
 * This was generated with the help of an LLM
 */
fun angleBetween(a: XandYPosition, vertex: XandYPosition, b: XandYPosition): Double {
    val vectorA = Pair(a.positionX - vertex.positionX, a.positionY - vertex.positionY)
    val vectorB = Pair(b.positionX - vertex.positionX, b.positionY - vertex.positionY)

    val dotProduct = vectorA.first * vectorB.first + vectorA.second * vectorB.second
    val lengthA = sqrt(vectorA.first * vectorA.first + vectorA.second * vectorA.second)
    val lengthB = sqrt(vectorB.first * vectorB.first + vectorB.second * vectorB.second)

    val cosineOfAngle = (dotProduct / (lengthA * lengthB)).coerceIn(-1f, 1f)

    return Math.toDegrees(acos(cosineOfAngle.toDouble()))
}

/**
 * Finds three named joints in [joints] and returns the angle at [vertex] between [from] and [to].
 * Returns null if any of the three joints wasn't detected.
 */
fun angleBetween(joints: List<XandYPosition>, from: JointType, vertex: JointType, to: JointType): Double? {
    val a = joints.firstOrNull { it.joint == from } ?: return null
    val v = joints.firstOrNull { it.joint == vertex } ?: return null
    val b = joints.firstOrNull { it.joint == to } ?: return null

    return angleBetween(a, v, b)
}
