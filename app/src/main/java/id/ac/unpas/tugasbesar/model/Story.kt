package id.ac.unpas.tugasbesar.model

data class Story(
    val title: String,
    val author: String,
    val imageRes: Int,
    val views: String,
    val likes: String,
    val tags: List<String>,
    val genre: String,
    val rating: Double,
    val sinopsis: String = "",
    val altTitle: String? = null
)
