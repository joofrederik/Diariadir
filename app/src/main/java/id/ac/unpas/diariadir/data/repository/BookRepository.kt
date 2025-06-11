package id.ac.unpas.diariadir.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.unpas.diariadir.BuildConfig
import id.ac.unpas.diariadir.data.local.DiariadirDB
import id.ac.unpas.diariadir.data.local.dao.BookDao
import id.ac.unpas.diariadir.data.local.entity.Book
import id.ac.unpas.diariadir.data.remote.ApiService
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit

class BookRepository constructor(
    apiService: ApiService,
    bookDao: BookDao
) {
    suspend fun insertAllBooks(reload: Boolean = true, context: Context) {
        if (reload) {
            val retrofit: Retrofit = Retrofit.Builder().baseUrl(BuildConfig.API_URL).addConverterFactory(
                GsonConverterFactory.create()).build()
            val apiService: ApiService = retrofit.create<ApiService>(ApiService::class.java)
            val call: Call<List<Book>> = apiService.getBooksData()

            call.enqueue(object : Callback<List<Book>> {
                override fun onResponse(response: Response<List<Book>>?, retrofit: Retrofit?) {
                    if (response!!.isSuccess) {
                        val db = Room.databaseBuilder(
                            context,
                            DiariadirDB::class.java, "diariadir"
                        ).build()
                        val bookDao = db.bookDao()
                        Log.d("BookTag", "halo " + response.body().toString())
                        //bookDao.insertBooks(response.body())
                        //callback(response.body() as Book)
                    }
                }

                override fun onFailure(t: Throwable?) {
                    Toast.makeText(context, "Request to API fail.", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}