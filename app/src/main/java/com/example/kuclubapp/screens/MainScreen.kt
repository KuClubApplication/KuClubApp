package com.example.kuclubapp.screens

import CategoryScreen
import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.viewmodel.NavClubViewModel
import com.example.kuclubapp.viewmodel.NavNoticeViewModel
import com.example.kuclubapp.viewmodel.NavUserViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) { context as ViewModelStoreOwner }
}

val LocalNavGraphViewModelStoreOwner =
    staticCompositionLocalOf<ViewModelStoreOwner> {
        error("Undefined")
    }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(navController: NavHostController, navUserViewModel: NavUserViewModel,
               navNoticeViewModel: NavNoticeViewModel, navClubViewModel: NavClubViewModel,
               startDestination: String,
               onLoginSuccess: (String) -> Unit) {
    val navStoreOwner = rememberViewModelStoreOwner()

    val context = LocalContext.current
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    var showDialog by remember {
        mutableStateOf(false)
    }

    val requestPermissionLauncher
    = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted)
            Toast.makeText(context, "알림 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
        else
            if (!permissionState.status.shouldShowRationale)
                showDialog = true
    }

    LaunchedEffect(key1 = permissionState) {
        if(!permissionState.status.isGranted && !permissionState.status.shouldShowRationale)
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
    }

    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {

        Scaffold(
            topBar = {
                if(navUserViewModel.loginStatus.value)
                    TopBar(navController)
            },
            bottomBar = {
                if(navUserViewModel.loginStatus.value)
                    BottomNavigationBar(navController)
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

                    composable(NavRoutes.RegisterSuccess.route) {
                        RegisterSuccessScreen(navController, navUserViewModel)
                    }

                    composable(NavRoutes.ClubList.route) {
                        ClubListScreen(navController,navClubViewModel,navUserViewModel)
                    }

                    composable(NavRoutes.Setting.route) {
                        SettingScreen(navController, navUserViewModel)
                    }

                    composable(NavRoutes.Category.route) {
                        CategoryScreen(navController, navUserViewModel)
                    }

                    composable(NavRoutes.Mypage.route) {
                        MypageScreen(navController, navUserViewModel, navClubViewModel)
                    }

                    composable(NavRoutes.Contact.route) {
                        ContactInfoScreen(navController, navUserViewModel)
                    }

                    composable(NavRoutes.NoticeList.route) {
                        NoticeListScreen(navController, navUserViewModel, navNoticeViewModel, context)
                    }

                    composable(
                        route = "NoticeDetail/{noticeNum}",
                        arguments = listOf(navArgument("noticeNum") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val noticeNum = backStackEntry.arguments?.getInt("noticeNum")
                        NoticeDetailScreen(noticeNum, navController, navUserViewModel, navNoticeViewModel)
                    }

                    composable(NavRoutes.Alarm.route) {
                        AlarmScreen(navController, navUserViewModel)
                    }
                    composable(
                        route = NavRoutes.ClubDetail.route,
                        arguments = listOf(navArgument("club") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val clubJson = backStackEntry.arguments?.getString("club")
                        if (clubJson != null) {
                            ClubDetailScreen(navController,club = clubJson)
                        }
                    }
                    composable(
                        route = NavRoutes.webView.route,
                        arguments = listOf(navArgument("url") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val url = backStackEntry.arguments?.getString("url")
                            if (url != null) {
                                openWebView(url)
                            }
                    }
                    composable(NavRoutes.CategoryClubList.route) {
                        CategoryClubScreen(navController,navClubViewModel,navUserViewModel)
                    }
                }
            }

        }
    }

}


