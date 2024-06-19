package com.example.kuclubapp.screens

import android.util.Log
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.R
import com.example.kuclubapp.firebaseDB.Clubs
import com.example.kuclubapp.viewmodel.NavClubViewModel
import com.example.kuclubapp.viewmodel.NavUserViewModel

@Composable
fun CategoryClubScreen(navController: NavHostController, navClubViewModel: NavClubViewModel, navUserViewModel: NavUserViewModel) {

    val clubCategory = navClubViewModel.selectedCategory
    val clubCategoryItems by navClubViewModel.categoryClubs.observeAsState(emptyList())
    if (clubCategory != null) {
        navClubViewModel.getClubsByCategoryId(clubCategory)
    }
    val clubItems by navClubViewModel.clubs.observeAsState(emptyList())
    var btn1IsChecked by remember {
        mutableStateOf(true)
    }
    var btn2IsChecked by remember {
        mutableStateOf(true)
    }
    val (centerClubs, majorClubs) = classifyClubs(clubCategoryItems)
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD9FDE8).copy(alpha = 0.6f))
    ) {
        navClubViewModel.getAllClubs() // Observe the LiveData
        Row(
            modifier = Modifier
                .fillMaxWidth()
                , horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "중앙 동아리", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(20.dp))
            Icon(
                painter = painterResource(id = if (btn1IsChecked) R.drawable.ic_checked else R.drawable.ic_unchecked),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(65.dp)
                    .padding(20.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        btn1IsChecked = !btn1IsChecked
                    }
            )
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(1.dp)
            .border(10.dp, Color.Black))

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, )
            .height(1.dp)
            .border(10.dp, Color.Black))
        //중앙동아리 or 과동아리 분류별로 clubItems 보내면 ok
        if(btn1IsChecked) ClubList(clubItems = centerClubs, navController = navController,navUserViewModel,navClubViewModel)

        Row(
            modifier = Modifier
                .fillMaxWidth()
            , horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "단과대 동아리", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(20.dp))
            Icon(
                painter = painterResource(id = if (btn2IsChecked) R.drawable.ic_checked else R.drawable.ic_unchecked),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(65.dp)
                    .padding(20.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        btn2IsChecked = !btn2IsChecked
                    }
            )
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(1.dp)
            .border(10.dp, Color.Black))
        //중앙동아리 or 과동아리 분류별로 clubItems 보내면 ok
        if(btn2IsChecked) ClubList(clubItems = majorClubs, navController = navController,navUserViewModel,navClubViewModel)

    }
}
fun classifyClubs(clubCategoryItems: List<Clubs>): Pair<List<Clubs>, List<Clubs>> {
    val centerClubs = mutableListOf<Clubs>()
    val majorClubs = mutableListOf<Clubs>()

    for (club in clubCategoryItems) {
        when (club.clubClassification) {
            "중앙 동아리" -> centerClubs.add(club)
            "단과대 동아리" -> majorClubs.add(club)
        }
    }

    return Pair(centerClubs, majorClubs)
}