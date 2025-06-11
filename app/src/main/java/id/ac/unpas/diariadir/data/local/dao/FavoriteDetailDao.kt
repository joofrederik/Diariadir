package id.ac.unpas.diariadir.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.ac.unpas.diariadir.data.local.entity.FavoriteDetail

@Dao
interface FavoriteDetailDao {
    @Query ("select * from favorite_details")
    fun getFavDetails(): List<FavoriteDetail>

    @Insert
    fun insertFavDetail(favoriteDetail: FavoriteDetail)

    @Update
    fun updateFavDetail(favoriteDetail: FavoriteDetail)

    @Delete
    fun deleteFavDetail(favoriteDetail: FavoriteDetail)
}