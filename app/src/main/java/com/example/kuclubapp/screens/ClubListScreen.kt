package com.example.kuclubapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.R
import com.example.kuclubapp.data.ClubItem
import com.example.kuclubapp.viewmodel.NavUserViewModel
import com.example.kuclubapp.viewmodel.UserRepository
import com.example.kuclubapp.viewmodel.UserViewModelFactory
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Preview
@Composable
fun TestScreen(){
    val viewModel: NavUserViewModel =
        viewModel(factory = UserViewModelFactory(UserRepository(Firebase.database)))
    val navController = rememberNavController()
    ClubListScreen()
}
@Composable
fun ClubListScreen() {

    
    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val clubItems = listOf(
            ClubItem(R.drawable.konkuk_logo, "중앙동아리", "목방", 13),
            ClubItem(R.drawable.konkuk_logo, "중앙동아리", "목방", 13),
            ClubItem(R.drawable.konkuk_logo, "중앙동아리", "목방", 13),
        ) //임시 더미데이터
        ClubList(clubItems = clubItems)

    }
}



@Composable
fun ClubList(clubItems: List<ClubItem>) {
    LazyColumn {
        items(clubItems){
            ClubListItem(club = it)
        }
    }
}
@Composable
fun ClubListItem(club: ClubItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = club.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .padding(end = 8.dp),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = club.category,
                color = Color.Gray
            )
            Text(
                text = club.name,
                color = Color.Black
            )
        }
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_mail_outline_24), //하트 아이콘으로 수정해야됨!!
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "${club.likes} likes",
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}
