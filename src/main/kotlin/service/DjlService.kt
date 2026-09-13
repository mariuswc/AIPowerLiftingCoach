package no.marius.coach.service

import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.Joints
import ai.djl.repository.zoo.ZooModel
import no.marius.coach.dto.response.DJLResponse
import no.marius.coach.dto.response.OllamaResponse
import no.marius.coach.dto.response.XandYPosition
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono


@Service
class DjlService(
    private val zooModel: ZooModel<Image, Array<Joints>>,
    private val ollamaService: OllamaService
) {
    private val logger: Logger = LoggerFactory.getLogger(DjlService::class.java)

    init {
        logger.info("Model {} has been loaded", zooModel.modelPath)
    }

    fun analyzeJoints(image: MultipartFile): Mono<OllamaResponse> {

        val img: Image = ImageFactory.getInstance().fromInputStream(image.inputStream)
        val prediction = zooModel.newPredictor().predict(img)

        if (prediction.isNullOrEmpty()) DJLResponse(emptyList(), "The image does not contain any joints")

        val positions = prediction?.toXandYPosition()

        return ollamaService.stream(positions)
    }



    private fun Joints.toXandYPosition(): List<XandYPosition> =
        this.joints.map { joint ->
            XandYPosition(joint.x.toFloat(), joint.y.toFloat())
        }

    private fun Array<Joints>.toXandYPosition(): List<XandYPosition> =
        this.flatMap { it.toXandYPosition()}
}











