package no.marius.coach.service

import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.Joints
import ai.djl.repository.zoo.ZooModel
import com.github.kokorin.jaffree.ffmpeg.*
import no.marius.coach.model.dto.response.DJLResponse
import no.marius.coach.model.dto.response.OllamaResponse
import no.marius.coach.util.toXandYPosition
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.awt.image.BufferedImage
import java.nio.file.Files

@Service
class VideoService(
    private val zooModel: ZooModel<Image, Array<Joints>>,
    private val ollamaService: OllamaService
    )

{
    fun extractVideoFrames(videoBytes: ByteArray?): List<BufferedImage> {
        val tempFile = Files.createTempFile("upload", ".mp4")
        Files.write(tempFile, videoBytes)

        val frames = mutableListOf<BufferedImage>()

        try {
            FFmpeg.atPath()
                .addInput(UrlInput.fromPath(tempFile))
                .addOutput(
                    FrameOutput.withConsumer(object: FrameConsumer{
                        override fun consumeStreams(streams: List<Stream>) {}

                        override fun consume(frame: Frame?) {
                            if (frame == null) return
                            frames.add(frame.image)
                        }
                    })
                )
                .execute()
        } finally {
            Files.deleteIfExists(tempFile)
        }

        return frames
    }

    fun analyzeVideoFrames(extractedImages:List<BufferedImage>): Mono<OllamaResponse> {

        val singleImage = extractedImages.map { image ->

            val predictionImage: Image? = ImageFactory.getInstance()?.fromImage(image)
            val prediction = zooModel.newPredictor().predict(predictionImage)

            if (prediction.isNullOrEmpty()) DJLResponse(emptyList(), "The image does not contain any joints")
            prediction?.toXandYPosition()

        }
        return ollamaService.stream(singleImage)

    }
}
