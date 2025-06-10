package id.ac.unpas.diariadir.data.local.entity

data class Book (
    val id: Int,
    val title: String,
    val author: String,
    val imageRes: Int,
    val genre: String,
    val desc: String = ""
)