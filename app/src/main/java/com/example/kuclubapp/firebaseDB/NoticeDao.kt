package com.example.kuclubapp.firebaseDB

import android.content.Context
import android.util.Log
import com.example.kuclubapp.sendNoticeNotification
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NoticeDao(private val firebaseDB: FirebaseDatabase) {

    private val TAG = "NoticeDao"

    suspend fun insertNotice(notice: Notice, context: Context){
        var table = firebaseDB.getReference("KuclubDB/Notice")
        var noticeInfo = table.child(notice.noticeNum.toString()).setValue(notice)
        sendNoticeNotification(context, notice.clubName,"새로운 공지사항이 있습니다.")
//        clubInfo.child("clubCategoryId").setValue(clubCategory.clubCategoryId)
//        clubInfo.child("clubCategory").setValue(clubCategory.clubCategory)
    }

    private var initialDataLoaded = false
    private var lastProcessedNoticeId = 0

    fun monitorNewNotices(context: Context) {
        val table = firebaseDB.getReference("KuclubDB/Notice")

        table.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { noticeSnapshot ->
                    val notice = noticeSnapshot.getValue(Notice::class.java)
                    notice?.let {
                        if (!initialDataLoaded) {
                            lastProcessedNoticeId = notice.noticeNum
                        } else {
                            if (notice.noticeNum > lastProcessedNoticeId) {
                                Log.d(TAG, "New notice added")
                                Log.d(TAG, "noticeNum: ${notice.noticeNum}")
                                Log.d(TAG, "noticeTitle: ${notice.noticeTitle}")
                                Log.d(TAG, "clubName: ${notice.clubName}")
                                Log.d(TAG, "noticeContent: ${notice.noticeContent}")
                                Log.d(TAG, "------------------------")

                                sendNoticeNotification(context, notice.clubName, "새로운 공지사항이 있습니다.")

                                lastProcessedNoticeId = notice.noticeNum
                            }
                        }
                    }
                }
                initialDataLoaded = true
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Failed to read data:", databaseError.toException())
            }
        })
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
