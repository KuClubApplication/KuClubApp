package com.example.kuclubapp.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kuclubapp.DataStoreManager
import com.example.kuclubapp.firebaseDB.User
import kotlinx.coroutines.launch


class UserViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NavUserViewModel::class.java)) {
            return NavUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class NavUserViewModel(private val repository: UserRepository): ViewModel() {
    var userId:String ?= null
    var userPasswd:String ?= null

    var loginStatus = mutableStateOf(false)

    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun getValidUser(id: String, passwd: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.getValidUser(id, passwd, onResult)
        }
    }

    suspend fun deleteToken(context: Context) {
        viewModelScope.launch {
            DataStoreManager.deleteToken(context)
        }
    }

    fun setUserInfo(id:String) {
        userId = id
    }
}
