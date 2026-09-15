package no.marius.coach.exceptions

import org.springframework.http.HttpStatus

open class CoachException(message: String, val httpStatus: HttpStatus, val errorCode: String) : Exception(message)


class InvalidFileExtension(mimeType: String, allowedExtensions: List<String>? = null): CoachException(
    "Invalid file extension: $mimeType, the allowed extensions are: $allowedExtensions",
    HttpStatus.UNSUPPORTED_MEDIA_TYPE,
    errorCode = "INVALID_FILE_EXTENSION"
)
