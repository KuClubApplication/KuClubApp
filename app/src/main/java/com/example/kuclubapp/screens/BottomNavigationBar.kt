package com.example.kuclubapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.R

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(navController.currentBackStackEntry?.destination?.route) }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            selectedItem = destination.route
        }
    }

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary, // 원하는 색상으로 변경
    ) {
        BottomNavigationItem(
            icon = {
                val iconRes = if (selectedItem == NavRoutes.ClubList.route) {
                    R.drawable.home_checked
                } else {
                    R.drawable.home_unchecked
                }
                Image(painter = painterResource(id = iconRes), contentDescription = "ClubList", modifier = Modifier.size(24.dp))
            },
            selected = selectedItem == NavRoutes.ClubList.route,
            onClick = { navController.navigate(NavRoutes.ClubList.route) }
        )
        BottomNavigationItem(
            icon = {
                val iconRes = if (selectedItem == NavRoutes.Category.route) {
                    R.drawable.clue_checked
                } else {
                    R.drawable.clue_unchecked
                }
                Image(painter = painterResource(id = iconRes), contentDescription = "Register", modifier = Modifier.size(24.dp))
            },
            selected = selectedItem == NavRoutes.Category.route,
            onClick = { navController.navigate(NavRoutes.Category.route) }
        )
        BottomNavigationItem(
            icon = {
                val iconRes = if (selectedItem == NavRoutes.Mypage.route) {
                    R.drawable.mypage_checked
                } else {
                    R.drawable.mypage_unchecked
                }
                Image(painter = painterResource(id = iconRes), contentDescription = "Setting", modifier = Modifier.size(24.dp))
            },
            selected = selectedItem == NavRoutes.Mypage.route,
            onClick = { navController.navigate(NavRoutes.Mypage.route) }
        )
        BottomNavigationItem(
            icon = {
                val iconRes = if (selectedItem == NavRoutes.Setting.route) {
                    R.drawable.settings_checked
                } else {
                    R.drawable.settings_unchecked
                }
                Image(painter = painterResource(id = iconRes), contentDescription = "Settings", modifier = Modifier.size(24.dp))
            },
            selected = selectedItem == NavRoutes.Setting.route,
            onClick = { navController.navigate(NavRoutes.Setting.route) }
        )
    }
}