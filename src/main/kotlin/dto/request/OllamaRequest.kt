package no.marius.coach.dto.request

data class OllamaRequest(
    val model: String = "llama3.1:8b",
    val prompt: String,
    val stream: Boolean = false

)