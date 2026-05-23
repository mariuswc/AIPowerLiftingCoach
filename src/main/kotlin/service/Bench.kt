package no.marius.coach.service

import org.apache.catalina.User

data class Bench(
    val id: String,
    val user: User
) {
}

data class testing(): User