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
    fun getUserData(): Call<User>

    @GET("books")
    fun getBookData(): Call<Book>

    @GET("genres")
    fun getGenreData(): Call<Genre>

    @GET("reviews")
    fun getReviewData(): Call<Review>

    @GET("review_details")
    fun getReviewDetailData(): Call<ReviewDetail>

    @POST("review_details")
    fun addReviewDetailData(): Call<ReviewDetail>

    @POST("users")
    fun signIn(): Call<User>
}