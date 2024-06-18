package com.example.kuclubapp.firebaseDB

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.values
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserLikedClubDao(private val firebaseDB:FirebaseDatabase) {
    suspend fun insertLiked(userLikedClub: UserLikedClub){
        var table = firebaseDB.getReference("KuclubDB/UserLikedClub")
        table.child(userLikedClub.clubId.toString()+userLikedClub.userId).setValue(userLikedClub)
    }
    suspend fun deleteLiked(userLikedClub: UserLikedClub){
        val table = firebaseDB.getReference("KuclubDB/UserLikedClub")
        table.child(userLikedClub.clubId.toString()+userLikedClub.userId).removeValue()
    }

    suspend fun getAllLiked(userId:String, onResult: (List<String>) -> Unit) {  // userId를 String으로 받아서 해당하는 clubId를 String으로 반환
        val clubIds = mutableListOf<String>()
        val table = firebaseDB.getReference("KuclubDB/UserLikedClub")
        val query = table.orderByChild("userId").equalTo(userId)  // 주의
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userLikedClubSnapshot in dataSnapshot.children) {
                    val userLikedClubId = userLikedClubSnapshot.getValue(UserLikedClub::class.java)
                    userLikedClubId?.let { clubIds.add(it.clubId.toString()) }
                    Log.d("Insert Club","리스트가 업데이트 되었습니다"+userLikedClubId.toString())
                }
                onResult(clubIds)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onResult(emptyList())
            }
        })
    }
//    suspend fun getAllLikedByClub(onResult: (List<UserLikedClub>) -> Unit) {
//        val club = mutableListOf<UserLikedClub>()
//        val table = firebaseDB.getReference("KuclubDB/UserLikedClub")
//        table.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (userLikedClubSnapshot in dataSnapshot.children) {
//                    val userLikedClubId = userLikedClubSnapshot.getValue(UserLikedClub::class.java)
//                    userLikedClubId?.let { club.add(it) }
//                    Log.d("Insert Club","리스트가 업데이트 되었습니다"+userLikedClubId.toString())
//                }
//                onResult(club)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                onResult(emptyList())
//            }
//        })
//    }
    fun getAllLikedByClub(): Flow<List<UserLikedClub>> = callbackFlow {
        val table = firebaseDB.getReference("KuclubDB/UserLikedClub")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<UserLikedClub>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(UserLikedClub::class.java)
                    item?.let { itemList.add(it) }
                    Log.d("testtest1",item.toString())
                }
                trySend(itemList)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        table.addListenerForSingleValueEvent(listener)
        awaitClose {
            table.removeEventListener(listener)
        }
    }

}