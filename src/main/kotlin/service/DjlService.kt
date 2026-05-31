package no.marius.coach.service

import ai.djl.inference.Predictor
import ai.djl.modality.Classifications
import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.Joints
import ai.djl.modality.cv.translator.ImageClassificationTranslator
import ai.djl.modality.cv.util.NDImageUtils
import ai.djl.ndarray.NDArray
import ai.djl.repository.zoo.ZooModel
import ai.djl.translate.Transform
import ai.djl.translate.Translator
import io.micrometer.core.instrument.binder.BaseUnits.CLASSES
import jdk.internal.jmod.JmodFile
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Paths


@Service
class DjlService(
    private val zooModel: ZooModel<Image, Array<Joints>>,
    @param:Value("\${image.path}")
    private val imagePath: String
) {
  private val predictor: Predictor<Image, Array<Joints>> = zooModel.newPredictor()


    init {
        println("Model ${zooModel.modelPath} has been loaded")
    }

    fun validateImage(image: MultipartFile) {
        requireNotNull(image){"Image was null"}
            val imageExtension = FilenameUtils.getExtension(image.originalFilename?.lowercase())

            if (imageExtension !in AllowedFileType.entries.map { it.name })
                throw FileNotFoundException("Please enter a valid file, allowed filetypes are ${AllowedFileType.entries.map { it.name.lowercase() } }}")
        }


    fun analyzeJoints(image: MultipartFile): Predictor<Image?, Array<Joints>?>? {
        val imagePath = Paths.get(imagePath)
        val file: Image = ImageFactory.getInstance().fromFile(imagePath)
        validateImage(image)
        val predict = predictor.predict(file)
        println(predict.contentToString())

    }


        fun uploadImageToDir(image: MultipartFile) {

            val imageDir = File(imagePath)
            val fileName = requireNotNull(image.originalFilename)
            val imageFile = File(imageDir, fileName)

            try {
                if (!imageDir.exists()){
                    imageDir.mkdirs()
                    imageFile.writeBytes(image.bytes)
                    println("Directory succesfully written: ${imageFile.path}")
                }
                else{
                    imageFile.writeBytes(image.bytes)
                    println("Image has been uploaded")
                }
            } catch (e: Exception) {
                println("somethings happen, $e")
            }
        }

    enum class AllowedFileType(mimetype: String){PNG("text/pdf"),JPG("text/pdf")}
}



