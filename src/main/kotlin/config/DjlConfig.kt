package no.marius.coach.config

import ai.djl.Application
import ai.djl.modality.cv.Image
import ai.djl.modality.cv.output.Joints
import ai.djl.repository.zoo.Criteria
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DjlConfig {

@Bean
fun djlSetup(){
    val criteria = Criteria.builder()
        .setTypes(Image::class.java, Joints::class.java)
        .optApplication(Application.CV.POSE_ESTIMATION)// defines input and output data type
        .build()
    criteria.loadModel()
}

}