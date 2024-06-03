package com.example.kuclubapp.viewmodel

import com.example.kuclubapp.firebaseDB.Notice
import com.example.kuclubapp.firebaseDB.NoticeDao
import com.google.firebase.database.FirebaseDatabase

class NoticeRepository(private val firebaseDB: FirebaseDatabase) {

    private val noticeDao = NoticeDao(firebaseDB)

    suspend fun getAllNotices(onResult: (List<Notice>) -> Unit) {
        noticeDao.getAllNotices(onResult)
    }
}