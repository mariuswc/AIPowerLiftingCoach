//package no.marius.coach.service
//
//import ai.djl.inference.Predictor
//import ai.djl.modality.cv.Image
//import ai.djl.modality.cv.output.Joints
//import ai.djl.repository.zoo.ZooModel
//import com.fasterxml.jackson.core.io.UTF8Writer
//import org.apache.commons.io.FilenameUtils
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.web.multipart.MultipartFile
//import java.io.File
//import java.io.FileNotFoundException
//import java.io.InputStream
//
//
//class DjlService(
//    private val zooModel: ZooModel<Image, Array<Joints>>,
//    @Value("\$image.path")
//    private val imagePath: String
//) {
//    private val predictor: Predictor<Image, Array<Joints?>?> = zooModel.newPredictor()
//
//
//    init {
//        println("Model ${zooModel.modelPath} has been loaded")
//    }
//
//    fun validateImage(image: MultipartFile) {
//        requireNotNull(image){"Image was null"}
//            val imageExtension = FilenameUtils.getExtension(image.originalFilename?.lowercase())
//
//            if (imageExtension !in AllowedFileType.entries.map { it.name })
//                throw FileNotFoundException("Please enter a valid file, allowed filetypes are ${AllowedFileType.entries.map { it.name.lowercase() } }}")
//        }
//
//
//    fun analyzeJoints(image: MultipartFile): Predictor<Image?, Array<Joints>?>? {
//        validateImage(image)
//       // return predictor.predict(//needs an image type)
//    }
//
//
//        fun saveByteArrayToImageType(image: MultipartFile) {
//
//            val file = File(imagePath)
//
//            //writing the bytes to the file
//            file.writeBytes(image.bytes)
//
//           //reading from file
//            val openInputStream = file.inputStream()
//            val readByte = file.readBytes()
//            println(readByte.decodeToString())
//            openInputStream.close()
//
//
//            file.outputStream().write(image.bytes)
//
//        }
//
//    enum class AllowedFileType(mimetype: String){PNG("text/pdf"),JPG("text/pdf")}
//}
//
//
//
