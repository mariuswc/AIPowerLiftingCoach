package no.marius.coach.dto.request

import no.marius.coach.dto.response.XandYPosition

data class OllamaRequest(
    val system: String = "Analyze these joints",
    val prompt: String,
    val model: String = "llama3.1:8b",
    val stream: Boolean = false
)

