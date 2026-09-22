package no.marius.coach.service

import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.Joints
import ai.djl.repository.zoo.ZooModel
import com.github.kokorin.jaffree.StreamType
import com.github.kokorin.jaffree.ffmpeg.FFmpeg
import com.github.kokorin.jaffree.ffmpeg.Frame
import com.github.kokorin.jaffree.ffmpeg.FrameConsumer
import com.github.kokorin.jaffree.ffmpeg.FrameOutput
import com.github.kokorin.jaffree.ffmpeg.Stream
import com.github.kokorin.jaffree.ffmpeg.UrlInput
import no.marius.coach.model.dto.response.DJLResponse
import no.marius.coach.model.dto.response.OllamaResponse
import no.marius.coach.util.toXandYPosition
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.awt.image.BufferedImage
import java.nio.file.Files
import kotlin.io.path.exists

@Service
class VideoService(
    private val zooModel: ZooModel<Image, Array<Joints>>,
    private val ollamaService: OllamaService
    )

{
    fun extractVideoFrames(videoBytes: ByteArray?): List<BufferedImage> {
        //something wrong with path, ffmpeg reads from wrong path???
        val tempFile = Files.createTempFile("video", ".mp4")
        println("the file: ${tempFile.exists()}")
        Files.write(tempFile, videoBytes)
        println("the file still: ${tempFile.exists()}")
        val frames = mutableListOf<BufferedImage>()

        try {
            FFmpeg.atPath()
                .addInput(UrlInput.fromPath(tempFile))
                .addOutput(
                    FrameOutput.withConsumer(object: FrameConsumer {
                        override fun consumeStreams(streams: List<Stream>) {}

                        override fun consume(frame: Frame?) {
                            if (frame == null) return
                            frames.add(frame.image)
                        }
                    })
                        .setFrameRate(5)
                        .disableStream(StreamType.AUDIO)
                )
                .setProgressListener {
                    println("The progress: $it")
                }
                .execute()

        } finally {
            Files.deleteIfExists(tempFile)
        }

        return frames
    }

    fun analyzeVideoFrames(bytes: ByteArray?): Mono<OllamaResponse> {

        val extractedImages = extractVideoFrames(bytes)

        val singleImage = extractedImages.map { image ->

            val predictionImage: Image? = ImageFactory.getInstance()?.fromImage(image)
            val prediction = zooModel.newPredictor().predict(predictionImage)

            if (prediction.isNullOrEmpty()) DJLResponse(emptyList(), "The image does not contain any joints")
            prediction?.toXandYPosition()

        }
        return ollamaService.stream(singleImage)

    }
}
