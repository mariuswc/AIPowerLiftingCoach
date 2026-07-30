package no.marius.coach.service

import ai.djl.inference.Predictor
import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.Joints
import ai.djl.repository.zoo.ZooModel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class DjlService(
    zooModel: ZooModel<Image, Array<Joints>>,
) {
    private val predictor: Predictor<Image, Array<Joints>> = zooModel.newPredictor()
    private val logger = LoggerFactory.getLogger(DjlService::class.java)

    init {
        println("Model ${zooModel.modelPath} has been loaded")
    }

    fun analyzeJoints(image: MultipartFile): Array<Joints>? {

        return try {   //we read directly from the inputstream
             val img: Image = ImageFactory.getInstance().fromInputStream(image.inputStream)
             val predictResult = predictor.predict(img)
             if (predictResult.isNullOrEmpty()) logger.info("The image did not contain an array")

             predictResult
         }
         catch (e: Exception){
             return null
         }
    }
}









