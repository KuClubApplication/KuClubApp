package com.example.kuclubapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.R
import com.example.kuclubapp.firebaseDB.Clubs
import com.example.kuclubapp.viewmodel.NavClubViewModel
import com.example.kuclubapp.viewmodel.NavUserViewModel

@Composable
fun MypageScreen(navController: NavHostController, navUserViewModel: NavUserViewModel, navClubViewModel: NavClubViewModel) {

    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxSize().background(Color(0xFFD9FDE8).copy(alpha = 0.6f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text("마이 페이지", fontSize = 40.sp)
//        Text("유저 아이디: " + navUserViewModel.userId, fontSize = 20.sp)
//        Button(
//            onClick = { navController.navigate(NavRoutes.Setting.route) },
//                modifier = Modifier
//                .width(300.dp)
//                .height(80.dp)
//                .padding(10.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color(0xFF0A3711),
//                contentColor = Color.White
//            ),
//            shape = MaterialTheme.shapes.medium
//        ) {
//            Text("세팅 페이지 이동", fontSize = 25.sp)
//        }
        Spacer(modifier = Modifier.height(40.dp))
        Image(painter = painterResource(id = R.drawable.outline_person_24),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(8.dp, Color.LightGray, CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = navUserViewModel.userName.toString(), fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = navUserViewModel.userMajor.toString(), fontSize = 25.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(24.dp))
        Card (
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "관심 동아리 목록", fontSize = 25.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp))
            var userId = navUserViewModel.userId?:""    // userId에 @ 같이 있어서 없앰
            if (userId.contains("@")){
                userId = userId.split("@")[0]
            }

            val clubLikedList = navClubViewModel.clubLiked.value
                if(clubLikedList == null || clubLikedList.size == 0) {
                    noLikedClub(userId, navClubViewModel)
                }
                else {
                    likedClubUI(clubLikedList)
                }
        }
    }
}
@Composable
fun noLikedClub(userId:String, navClubViewModel:NavClubViewModel){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // 테두리로부터 16.dp 간격을 줍니다
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .height(300.dp)
                    .clickable { testInsert(userId, navClubViewModel) }, // test 용 함수 (추후 제거 예정)
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.koo), // 여기에 원하는 이미지 리소스를 넣습니다.
                        contentDescription = "No clubs",
                        modifier = Modifier.size(200.dp) // 이미지 크기를 설정합니다.
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // 이미지와 텍스트 사이의 간격을 설정합니다.
                    Text(
                        text = "관심 동아리가 없습니다",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray // 텍스트 색상을 설정합니다.
                    )
                }
            }
        }
    }
}

@Composable
fun likedClubUI(clubLikedList:List<Clubs>) {
    LazyColumn {
        items(clubLikedList) {index ->
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .border(5.dp, Color.LightGray, RoundedCornerShape(10.dp))
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column (
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(250.dp),
                    verticalArrangement = Arrangement.Center,

                ){
                    Text(
                        text = index.clubClassification,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(start = 15.dp, )
                    )
                    Text(
                        text = index.clubName,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
                        )
                }
                Row (
                    modifier = Modifier.fillMaxWidth().padding(end = 20.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ){
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite Icon",
                        tint = Color.Red,
                        modifier = Modifier.size(32.dp) // 아이콘 크기 설정
                    )
                    Text(
                        text = "Likes " + index.clubLikes,
                        fontSize = 20.sp
                        )
                }

            }
        }
    }
}
fun testInsert(userId: String, navClubViewModel:NavClubViewModel){
//    val club1 = Clubs(1, 0,
//        "no.1 Name", "noClassification",
//        "noCategory"
//    )
//    val club2 = Clubs(2, 0,
//        "no.2 Name", "noClassification",
//        "noCategory"
//    )
//    navClubViewModel.insertClub(club1)
//    navClubViewModel.insertClub(club2)
//    navClubViewModel.deleteLiked(userId, club1)
//    navClubViewModel.deleteLiked(userId, club2)
//    navClubViewModel.insertLiked(userId, 1)
//    navClubViewModel.insertLiked(userId, 2)
//    navClubViewModel.getAllLiked(userId)
}