package dto

data class OllamaRequest(
    val model: String = "llama3.1:8b",
    val prompt: String
)
