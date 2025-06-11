package id.ac.unpas.diariadir.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.ac.unpas.diariadir.data.local.entity.ReviewDetail

@Dao
interface ReviewDetailDao {
    @Query ("select * from review_details")
    fun getReviewDetails(): List<ReviewDetail>

    @Insert
    fun insertReviewDetails(reviewDetail: ReviewDetail)

    @Update
    fun updateReviewDetails(reviewDetail: ReviewDetail)

    @Delete
    fun deleteReviewDetails(reviewDetail: ReviewDetail)
}