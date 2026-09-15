package no.marius.coach.service

import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.Joints
import ai.djl.repository.zoo.ZooModel
import no.marius.coach.dto.response.DJLResponse
import no.marius.coach.dto.response.OllamaResponse
import no.marius.coach.dto.response.XandYPosition
import no.marius.coach.exceptions.InvalidFileExtension
import org.apache.tika.Tika
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.io.ByteArrayInputStream


@Service
class DjlService(
    private val zooModel: ZooModel<Image, Array<Joints>>,
    private val ollamaService: OllamaService
) {
    private val logger: Logger = LoggerFactory.getLogger(DjlService::class.java)

    init {
        logger.info("Model {} has been loaded", zooModel.modelPath)
    }

    fun analyzeJoints(image: ByteArray?): Mono<OllamaResponse> {
        //we validate the bytes before we predict it
        validateImage(image)

        //fromInputStream IS THROWING AN ERROR BECAUSE IT SAYS THAT IMAGE==NULL
        val predictionImage: Image? = ImageFactory.getInstance()?.fromInputStream(ByteArrayInputStream(image))
        val prediction = zooModel.newPredictor().predict(predictionImage)

        if (prediction.isNullOrEmpty()) DJLResponse(emptyList(), "The image does not contain any joints")

        val positions = prediction?.toXandYPosition()
        return ollamaService.stream(positions)
    }


    private fun Joints.toXandYPosition(): List<XandYPosition> =
        this.joints.map { joint ->
            XandYPosition(joint.x.toFloat(), joint.y.toFloat())
        }

    private fun Array<Joints>.toXandYPosition(): List<XandYPosition> =
        this.flatMap { it.toXandYPosition() }


    private fun validateImage(bytes: ByteArray?) {

        val tika = Tika()
        //tika library will detect the mimetype so we can validate that it has the correct extension(png/jpeg)
        val mimeType = tika.detect(bytes)
        val allowedFileTypes = listOf("image/jpeg", "image/png")

        if (mimeType !in allowedFileTypes){
            logger.warn("The file uploaded has an invalid file extension: $mimeType, the allowed filetypes are: $allowedFileTypes")
            throw InvalidFileExtension(mimeType, allowedFileTypes)
        }

    }
}












