package com.example.kuclubapp.firebaseDB

import android.content.Context
import com.example.kuclubapp.sendNoticeNotification
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NoticeDao(private val firebaseDB: FirebaseDatabase) {
    suspend fun insertNotice(notice: Notice, context: Context){
        var table = firebaseDB.getReference("KuclubDB/Notice")
        var noticeInfo = table.child(notice.noticeNum.toString()).setValue(notice)
        sendNoticeNotification(context, "새로운 공지사항이 있습니다.")
//        clubInfo.child("clubCategoryId").setValue(clubCategory.clubCategoryId)
//        clubInfo.child("clubCategory").setValue(clubCategory.clubCategory)
    }

    suspend fun deleteNotice(notice: Notice){
        var table = firebaseDB.getReference("KuclubDB/Notice")
        table.orderByKey().equalTo(notice.noticeNum.toString()).ref.removeValue()
    }

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

    suspend fun getNoticeDetail(noticeNum: Int, onResult: (Notice?) -> Unit) {
        val table = firebaseDB.getReference("KuclubDB/Notice")
        table.orderByChild("noticeNum").equalTo(noticeNum.toDouble())
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val notice = dataSnapshot.children.firstOrNull()?.getValue(Notice::class.java)
                    onResult(notice)
                }

                override fun onCancelled(error: DatabaseError) {
                    onResult(null)
                }
        })
    }
}
