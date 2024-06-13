package com.example.kuclubapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.R
import com.example.kuclubapp.viewmodel.NavUserViewModel

@Composable
fun RegisterSuccessScreen(navController: NavHostController, navUserViewModel: NavUserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "회원 가입이\n완료되었습니다.",
            style = TextStyle(
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_add_task_24),
            contentDescription = "check_icon",
            tint = Color.Green,
            modifier = Modifier.size(100.dp).padding(bottom = 16.dp)
        )

        Button(
            onClick = { navController.navigate(NavRoutes.Login.route) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0A3711)
            )
        ) {
            Text(
                text = "로그인 화면으로 이동하기",
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
