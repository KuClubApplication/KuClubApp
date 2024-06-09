package com.example.kuclubapp.firebaseDB

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ClubCategoryDao(private val firebaseDB: FirebaseDatabase) {
    suspend fun insertCategory(clubCategory:ClubCategory){
        var table = firebaseDB.getReference("KuclubDB/ClubCategory")
        var clubInfo = table.child(clubCategory.clubCategoryId.toString()).setValue(clubCategory)
//        clubInfo.child("clubCategoryId").setValue(clubCategory.clubCategoryId)
//        clubInfo.child("clubCategory").setValue(clubCategory.clubCategory)
    }

    suspend fun deleteClub(category: String){
        var table = firebaseDB.getReference("KuclubDB/ClubCategory")
        table.orderByChild(category).equalTo(category).ref.removeValue()
    }

    suspend fun getAllCategory(onResult: (List<ClubCategory>) -> Unit) {
        val categorys = mutableListOf<ClubCategory>()
        val table = firebaseDB.getReference("KuclubDB/ClubCategory")
        table.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (categorysSnapshot in dataSnapshot.children) {
                    val category = categorysSnapshot.getValue(ClubCategory::class.java)
                    category?.let { categorys.add(it) }
                }
                onResult(categorys)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onResult(emptyList())
            }
        })
    }
}