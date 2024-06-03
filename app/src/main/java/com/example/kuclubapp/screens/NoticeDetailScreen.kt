package com.example.kuclubapp.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.kuclubapp.viewmodel.NavNoticeViewModel
import com.example.kuclubapp.viewmodel.NavUserViewModel

@Composable
fun NoticeDetailScreen(navController: NavHostController, navUserViewModel: NavUserViewModel,
                       navNoticeViewModel: NavNoticeViewModel
) {
    /* 아직 수정 중인 파일 (noticeNum을 인자로 받고, DAO단까지 가서 그 값으로 Notice 객체 가져와야 할 듯. */
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            elevation = 4.dp
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Text(
//                    text = "번호: ${notice.noticeNum}",
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                Text(
//                    text = "제목: ${notice.noticeTitle}",
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                Text(
//                    text = "날짜: ${notice.noticeDt}",
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                Text(
//                    text = "내용: ${notice.noticeContent}",
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = { navController.popBackStack() },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = "Back")
//        }
//    }
}