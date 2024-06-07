package com.example.kuclubapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kuclubapp.viewmodel.NavNoticeViewModel
import com.example.kuclubapp.viewmodel.NavUserViewModel

@Composable
fun NoticeDetailScreen(noticeNum: Int?, navController: NavHostController,
                       navUserViewModel: NavUserViewModel,
                       navNoticeViewModel: NavNoticeViewModel
) {
    val noticeDetail by navNoticeViewModel.noticeDetail.observeAsState()

    LaunchedEffect(noticeNum) {
        noticeNum?.let {
            navNoticeViewModel.getNoticeDetail(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                noticeDetail?.let {
                    NoticeItem("번호", noticeNum.toString())
                    NoticeItem("제목", it.noticeTitle)
                    NoticeItem("날짜", it.noticeDt)
                    NoticeItem("내용", it.noticeContent)
                }
            }
        }
    }
}

@Composable
fun NoticeItem(noticeLabel: String, noticeLabelContent: String) {
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
                text = noticeLabel,
                modifier = Modifier.padding(horizontal = 10.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text(
                text = noticeLabelContent,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}