package no.marius.coach.service

import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.Joints
import ai.djl.repository.zoo.ZooModel
import no.marius.coach.model.dto.response.DJLResponse
import no.marius.coach.model.dto.response.OllamaResponse
import no.marius.coach.exceptions.InvalidFileExtension
import no.marius.coach.model.domain.FileExtensions
import no.marius.coach.model.domain.FileExtensions.*
import no.marius.coach.util.toXandYPosition
import org.apache.tika.Tika
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.io.ByteArrayInputStream


@Service
class DjlService(
    private val zooModel: ZooModel<Image, Array<Joints>>,
    private val ollamaService: OllamaService,
    private val videoService: VideoService,
) {
    private val logger: Logger = LoggerFactory.getLogger(DjlService::class.java)

    init {
        logger.info("Model {} has been loaded", zooModel.modelPath)
    }

    fun analyzeJoints(image: ByteArray?): Mono<OllamaResponse> {
        //we validate the input from the API
        val validatedInput = validateUploadedFile(image)

        //We cast the sealed classes as the specific types for "video" and "pictures"
        return when (validatedInput) {
            is PictureExtension -> analyzeImage(validatedInput.bytes)
            is VideoExtension -> videoService.analyzeVideoFrames(validatedInput.videoBytes)
        }
    }

    private fun analyzeImage(bytes: ByteArray?): Mono<OllamaResponse> {
        val predictionImage: Image = ImageFactory.getInstance().fromInputStream(ByteArrayInputStream(bytes))
        val prediction = zooModel.newPredictor().predict(predictionImage)

        if (prediction.isNullOrEmpty()) DJLResponse(emptyList(), "The image does not contain any joints")

        val positions = prediction?.toXandYPosition()
        return ollamaService.stream(listOf(positions))
    }

    /*

    I have created sealed classes for both VideoExtension and PictureExtension
    This makes it easier to know what types are returned from the ValidateUploadedFile function
    Either we get the raw bytes for pictures, or we get a list of "VideoFrames"

    */
    private fun validateUploadedFile(bytes: ByteArray?): FileExtensions {
        val tika = Tika()
        val mimeType = tika.detect(bytes)

        // Scanning through the enum for the allowed entry
        val allowedFileExtension = AllowedFileType.entries.firstOrNull {
            it.mimeType == mimeType
        } ?: throw InvalidFileExtension(mimeType)


        return when (allowedFileExtension.isVideo) {
            true -> VideoExtension(bytes)
            false -> PictureExtension(bytes)

        }
    }

    enum class AllowedFileType(val mimeType: String, val isVideo: Boolean) {
        JPEG("image/jpeg", false),
        PNG("image/png", false),
        MP4("video/mp4", true),
        QUICKTIME("video/quicktime",true);
    }


}