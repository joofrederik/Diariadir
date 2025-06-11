package id.ac.unpas.diariadir.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "books",
    foreignKeys = [ForeignKey(
        entity = Genre::class,
        childColumns = ["genre_id"],
        parentColumns = ["id"]
    )])
data class Book (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id", index = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "image") val imageRes: Int,
    @ColumnInfo(name = "genre_id")val genre: String,
    @ColumnInfo(name = "desc") val desc: String = ""
)