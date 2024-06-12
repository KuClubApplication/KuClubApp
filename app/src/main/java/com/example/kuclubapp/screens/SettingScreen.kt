package com.example.kuclubapp.screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.R
import com.example.kuclubapp.viewmodel.NavUserViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingScreen(navController: NavController, navUserViewModel: NavUserViewModel) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(
        permission = android.Manifest.permission.POST_NOTIFICATIONS)
    var notificationsEnabled by remember { mutableStateOf(permissionState.status.isGranted) }
    val prevPermissionState = remember { mutableStateOf(permissionState.status.isGranted) }

    LaunchedEffect(permissionState.status.isGranted) {
        notificationsEnabled = permissionState.status.isGranted
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val isGranted = permissionState.status.isGranted
                notificationsEnabled = isGranted

//                if (!isGranted) {
//                    (context as? Activity)?.finish()
//                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFD9FDE8).copy(alpha = 0.6f))
    ) {
        NotificationSettings(notificationsEnabled) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            }
            context.startActivity(intent)
        }
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        CustomerSupport(navController)
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        UserSupport(navController, navUserViewModel)

        Spacer(modifier = Modifier.padding(vertical = 50.dp))
        Text("테스트용 유저 아이디: " + navUserViewModel.userId, fontSize = 20.sp)
    }
}

@Composable
fun NotificationSettings(
    notificationsEnabled: Boolean,
    onClick: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 20.dp)) {
        Text(
            text = "알림",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0F0))
                .padding(vertical = 12.dp)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, size.height + 12.dp.toPx() - strokeWidth / 2),
                        end = Offset(size.width, size.height + 12.dp.toPx() - strokeWidth / 2),
                        strokeWidth = strokeWidth
                    )
                }
                .clickable { onClick() }
        ) {
            Text(
                text = "알림 수신 동의",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = if (notificationsEnabled) "ON" else "OFF",
                fontWeight = FontWeight.Bold,
                color = if (notificationsEnabled) Color(0xFF008000) else Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun CustomerSupport(navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "고객 지원",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        )
        SupportOption(
            label = "공지사항",
            onClick = { navController.navigate(NavRoutes.NoticeList.route) }
        )
        SupportOption(
            label = "고객 문의",
            onClick = { navController.navigate(NavRoutes.Contact.route) }
        )
    }
}

@Composable
fun UserSupport(navController: NavController, navUserViewModel: NavUserViewModel) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "사용자",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        )
        SupportOption(
            label = "로그아웃",
            onClick = {
                navUserViewModel.viewModelScope.launch {
                    navUserViewModel.deleteToken(context)
                    navUserViewModel.loginStatus.value = false
                    navController.navigate(NavRoutes.Login.route)
                }
            }
        )
    }
}

@Composable
fun SupportOption(label: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Color(0xFFF0F0F0))
            .padding(vertical = 8.dp)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height + 8.dp.toPx() - strokeWidth / 2),
                    end = Offset(size.width, size.height + 8.dp.toPx() - strokeWidth / 2),
                    strokeWidth = strokeWidth
                )
            }
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_forward_ios_24),
            contentDescription = "arrow_icon",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
