package no.marius.coach.service

import ai.djl.modality.cv.Image
import ai.djl.modality.cv.output.Joints
import ai.djl.repository.zoo.ZooModel
import org.apache.commons.io.FilenameUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException


@Service
class djlService(
    private val model: ZooModel<Image, Joints>
) {

    private val logger = LoggerFactory.getLogger(djlService::class.java)


    fun validateImage(image: MultipartFile): String {
        if(image.isEmpty)
            throw FileNotFoundException("The file is empty") //making sure the picture is not empty
        val imageExtension = FilenameUtils.getExtension(image.originalFilename)
        logger.warn("File was empty")

        if (imageExtension !in AllowedFileType.entries.map { it.name })
            throw FileNotFoundException("Please enter a valid file, allowed filetypes are ${AllowedFileType.entries.map { it.name }}")
        logger.warn("Not supported file format")
        getJointsEstimation(image)
    }

    fun getJointsEstimation(image: MultipartFile){
        val loadModel = model.newPredictor()
    }

    enum class AllowedFileType{PNG,JPG}
}