package com.example.kuclubapp

sealed class NavRoutes(val route: String) {

    object Login : NavRoutes("Login")
    object Register : NavRoutes("Register")
    object ClubList : NavRoutes("ClubList")
    object Setting : NavRoutes("Setting")

}