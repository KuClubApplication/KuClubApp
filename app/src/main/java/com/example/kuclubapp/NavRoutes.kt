package com.example.kuclubapp

sealed class NavRoutes(val route: String) {

    object Login : NavRoutes("Login")
    object Register : NavRoutes("Register")
    object RegisterSuccess : NavRoutes("RegisterSuccess")
    object ClubList : NavRoutes("ClubList")
    object Setting : NavRoutes("Setting")
    object Category : NavRoutes("Category")
    object Mypage : NavRoutes("Mypage")
    object Contact : NavRoutes("Contact")
    object NoticeList : NavRoutes("NoticeList")
    object NoticeDetail : NavRoutes("NoticeDetail/{noticeNum}")
    object Alarm : NavRoutes("Alarm")
    object ClubDetail : NavRoutes("ClubDetail/{club}"){
        fun createRoute(club: String) = "club_detail/$club"
    }
    object webView : NavRoutes("webView")

    object CategoryClubList : NavRoutes("CategoryClubList")
}