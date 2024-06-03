package com.example.kuclubapp.firebaseDB

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NoticeDao(private val firebaseDB: FirebaseDatabase) {

    suspend fun getAllNotices(onResult: (List<Notice>) -> Unit) {
        val notices = mutableListOf<Notice>()
        val table = firebaseDB.getReference("KuclubDB/Notice")
        table.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (noticeSnapshot in dataSnapshot.children) {
                    val notice = noticeSnapshot.getValue(Notice::class.java)
                    notice?.let { notices.add(it) }
                }
                onResult(notices)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onResult(emptyList())
            }
        })
    }
}
