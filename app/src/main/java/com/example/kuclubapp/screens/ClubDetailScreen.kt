package com.example.kuclubapp.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.kuclubapp.data.ClubDetails
import com.example.kuclubapp.R
@Composable
fun ClubDetailScreen() {
    //추후에는 인자로 객체 전달 받기로 수정
    val clubDetails = ClubDetails(
        name = "GDSC Konkuk",
        imageRes = R.drawable.konkuk_logo,
        category = "코딩/개발",
        type = "단과 동아리",
        president = "이현우",
        contact = "010-xxxx-xxxx",
        foundingDate = "설립일",
        instagram = "Instagram",
        introduction = "GDSC(Google Developer Student Clubs)는 ...",
        activities = listOf("정기 모임", "신입생 프로젝트", "Google Solution Challenge", "학술 공유회"),
        recruitment = listOf(
            "GDSC Konkuk Core Member 지원 자격",
            "1. 공대생이어야 함",
            "2. GDSC 활동에 적극적으로 참여할 수 있는 사람",
            "GDSC Konkuk 모집 분야",
            "- Developer Relations",
            "- UX/UI Designer",
            "- Android",
            "- iOS",
            "- Flutter",
            "- Backend",
            "- Web Frontend",
            "- AI/ML Engineer"
        )
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD9FDE8))
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = clubDetails.name, fontWeight = FontWeight.ExtraBold, fontSize = 27.sp,modifier = Modifier.padding(top = 15.dp))
                Image(
                    painter = painterResource(id = clubDetails.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(200.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(text = clubDetails.category,  color = Color.Gray, fontSize = 16.sp,fontWeight = FontWeight.Bold)
                Text(text = clubDetails.type,  color = Color.Gray,fontSize = 16.sp,fontWeight = FontWeight.Bold)
            }
        }

        item {
            ClubInfoSection(clubDetails)
        }

        item {
            ClubIntroductionSection(clubDetails.introduction)
        }

        item {
            ClubActivitiesSection(clubDetails.activities)
        }

        item {
            ClubRecruitmentSection(clubDetails.recruitment)
        }
    }
}

@Composable
fun ClubInfoSection(clubDetails: ClubDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            .background(Color.White),
    ) {
        Row(){
            Column {
                Text(
                    text = "정보",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                )
                Text(
                    text = clubDetails.type,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.icon_heart),
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(24.dp).padding(top = 8.dp).align(Alignment.CenterVertically)
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_heart),
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(24.dp).padding(top = 8.dp).align(Alignment.CenterVertically)
            )


        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "회장: ${clubDetails.president}")
        Text(text = "연락처: ${clubDetails.contact}")
        Text(text = "설립일: ${clubDetails.foundingDate}")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Instagram: ")
            Text(text = clubDetails.instagram, color = Color.Blue)
        }
    }
}

@Composable
fun ClubIntroductionSection(introduction: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "동아리 소개")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = introduction)
    }
}

@Composable
fun ClubActivitiesSection(activities: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "활동 내역")
        Spacer(modifier = Modifier.height(8.dp))
        activities.forEach { activity ->
            Text(text = activity)
        }
    }
}

@Composable
fun ClubRecruitmentSection(recruitment: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "모집 글")
        Spacer(modifier = Modifier.height(8.dp))
        recruitment.forEach { recruit ->
            Text(text = recruit)
        }
    }
}
