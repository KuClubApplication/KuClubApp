package com.example.kuclubapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kuclubapp.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    var currentRoute by remember { mutableStateOf(navController.currentBackStackEntry?.destination?.route) }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute = destination.route
        }
    }

    Column {
        TopAppBar(
            {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = when (currentRoute) {
                            NavRoutes.ClubList.route -> "KU 동아리앱"
                            NavRoutes.Category.route -> "카테고리 분류"
                            NavRoutes.Mypage.route -> "마이 페이지"
                            NavRoutes.Setting.route -> "설정"
                            NavRoutes.Contact.route -> "고객 문의"
                            NavRoutes.NoticeList.route -> "공지사항"
                            NavRoutes.NoticeDetail.route -> "상세 공지사항"
                            NavRoutes.Alarm.route -> "알림"
                            NavRoutes.ClubDetail.route -> "상세 페이지"
                            NavRoutes.CategoryClubList.route -> "카테고리 동아리"
                            else -> "App"
                        },
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
            }, navigationIcon = {
                if (currentRoute != NavRoutes.Login.route) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            },
            actions = {
                IconButton(onClick = { navController.navigate(NavRoutes.Alarm.route) }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White, // 배경색을 흰색으로 설정
                titleContentColor = Color(0xFF09612C) // 제목 색상을 검정으로 설정 (필요에 따라)
            )
        )
        Divider(color = Color.Black, thickness = 1.dp) // 하단 검은색 줄 추가
    }
}
