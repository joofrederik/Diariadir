package id.ac.unpas.diariadir.data.remote

import retrofit.Call
import retrofit.http.GET
import retrofit.http.POST
import id.ac.unpas.diariadir.data.local.entity.User
import id.ac.unpas.diariadir.data.local.entity.Book
import id.ac.unpas.diariadir.data.local.entity.Genre
import id.ac.unpas.diariadir.data.local.entity.Review
import id.ac.unpas.diariadir.data.local.entity.ReviewDetail

interface ApiService {
    @GET("users")
    suspend fun getUserData(): Call<User>

    @GET("books")
    suspend fun getBooksData(): Call<List<Book>>

    @GET("genres")
    suspend fun getGenreData(): Call<Genre>

    @GET("reviews")
    suspend fun getReviewData(): Call<Review>

    @GET("review_details")
    suspend fun getReviewDetailData(): Call<ReviewDetail>

    @POST("review_details")
    suspend fun addReviewDetailData(): Call<ReviewDetail>

    @POST("users")
    suspend fun signIn(): Call<User>
}