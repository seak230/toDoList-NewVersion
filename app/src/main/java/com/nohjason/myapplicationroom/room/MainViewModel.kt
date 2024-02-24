package com.nohjason.myapplicationroom.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MainViewModel(application: Application) : ViewModel() {

    val allProducts: LiveData<List<UserEntity>>
    private val repository: UserRepository

    init {
        val userDb = UserDatabase.getUser(application)
        val userDao = userDb.userDao()
        repository = UserRepository(userDao)

        allProducts = repository.allUser
    }

    fun insertProduct(user: UserEntity) {
        repository.insertUser(user)
    }

    fun updateProduct(user: UserEntity) {
        repository.updateUser(user)
    }

    fun deleteProduct(user: UserEntity) {
        repository.deleteUser(user)
    }
}