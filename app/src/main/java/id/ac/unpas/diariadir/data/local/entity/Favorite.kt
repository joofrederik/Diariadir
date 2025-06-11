package id.ac.unpas.diariadir.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity (tableName = "favorites",
    foreignKeys = [ForeignKey(
        entity = User::class,
        childColumns = ["user_id"],
        parentColumns = ["id"]
    )])
data class Favorite(
    @PrimaryKey @ColumnInfo(name = "id", index = true) val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int
)
