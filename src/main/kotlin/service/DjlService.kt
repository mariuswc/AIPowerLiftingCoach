package no.marius.coach.service

import ai.djl.inference.Predictor
import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.Joints
import ai.djl.repository.zoo.ZooModel
import no.marius.coach.dto.response.DJLResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class DjlService(
    zooModel: ZooModel<Image, Array<Joints>>,
) {
    private val predictor: Predictor<Image, Array<Joints>> = zooModel.newPredictor()
    val logger: Logger = LoggerFactory.getLogger(DjlService::class.java)

    init {
        println("Model ${zooModel.modelPath} has been loaded")
    }

    fun analyzeJoints(image: MultipartFile): DJLResponse? {
            //we read directly from the inputstream
            val img: Image = ImageFactory.getInstance().fromInputStream(image.inputStream)
            val prediction = predictor.predict(img)

             return if (prediction.isEmpty()) {
               DJLResponse(prediction, "The image does not contain any joints")
            } else {
                DJLResponse(prediction)
            }
        }
    }











