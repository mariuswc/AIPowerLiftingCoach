package no.marius.coach.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CoachExceptionHandler {

    private val logger = LoggerFactory.getLogger(CoachExceptionHandler::class.java)

    @ExceptionHandler(CoachException::class)
    fun handleCoachException(e: CoachException): ResponseEntity<Map<String, String?>> =
        ResponseEntity.status(e.httpStatus).body(
            mapOf(
                "errorCode" to e.errorCode,
                "message" to e.message,
            )
        )

    @ExceptionHandler(Exception::class)
    fun handleUnexpected(e: Exception): ResponseEntity<Map<String, String?>> {
        logger.error("Unexpected error", e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            mapOf(
                "errorCode" to "INTERNAL_ERROR",
                "message" to "Something went wrong",
            )
        )
    }
}
