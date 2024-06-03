package com.example.kuclubapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.viewmodel.NavUserViewModel

@Composable
fun MypageScreen(navController: NavHostController, navUserViewModel: NavUserViewModel) {

    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("마이 페이지", fontSize = 40.sp)
        Text("유저 아이디: " + navUserViewModel.userId, fontSize = 20.sp)
        Button(
            onClick = { navController.navigate(NavRoutes.Setting.route) },
                modifier = Modifier
                .width(300.dp)
                .height(80.dp)
                .padding(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0A3711),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("세팅 페이지 이동", fontSize = 25.sp)
        }
    }
}
