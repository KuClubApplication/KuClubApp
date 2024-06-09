package com.example.kuclubapp.firebaseDB

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.values

class UserLikedClubDao(private val firebaseDB:FirebaseDatabase) {
    suspend fun insertLiked(userLikedClub: UserLikedClub){
        var table = firebaseDB.getReference("KuclubDB/UserLikedClub")

        val liked = table.push().setValue(userLikedClub).
        addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "ClubLiked added successfully")
            } else {
                Log.e("Firebase", "Failed to add clubLiked", task.exception)
            }
        }
    }
    suspend fun deleteLiked(userLikedClub: UserLikedClub){
        val table = firebaseDB.getReference("KuclubDB/UserLikedClub")
        val query = table.orderByChild("userId").equalTo(userLikedClub.userId).ref
        query.orderByChild("clubId").equalTo(userLikedClub.clubId.toString()).ref.removeValue()
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
}