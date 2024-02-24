package com.nohjason.myapplicationroom.room

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(private val userDao: UserDao) {
    val allUser: LiveData<List<UserEntity>> = userDao.getAllUser()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertUser(newuser: UserEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.insert(newuser)
        }
    }

    fun updateUser(newuser: UserEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.update(newuser)
        }
    }

    fun deleteUser(newuser: UserEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.delete(newuser)
        }
    }
}