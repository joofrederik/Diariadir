package id.ac.unpas.diariadir.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.ac.unpas.diariadir.data.local.entity.Favorite

@Dao
interface FavoriteDao {
    @Query ("select * from favorites")
    fun getFavs(): List<Favorite>

    @Insert
    fun insertFav(favorite: Favorite)

    @Update
    fun updateFav(favorite: Favorite)

    @Delete
    fun deleteFav(favorite: Favorite)
}