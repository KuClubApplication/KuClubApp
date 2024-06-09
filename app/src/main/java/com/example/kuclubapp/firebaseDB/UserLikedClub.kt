package com.example.kuclubapp.firebaseDB

data class UserLikedClub(
    val clubId:Int,
    val userId:String
){
    constructor():this(0, "noID")
}
