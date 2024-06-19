package com.example.kuclubapp.firebaseDB

import org.w3c.dom.Text

data class Clubs(
    val clubId:Int,
    val clubCategoryId:Int,
    var clubName: String,
    var clubClassification: String,
    var clubCategory: String,
    var clubIntroduction: String? = null,
    var clubInsta: String? = null,
    var clubActivities : String?=null,
    var clubRecruitment : String?=null,
    var clubPresident : String?=null,
    var clubContactNum : String?=null,
    var clubEstablishedDate :String?=null,
    var clubImgUrl :String?=null,
    var clubLikes : Int?=0
){
    constructor():this(0, 0, "","","")
}
