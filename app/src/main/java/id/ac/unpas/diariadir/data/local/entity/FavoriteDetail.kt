package id.ac.unpas.diariadir.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity (tableName = "favorite_details",
    foreignKeys = [ForeignKey(
        entity = Favorite::class,
        parentColumns = ["id"],
        childColumns = ["favorite_id"]
    ),
    ForeignKey(
        entity = Book::class,
        parentColumns = ["id"],
        childColumns = ["book_id"]
    )])
data class FavoriteDetail(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "favorite_id") val favoriteId: Int,
    @ColumnInfo(name = "book_id") val bookId: Int
)
