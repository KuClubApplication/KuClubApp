package com.example.kuclubapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.R
import com.example.kuclubapp.data.ClubItem
import com.example.kuclubapp.firebaseDB.Clubs
import com.example.kuclubapp.viewmodel.NavClubViewModel
import com.example.kuclubapp.viewmodel.NavUserViewModel
import com.example.kuclubapp.viewmodel.UserRepository
import com.example.kuclubapp.viewmodel.UserViewModelFactory
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

@Composable
fun ClubListScreen(navController:NavHostController,navClubViewModel:NavClubViewModel) {

    
    val context = LocalContext.current
    val clubItems by navClubViewModel.clubs.observeAsState(emptyList())
    Column (
        modifier = Modifier.fillMaxSize().background(Color(0xFFD9FDE8).copy(alpha = 0.6f))
    ) {
        navClubViewModel.getAllClubs() // Observe the LiveData

        ClubList(clubItems = clubItems, navController = navController)

    }
}



@Composable
fun ClubList(clubItems: List<Clubs>, navController: NavHostController) {
    LazyColumn {
        itemsIndexed(clubItems) { index, club ->
            val topPadding = if (index == 0) 31.dp else 15.dp
            ClubListItem(club = club, topPadding = topPadding,navController)
        }
    }
}

@Composable
fun ClubListItem(club: Clubs, topPadding: Dp,navController: NavHostController) {
    val gson = Gson()
    val clubJson = gson.toJson(club)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = topPadding, start = 22.dp,end = 20.dp)
            .height(100.dp)
            .border(2.dp, Color(0xFFD0CCCC), shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable {
                navController.navigate("ClubDetail/${clubJson}")
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.konkuk_logo),
            contentDescription = null,
            modifier = Modifier
                .size(78.dp)
                .padding(12.dp)
                .clip(RoundedCornerShape(200.dp)).align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = club.clubCategory, fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = club.clubName,fontSize = 20.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.align(Alignment.Bottom).padding(bottom = 10.dp, end = 15.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_heart),
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(24.dp).padding(end = 4.dp)
            )
            Text(
                text = "${club.clubLikes} likes",
                color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Bold ,
                modifier = Modifier
            )
        }
    }
}


