package com.example.kuclubapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kuclubapp.viewmodel.NavUserViewModel

@Composable
fun ContactInfoScreen(controller: NavHostController, navUserViewModel: NavUserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD9FDE8).copy(alpha = 0.6f))
            .padding(16.dp)
    ) {
        ContactInfoItem("공식 이메일 주소", "kuclub3official@gmail.com")
        Spacer(modifier = Modifier.height(16.dp))
        DeveloperCallInfo()
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
                fontWeight = FontWeight.Black,
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

@Composable
fun DeveloperCallInfo() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Contact",
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        )
        DeveloperInfoItem(
            developerNm = "최예름",
            developerPhone = "Tel: 010-1234-5678"
        )
        DeveloperInfoItem(
            developerNm = "박성준",
            developerPhone = "Tel: 010-1234-5678"
        )
        DeveloperInfoItem(
            developerNm = "박성근",
            developerPhone = "Tel: 010-1234-5678"
        )
        DeveloperInfoItem(
            developerNm = "김종우",
            developerPhone = "Tel: 010-1234-5678"
        )
    }
}

@Composable
fun DeveloperInfoItem(developerNm: String, developerPhone: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0))
            .padding(vertical = 20.dp)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height + 20.dp.toPx() - strokeWidth / 2),
                    end = Offset(size.width, size.height + 20.dp.toPx() - strokeWidth / 2),
                    strokeWidth = strokeWidth
                )
            }
    ) {
        Row {
            Text(
                text = developerNm,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = developerPhone,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}