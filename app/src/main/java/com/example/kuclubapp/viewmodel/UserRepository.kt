package com.example.kuclubapp.viewmodel

import com.example.kuclubapp.firebaseDB.User
import com.example.kuclubapp.firebaseDB.UserDao
import com.google.firebase.database.FirebaseDatabase

class UserRepository(private val firebaseDB: FirebaseDatabase) {

    private val userDao = UserDao(firebaseDB)

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getValidUser(id: String, passwd: String, onResult: (Boolean) -> Unit) {
        userDao.getValidUser(id, passwd, onResult)
    }

}




