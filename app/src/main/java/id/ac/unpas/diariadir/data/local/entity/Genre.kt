package id.ac.unpas.diariadir.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "genres")
data class Genre(
    @PrimaryKey @ColumnInfo(name = "id", index = true) val id: Int,
    @ColumnInfo(name = "name") val name: String
)