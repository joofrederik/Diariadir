package id.ac.unpas.diariadir.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "stories")
data class Story(
    @PrimaryKey val title: String,
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
