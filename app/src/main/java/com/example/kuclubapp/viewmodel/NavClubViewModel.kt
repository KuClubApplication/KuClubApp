package com.example.kuclubapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kuclubapp.firebaseDB.Clubs
import com.example.kuclubapp.firebaseDB.Notice
import com.example.kuclubapp.firebaseDB.UserLikedClub
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClubViewModelFactory(private val repository: ClubRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NavClubViewModel::class.java)) {
            return NavClubViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class NavClubViewModel(private val repository: ClubRepository): ViewModel() {
    // 전체 동아리 목록
    private val _clubs = MutableLiveData<List<Clubs>>()
    val clubs: LiveData<List<Clubs>> = _clubs
    // 관심 동아리 목록
    private val _clubLiked = MutableLiveData<List<Clubs>>()
    val clubLiked: LiveData<List<Clubs>> = _clubLiked
    // 동아리에 좋아요 수
    private var _itemList = MutableStateFlow<List<UserLikedClub>>(emptyList())
    val itemList = _itemList.asStateFlow()

    // 전체 동아리 접근
    fun insertClub(club: Clubs){
        viewModelScope.launch {
            repository.insertClub(club)
        }
    }
    fun deleteClub(club: Clubs){
        viewModelScope.launch {
            repository.deleteClub(club)
        }
    }
    fun searchById(userId: String){
        viewModelScope.launch {
            repository.searchbyClubId(userId){
                _clubs.value = it
            }
        }
    }
    fun searchByName(clubName:String){
        viewModelScope.launch {
            repository.searchbyClubName(clubName){
                _clubs.value = it
            }
        }
    }
    fun getAllClubs(){
        viewModelScope.launch {
            repository.getAllClubs {
                _clubs.value = it
            }
        }
    }

    // 관심 동아리 접근
    fun insertLiked(userId:String, clubId:Int){ // 현재 userId와 선택한 club을 넣으면 list에 추가됨
        val userLikedClub = UserLikedClub(clubId,userId)
        viewModelScope.launch {
            repository.insertLiked(userLikedClub)
        }
    }
    fun deleteLiked(userId:String, clubId:Int){
        val userLikedClub =UserLikedClub(clubId, userId)
        viewModelScope.launch {
            repository.deleteLiked(userLikedClub)
        }
    }
    fun getAllLiked(userId: String){
        val clubIdList = mutableListOf<String>()
        val clubLiked = mutableListOf<Clubs>()
        viewModelScope.launch {
            repository.getAllLiked(userId) { // clubId 리스트 받아옴
                it.forEach {
                    println(it + " ID 찾음")
                    clubIdList.add(it)
                }
                clubIdList.forEach {
                    println(it + " ID 찾음")
                    viewModelScope.launch {
                        repository.searchbyClubId(it){
                            it.forEach {
                                println(it.clubName + " ID 찾음")
                                clubLiked.add(it)
                            }
                            _clubLiked.value = clubLiked
                        }
                    }
                }
            }
        }
    }
    fun getAllLikedByClub(){
        val clubLiked = mutableListOf<UserLikedClub>()
        viewModelScope.launch {
            repository.getAllLikedClub().collect { it -> // UserLikedClub 리스트 받아옴
                it.forEach {
                    clubLiked.add(it)
                }
                Log.d("testtest1",it.toString())
                _itemList.value = clubLiked
            }
        }
    }
}