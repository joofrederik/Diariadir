package id.ac.unpas.diariadir.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.unpas.diariadir.data.local.entity.*
import id.ac.unpas.diariadir.data.local.dao.*

@Database (
    entities = [
        User::class, Favorite::class, FavoriteDetail::class, Genre::class,
        Review::class, ReviewDetail::class, Book::class
    ], version = 1, exportSchema = false)
abstract class DiariadirDB: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun favoriteDetailDao(): FavoriteDetailDao
    abstract fun genreDao(): GenreDao
    abstract fun reviewDao(): ReviewDao
    abstract fun reviewDetailDao(): ReviewDetailDao
    abstract fun bookDao(): BookDao
}