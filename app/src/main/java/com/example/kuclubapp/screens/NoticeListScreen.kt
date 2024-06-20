package com.example.kuclubapp.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kuclubapp.firebaseDB.Notice
import com.example.kuclubapp.viewmodel.NavNoticeViewModel
import com.example.kuclubapp.viewmodel.NavUserViewModel

@Composable
fun NoticeListScreen(navController: NavHostController, navUserViewModel: NavUserViewModel,
                     navNoticeViewModel: NavNoticeViewModel, context:Context) {

    var notices by remember { mutableStateOf<List<Notice>>(emptyList()) }

    LaunchedEffect(navNoticeViewModel) {
        navNoticeViewModel.getAllNotices()
    }

    navNoticeViewModel.notices.observeAsState().value?.let {
        notices = it
    }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(notices) { index, notice ->
                NoticeListItem(notice = notice, onClick = {
                    navController.navigate("NoticeDetail/${notice.noticeNum}")
                })
            }
        }
    }
@Composable
fun NoticeListItem(notice: Notice, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "번호: ${notice.noticeNum}")
            Text(text = "제목: ${notice.noticeTitle}")
            Text(text = "날짜: ${notice.noticeDt}")
        }
    }
}