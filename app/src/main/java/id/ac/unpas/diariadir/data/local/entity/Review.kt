package id.ac.unpas.diariadir.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity (tableName = "reviews",
    foreignKeys = [ForeignKey(
        entity = Book::class,
        childColumns = ["book_id"],
        parentColumns = ["id"]
    )])
data class Review(
    @PrimaryKey @ColumnInfo(name = "id", index = true) val id: Int,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "total_review") val totalReview: Int,
    @ColumnInfo(name = "book_id") val bookId: Int
)
