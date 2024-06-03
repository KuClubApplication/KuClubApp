package com.example.kuclubapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kuclubapp.viewmodel.NavUserViewModel

@Composable
fun ContactInfoScreen(controller: NavHostController, navUserViewModel: NavUserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ContactInfoItem("학교 위치", "건국대학교, 서울특별시 광진구 능동로 120")
        Spacer(modifier = Modifier.height(16.dp))
        ContactInfoItem("공식 이메일 주소", "kuclub3official@gmail.com")
        Spacer(modifier = Modifier.height(16.dp))
        ContactInfoItem("개발자", "최예름 박성근 박성준 김종우")
    }
}

@Composable
fun ContactInfoItem(label: String, value: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = label,
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}