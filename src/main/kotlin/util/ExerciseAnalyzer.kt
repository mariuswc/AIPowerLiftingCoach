package no.marius.coach.util

import no.marius.coach.model.domain.Exercises
import no.marius.coach.model.dto.response.XandYPosition

/**
 * One implementation per exercise. Spring collects them all, and [DjlService] picks the
 * one matching the requested exercise.
 */
interface ExerciseAnalyzer {

    val exercise: Exercises

    /**
     * Reference ranges and coaching cues for this exercise, appended to the system prompt.
     * Without these the model invents its own reference values.
     */
    val coachingContext: String

    /**
     * [framesOfJoints] holds the detected joints for each video frame, in order.
     * Returns the angles that matter for this exercise, in degrees.
     */
    fun analyze(framesOfJoints: List<List<XandYPosition>>): Map<String, Double>
}
