package no.marius.coach.model.dto.request

data class OllamaRequest(
    val system: String = "Analyze these joints",
    val prompt: String,
    val model: String = "llama3.1:8b",
    val stream: Boolean = false
)