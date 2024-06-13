package com.example.kuclubapp.firebaseDB

data class Notice(
    val noticeNum: Int = 0,
    val noticeTitle: String = "",
    val noticeDt: String = "",
    val noticeContent: String = ""
){
    constructor():this(0, "noTitle", "noDetail", "noContent")
}
