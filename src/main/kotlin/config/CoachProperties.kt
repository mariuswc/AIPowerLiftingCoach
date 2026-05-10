package config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("coach")
data class CoachProperties(
    val video: Video?,
    val ffmpeg: Ffmpeg?,
    val pose: Pose?,
) {
    data class Video(
        val storagePath: String,
        val fps: Int,
        val maxDurationSeconds: Int,
    )
    data class Ffmpeg(val path: String)
    data class Pose(val confidenceThreshold: Double)
}