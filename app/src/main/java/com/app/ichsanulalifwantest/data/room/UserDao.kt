package com.app.ichsanulalifwantest.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.ichsanulalifwantest.data.model.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserInput(id: Int): LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInput(user: UserEntity)
}