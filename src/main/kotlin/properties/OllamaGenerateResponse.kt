package no.marius.coach.properties

import com.fasterxml.jackson.annotation.JsonProperty

data class OllamaGenerateResponse(
    val model: String,
    val created_at: String,
    val response: String,
    val thinking: String? = null,
    val done: Boolean,
    val done_reason: String? = null,
    val total_duration: Long = 0,
    val load_duration: Long = 0,
    val prompt_eval_count: Long = 0,
    val prompt_eval_duration: Long = 0,
    val eval_count: Long = 0,
    val eval_duration: Long = 0,
    val logprobs: List<LogProb>? = null
)

data class LogProb(
    val token: String,
    val logprob: Double,
    val bytes: List<Int>? = null,
    val top_logprobs: List<LogProb>? = null
)

