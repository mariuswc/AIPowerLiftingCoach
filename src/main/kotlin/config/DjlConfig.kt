package no.marius.coach.config

import ai.djl.modality.cv.Image
import ai.djl.modality.cv.output.Joints
import ai.djl.repository.zoo.Criteria
import ai.djl.repository.zoo.ZooModel
import ai.djl.training.util.ProgressBar
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.awt.image.BufferedImage
import java.io.File

@Configuration
class DjlConfig {

    @Bean
    fun djlSetup(): ZooModel<Image, Array<Joints>?>? {
        val criteria = Criteria.builder()
            .setTypes(Image::class.java, Array<Joints>::class.java)
            .optModelUrls("djl://ai.djl.pytorch/yolo11n-pose")
            .optEngine("PyTorch")
            .optProgress(ProgressBar())
            .build()
   return criteria.loadModel()
}

}