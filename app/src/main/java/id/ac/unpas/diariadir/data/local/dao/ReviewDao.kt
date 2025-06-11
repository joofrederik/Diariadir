package id.ac.unpas.diariadir.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.ac.unpas.diariadir.data.local.entity.Book
import id.ac.unpas.diariadir.data.local.entity.Review

@Dao
interface ReviewDao {
    @Query ("select * from reviews")
    fun getReviews(): List<Review>

    @Query ("select * from reviews where reviews.book_id = :id")
    fun getReviewByBook(id: Int): Review

    @Insert
    fun insertReview(review: Review)

    @Update
    fun updateReview(review: Review)

    @Delete
    fun deleteReview(review: Review)
}