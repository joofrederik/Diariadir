package id.ac.unpas.diariadir.data.local.entity

data class Review(
    val id: Int,
    val rating: Double,
    val totalReview: Int,
    val bookId: Int
)
