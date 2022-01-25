package com.app.ichsanulalifwantest.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.ichsanulalifwantest.data.UserRepository
import com.app.ichsanulalifwantest.data.model.UserEntity
import kotlinx.coroutines.runBlocking

class UserViewModel(context: Context) : ViewModel()  {

    private val status = MutableLiveData<Boolean>()
    private val userRepository = UserRepository(context)

    val observableStatus: LiveData<Boolean>
        get() = status

    fun getUserInput(id : Int) = runBlocking {
        userRepository.getUserInput(id)
    }

    fun addInput(user: UserEntity) {
        status.value = try{
            userRepository.insert(user)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }
}