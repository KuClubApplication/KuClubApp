package com.example.kuclubapp.data

data class ClubDetails(
    val name: String,
    val imageRes: Int,
    val category: String,
    val type: String,
    val president: String,
    val contact: String,
    val foundingDate: String,
    val instagram: String,
    val introduction: String,
    val activities: List<String>,
    val recruitment: String
)
