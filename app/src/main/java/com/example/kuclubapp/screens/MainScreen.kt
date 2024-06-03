@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.example.kuclubapp.screens

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.viewmodel.NavUserViewModel
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.kuclubapp.R

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) { context as ViewModelStoreOwner }
}

val LocalNavGraphViewModelStoreOwner =
    staticCompositionLocalOf<ViewModelStoreOwner> {
        error("Undefined")
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    navUserViewModel: NavUserViewModel,
    startDestination: String,
    onLoginSuccess: (String) -> Unit
) {
    val navStoreOwner = rememberViewModelStoreOwner()

    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {
        Scaffold(
            topBar = {
                if (navController.currentBackStackEntry?.destination?.route == NavRoutes.Register.route
                    || navController.currentBackStackEntry?.destination?.route == NavRoutes.ClubList.route
                    || navController.currentBackStackEntry?.destination?.route == NavRoutes.Setting.route) {
                    TopBar(navController = navController)
                }
            },
            bottomBar = {
                if (navController.currentBackStackEntry?.destination?.route == NavRoutes.Register.route
                    || navController.currentBackStackEntry?.destination?.route == NavRoutes.ClubList.route
                    || navController.currentBackStackEntry?.destination?.route == NavRoutes.Setting.route) {
                    BottomNavigationBar(navController = navController)
                }
            }
        ) { contentPadding ->
            Column(modifier = Modifier.padding(contentPadding)) {
                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable(NavRoutes.Login.route) {
                        LoginScreen(navController, navUserViewModel, onLoginSuccess)
                    }
                    composable(NavRoutes.Register.route) {
                        RegisterScreen(navController, navUserViewModel)
                    }
                    composable(NavRoutes.ClubList.route) {
                        ClubListScreen(navController, navUserViewModel)
                    }
                    composable(NavRoutes.Setting.route) {
                        SettingScreen(navController, navUserViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(navController: NavHostController) {
    var currentRoute by remember { mutableStateOf(navController.currentBackStackEntry?.destination?.route) }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute = destination.route
        }
    }

    TopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = when (currentRoute) {
                        NavRoutes.ClubList.route -> "Club List"
                        NavRoutes.Register.route -> "Register"
                        NavRoutes.Setting.route -> "Settings"
                        else -> "App"
                    },
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        },
        navigationIcon = {
            if (currentRoute != NavRoutes.Login.route) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            IconButton(onClick = { /* 알림 버튼 클릭 처리 */ }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = androidx.compose.ui.graphics.Color.White
        )
    )
}

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
                val iconRes = if (selectedItem == NavRoutes.Register.route) {
                    R.drawable.clue_checked
                } else {
                    R.drawable.clue_unchecked
                }
                Image(painter = painterResource(id = iconRes), contentDescription = "Register", modifier = Modifier.size(24.dp))
            },
            selected = selectedItem == NavRoutes.Register.route,
            onClick = { navController.navigate(NavRoutes.Register.route) }
        )
        BottomNavigationItem(
            icon = {
                val iconRes = if (selectedItem == NavRoutes.Setting.route) {
                    R.drawable.mypage_checked
                } else {
                    R.drawable.mypage_unchecked
                }
                Image(painter = painterResource(id = iconRes), contentDescription = "Setting", modifier = Modifier.size(24.dp))
            },
            selected = selectedItem == NavRoutes.Setting.route,
            onClick = { navController.navigate(NavRoutes.Setting.route) }
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
