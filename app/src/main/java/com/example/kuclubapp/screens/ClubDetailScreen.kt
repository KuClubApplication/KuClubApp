package com.example.kuclubapp.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import java.lang.reflect.Member

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
        foundingDate = "2023-xx-xx",
        instagram = "Instagram",
        introduction = "GDSC(Google Developer Student Clubs)는 ...GDSC(Google Developer Student Clubs)는 ...GDSC(Google Developer Student Clubs)는 ...GDSC(Google Developer Student Clubs)는 ...GDSC(Google Developer Student Clubs)는 ...GDSC(Google Developer Student Clubs)는 ...",
        activities = listOf("정기 모임", "신입생 프로젝트", "Google Solution Challenge", "학술 공유회"),
        recruitment = " GDSC Konkuk Core Member 지원 자격\n"+
            "1. 공대생이어야 함\n"+
            "2. GDSC 활동에 적극적으로 참여할 수 있는 사람\n"+
            "GDSC Konkuk 모집 분야\n"+
            "- Developer Relations\n"+
            "- UX/UI Designer\n"+
            "- Android\n"+
            "- iOS\n"+
            "- Flutter\n"+
            "- Backend\n"+
            "- Web Frontend\n"+
            "- AI/ML Engineer"
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
        Row(modifier = Modifier.fillMaxWidth()){
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
            Spacer(modifier = Modifier.width(180.dp))
            Icon(
                painter = painterResource(id = R.drawable.icon_instagram),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(32.dp)
                    .padding(top = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_everytime),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(32.dp)
                    .padding(start = 10.dp, top = 8.dp)
                    .align(Alignment.CenterVertically)
            )


        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(1.dp)
            .border(10.dp, Color.Black))
        Row(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp,bottom=12.dp), horizontalArrangement = Arrangement.SpaceBetween ) {
            Text(text = "회장", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(text = clubDetails.president,fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp,bottom=12.dp), horizontalArrangement = Arrangement.SpaceBetween ) {
            Text(text = "연락처", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(text = clubDetails.contact,fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp,bottom=12.dp), horizontalArrangement = Arrangement.SpaceBetween ) {
            Text(text = "설립일", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(text = clubDetails.foundingDate,fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .height(2.dp)
            .border(10.dp, Color.Black))
    }
}

@Composable
fun ClubIntroductionSection(introduction: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ){
        Text(text = "동아리 소개", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(20.dp))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(1.dp)
            .border(10.dp, Color.Black))
        Text(text = introduction,fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .height(2.dp)
            .border(10.dp, Color.Black))
    }
}

@Composable
fun ClubActivitiesSection(activities: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(text = "활동 내역", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(20.dp))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(1.dp)
            .border(10.dp, Color.Black))
        activities.forEach { activity ->
            Text(text = "- "+activity, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp, end = 20.dp,top = 10.dp))
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .height(2.dp)
            .border(10.dp, Color.Black))
    }
}

@Composable
fun ClubRecruitmentSection(recruitment: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(text = "모집 글", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(20.dp))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(1.dp)
            .border(10.dp, Color.Black))
        Text(text = recruitment,fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp, end = 20.dp,top = 10.dp))
    }
}
