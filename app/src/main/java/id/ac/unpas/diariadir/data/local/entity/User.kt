package id.ac.unpas.diariadir.data.local.entity

data class User (
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val avatar: String
)