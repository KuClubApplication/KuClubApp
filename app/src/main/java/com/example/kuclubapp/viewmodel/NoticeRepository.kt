package com.example.kuclubapp.viewmodel

import android.content.Context
import com.example.kuclubapp.firebaseDB.Notice
import com.example.kuclubapp.firebaseDB.NoticeDao
import com.google.firebase.database.FirebaseDatabase

class NoticeRepository(private val firebaseDB: FirebaseDatabase) {

    private val noticeDao = NoticeDao(firebaseDB)
    suspend fun insertNotice(notice: Notice, context: Context){
        noticeDao.insertNotice(notice, context)
    }

    suspend fun monitorNewNotices(context: Context) {
        noticeDao.monitorNewNotices(context)
    }

    suspend fun deleteNotice(notice: Notice){
        noticeDao.deleteNotice(notice)
    }
    suspend fun getAllNotices(onResult: (List<Notice>) -> Unit) {
        noticeDao.getAllNotices(onResult)
    }

    suspend fun getNoticeDetail(noticeNum: Int, onResult: (Notice?) -> Unit) {
        noticeDao.getNoticeDetail(noticeNum, onResult)
    }
}