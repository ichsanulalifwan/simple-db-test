package com.app.ichsanulalifwantest.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.app.ichsanulalifwantest.data.model.UserEntity
import com.app.ichsanulalifwantest.data.room.UserDao
import com.app.ichsanulalifwantest.data.room.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserRepository(context: Context) {

    private val userDao: UserDao

    init {
        val db = UserDatabase.getInstance(context)
        userDao = db.userDao()
    }

    fun getUserInput(id: Int): LiveData<UserEntity> = userDao.getUserInput(id)

    fun insert(user: UserEntity) = runBlocking {
        this.launch(Dispatchers.IO) {
            userDao.insertInput(user)
        }
    }
}