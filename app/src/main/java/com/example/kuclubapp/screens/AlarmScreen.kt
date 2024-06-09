package com.example.kuclubapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kuclubapp.viewmodel.NavUserViewModel

@Composable
fun AlarmScreen(navController: NavHostController, navUserViewModel: NavUserViewModel){
    val notifications = listOf("KUIT에서 새로운 공지사항을 등록했습니다.",
        "GDSC Konkuk에서 새로운 공지사항을 등록했습니다.",
        "CONA에서 새로운 공지사항을 등록했습니다.")

    Column {
        Row {
            Text(text = "알림",
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "읽음",
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp))
        }
        NotificationList(notifications = notifications)
    }
}

@Composable
fun NotificationItem(
    notificationText: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val backgroundColor = if (isChecked) Color(0xFFDDDDDD) else Color(0xFFCCFFCC)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) }
            .background(backgroundColor)
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
            text = notificationText,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 15.dp)
        )

        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF7FFF00),
                uncheckedColor = Color.Gray
            )
        )
    }
}

@Composable
fun NotificationList(notifications: List<String>) {
    var checkedStates by remember { mutableStateOf(List(notifications.size) { false }) }

    LazyColumn {
        items(notifications) { notification ->
            NotificationItem(
                notificationText = notification,
                isChecked = checkedStates[notifications.indexOf(notification)]
            ) { isChecked ->
                val index = notifications.indexOf(notification)
                checkedStates = checkedStates.toMutableList().also {
                    it[index] = isChecked
                }
            }
        }
    }

}