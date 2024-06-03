package com.example.kuclubapp.firebaseDB

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserDao (private val firebaseDB: FirebaseDatabase) {

    suspend fun insertUser(user: User){
        var table = firebaseDB.getReference("KuclubDB/User")
        var userInfo = table.child(user.userId.split("@")[0])
        userInfo.child("userId").setValue(user.userId)
        userInfo.child("userPasswd").setValue(user.userPasswd)
        userInfo.child("userNm").setValue(user.userNm)
        userInfo.child("userMajor").setValue(user.userMajor)
    }

    suspend fun getValidUser(id: String, passwd: String, onResult: (Boolean) -> Unit){

        var table = firebaseDB.getReference("KuclubDB/User")
        val userQuery = table.orderByChild("userId").equalTo(id)
        userQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var isAuthenticated = false

                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null && user.userPasswd == passwd) {
                        isAuthenticated = true
                        break
                    }
                }

                onResult(isAuthenticated)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onResult(false)
            }
        })
    }
}