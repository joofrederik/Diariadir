package id.ac.unpas.diariadir.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.ac.unpas.diariadir.data.local.entity.Genre

@Dao
interface GenreDao {
    @Query ("select * from genres")
    fun getGenres(): List<Genre>

    @Insert
    fun insertGenre(genre: Genre)

    @Update
    fun updateGenre(genre: Genre)

    @Delete
    fun deleteGenre(genre: Genre)
}