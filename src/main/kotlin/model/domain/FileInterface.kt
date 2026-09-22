package no.marius.coach.model.domain

import java.awt.image.BufferedImage

sealed interface FileInterface

sealed class FileExtensions: FileInterface{
    class VideoExtension(val videoBytes: ByteArray?): FileExtensions()
    class PictureExtension(val bytes: ByteArray?): FileExtensions()

}




