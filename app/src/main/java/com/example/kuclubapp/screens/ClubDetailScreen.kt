package com.example.kuclubapp.screens

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.data.ClubDetails
import com.example.kuclubapp.R
import com.example.kuclubapp.firebaseDB.Clubs
import com.example.kuclubapp.viewmodel.NavClubViewModel
import com.google.gson.Gson
import java.lang.reflect.Member

@Composable
fun ClubDetailScreen(navController: NavHostController,navClubViewModel: NavClubViewModel) {
    //추후에는 인자로 객체 전달 받기로 수정
    val clubDetails = navClubViewModel.selectedClub
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
                if (clubDetails != null) {
                    Text(text = clubDetails.clubName, fontWeight = FontWeight.ExtraBold, fontSize = 27.sp,modifier = Modifier.padding(top = 15.dp))
                }
                if (clubDetails != null) {
                    val defaultImageUrl = "https://firebasestorage.googleapis.com/v0/b/ku-club-management.appspot.com/o/koo.png?alt=media&token=50ed63cd-8588-46e1-9189-830dfd09ce19"

                    AsyncImage(
                        model = if (clubDetails.clubImgUrl.isNullOrEmpty()) defaultImageUrl else clubDetails.clubImgUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(78.dp)
                            .padding(12.dp)
                            .clip(RoundedCornerShape(200.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                if (clubDetails != null) {
                    Text(text = clubDetails.clubCategory,  color = Color.Gray, fontSize = 16.sp,fontWeight = FontWeight.Bold)
                }
                if (clubDetails != null) {
                    Text(text = clubDetails.clubClassification,  color = Color.Gray,fontSize = 16.sp,fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            if (clubDetails != null) {
                ClubInfoSection(clubDetails,navController)
            }
        }

        item {
            if (clubDetails != null) {
                clubDetails.clubIntroduction?.let { ClubIntroductionSection(it) }
            }
        }

        item {
            if (clubDetails != null) {
                clubDetails.clubActivities?.let { ClubActivitiesSection(it) }
            }
        }

        item {
            if (clubDetails != null) {
                clubDetails.clubRecruitment?.let { ClubRecruitmentSection(it) }
            }
        }
    }
}


@Composable
fun ClubInfoSection(clubDetails: Clubs,navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            .background(Color.White),
    ) {
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
            Column {
                Text(
                    text = "정보",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                )
                Text(
                    text = clubDetails.clubCategory,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
            Row(modifier = Modifier
                .padding(end = 20.dp)
                .align(Alignment.CenterVertically)) {
                clubDetails.clubInsta?.let { Log.d("테스트", it) }
                if(clubDetails.clubInsta != null) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_instagram),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(top = 8.dp)
                            .align(Alignment.CenterVertically).clickable {
                                navController.navigate("webView")
                            }
                    )
                }
            }


        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(1.dp)
            .border(10.dp, Color.Black))
        Row(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp,bottom=12.dp), horizontalArrangement = Arrangement.SpaceBetween ) {
            Text(text = "회장", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            clubDetails.clubPresident?.let { Text(text = it,fontWeight = FontWeight.Bold, fontSize = 15.sp) }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp,bottom=12.dp), horizontalArrangement = Arrangement.SpaceBetween ) {
            Text(text = "연락처", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            clubDetails.clubContactNum?.let { Text(text = it,fontWeight = FontWeight.Bold, fontSize = 15.sp) }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp,bottom=12.dp), horizontalArrangement = Arrangement.SpaceBetween ) {
            Text(text = "설립일", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            clubDetails.clubEstablishedDate?.let { Text(text = it,fontWeight = FontWeight.Bold, fontSize = 15.sp) }
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
fun ClubActivitiesSection(activities: String) {
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
        Text(text = "- "+activities, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp, end = 20.dp,top = 10.dp))
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
            .padding(bottom = 40.dp)
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
