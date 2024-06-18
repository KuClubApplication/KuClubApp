package com.example.kuclubapp.viewmodel

import com.example.kuclubapp.firebaseDB.Clubs
import com.example.kuclubapp.firebaseDB.ClubsDao
import com.example.kuclubapp.firebaseDB.UserLikedClub
import com.example.kuclubapp.firebaseDB.UserLikedClubDao
import com.google.firebase.database.FirebaseDatabase

class ClubRepository(private val firebaseDB: FirebaseDatabase) {
    private val ClubsDao = ClubsDao(firebaseDB)
    private val UserLikedClubDao = UserLikedClubDao(firebaseDB)

    // Clubs 접근
    suspend fun insertClub(club: Clubs){
        ClubsDao.insertClub(club)
    }
    suspend fun deleteClub(club: Clubs){
        ClubsDao.deleteClub(club.clubName)
    }
    suspend fun searchbyClubName(clubName:String, onResult:(List<Clubs>) -> Unit){
        ClubsDao.searchbyClubName(clubName, onResult)
    }
    suspend fun searchbyClubId(clubId:String, onResult:(List<Clubs>) -> Unit){
        ClubsDao.searchbyClubId(clubId, onResult)
    }
    suspend fun getAllClubs(onResult:(List<Clubs>) -> Unit){
        ClubsDao.getAllClubs(onResult)
    }

    // UserLikedClub 접근
    suspend fun insertLiked(userLikedClub: UserLikedClub){
        UserLikedClubDao.insertLiked(userLikedClub)
    }
    suspend fun deleteLiked(userLikedClub: UserLikedClub){
        UserLikedClubDao.deleteLiked(userLikedClub)
    }
    suspend fun getAllLiked(userId:String, onResult: (List<String>) -> Unit){
        UserLikedClubDao.getAllLiked(userId, onResult)
    }
    fun getAllLikedClub() = UserLikedClubDao.getAllLikedByClub()
}