package id.ac.unpas.diariadir.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.ac.unpas.diariadir.data.local.entity.User

@Dao
interface UserDao {
    @Query ("select * from users")
    fun getUsers(): List<User>

    @Query ("select * from users where users.username = :usrnm")
    fun getUserByUsername(usrnm: String): User

    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}