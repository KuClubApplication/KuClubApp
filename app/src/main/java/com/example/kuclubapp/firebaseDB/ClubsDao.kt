package com.example.kuclubapp.firebaseDB

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ClubsDao(private val firebaseDB: FirebaseDatabase) {
    // 여기도 insert, delete 있지만 사용하지 않을 예정
    suspend fun insertClub(club: Clubs){
        var table = firebaseDB.getReference("KuclubDB/Clubs")
        var clubInfo = table.child(club.clubId.toString())
        clubInfo.setValue(club).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "Club added successfully")
                    } else {
                        Log.e("Firebase", "Failed to add club", task.exception)
                    }
                }
    }

    suspend fun deleteClub(clubName: String){
        var table = firebaseDB.getReference("KuclubDB/Clubs")
        table.orderByChild("clubName").equalTo(clubName).ref.removeValue()
    }

    suspend fun searchbyClubName(clubName:String, onResult: (List<Clubs>) -> Unit){
        val clubs = mutableListOf<Clubs>()
        val table = firebaseDB.getReference("KuclubDB/Clubs")
        val query = table.orderByChild("clubName").equalTo(clubName)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (clubSnapshot in snapshot.children){
                    val club = clubSnapshot.getValue(Clubs::class.java)
                    club?.let { clubs.add(it) }
                }
                onResult(clubs)
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(emptyList())
            }
        })
    }

    suspend fun searchbyClubId(clubId:String, onResult: (List<Clubs>) -> Unit){
        println("함수실행됨 " + clubId)
        val clubs = mutableListOf<Clubs>()
        val table = firebaseDB.getReference("KuclubDB/Clubs")
        val query = table.orderByKey().equalTo(clubId)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (clubSnapshot in snapshot.children){
                    val club = clubSnapshot.getValue(Clubs::class.java)
                    println(club?.clubName?:"null")
                    club?.let { clubs.add(it) }
                }
                onResult(clubs)
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(emptyList())
            }
        })
    }

    suspend fun getAllClubs(onResult: (List<Clubs>) -> Unit) {
        val clubs = mutableListOf<Clubs>()
        val table = firebaseDB.getReference("KuclubDB/Clubs")
        table.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (clubsSnapshot in dataSnapshot.children) {
                    val club = clubsSnapshot.getValue(Clubs::class.java)
                    club?.let { clubs.add(it) }
                }
                onResult(clubs)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onResult(emptyList())
            }
        })
    }
}