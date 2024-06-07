package com.example.kuclubapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
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
import com.example.kuclubapp.viewmodel.NavNoticeViewModel
import com.example.kuclubapp.viewmodel.NavUserViewModel

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
fun MainScreen(navController: NavHostController, navUserViewModel: NavUserViewModel,
               navNoticeViewModel: NavNoticeViewModel,
               startDestination: String,
               onLoginSuccess: (String) -> Unit) {
    val navStoreOwner = rememberViewModelStoreOwner()

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

                    composable(NavRoutes.ClubList.route) {
                        ClubListScreen(navController, navUserViewModel)
                    }

                    composable(NavRoutes.Setting.route) {
                        SettingScreen(navController, navUserViewModel)
                    }

                    composable(NavRoutes.Category.route) {
                        CategoryScreen(navController, navUserViewModel)
                    }

                    composable(NavRoutes.Mypage.route) {
                        MypageScreen(navController, navUserViewModel)
                    }

                    composable(NavRoutes.Contact.route) {
                        ContactInfoScreen(navController, navUserViewModel)
                    }

                    composable(NavRoutes.NoticeList.route) {
                        NoticeListScreen(navController, navUserViewModel, navNoticeViewModel)
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

                }
            }

        }
    }

}


