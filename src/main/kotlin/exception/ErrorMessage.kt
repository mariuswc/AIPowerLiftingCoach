package no.marius.coach.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class GlobalExceptionHandler(){
    @ExceptionHandler(IllegalArgumentException::class)
    fun HandleIllegalArgument(message: IllegalArgumentException): ResponseEntity<Map<String, String>> {
        val body = mapOf(
            "status" to "error",
            "cause" to (message.message?:"Invalid photo")
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

}



