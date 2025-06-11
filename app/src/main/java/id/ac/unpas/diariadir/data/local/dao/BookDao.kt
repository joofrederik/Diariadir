package id.ac.unpas.diariadir.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.ac.unpas.diariadir.data.local.entity.Book

@Dao
interface BookDao {
    @Query ("select * from books")
    fun getBooks(): List<Book>

    @Insert
    fun insertBooks(books: List<Book>)

    @Insert
    fun insertBook(book: Book)

    @Update
    fun updateBook(book: Book)

    @Delete
    fun deleteBook(book: Book)
}