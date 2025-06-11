package id.ac.unpas.diariadir.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity (tableName = "review_details",
    foreignKeys = [ForeignKey(
        entity = Review::class,
        childColumns = ["review_id"],
        parentColumns = ["id"]
    ),
    ForeignKey(
        entity = User::class,
        childColumns = ["user_id"],
        parentColumns = ["id"]
    )])
data class ReviewDetail(
    @PrimaryKey @ColumnInfo(name = "id", index = true) val id: Int,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "review_id") val reviewId: Int,
    @ColumnInfo(name = "user_id") val userId: Int
)
