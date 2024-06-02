package com.example.kuclubapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kuclubapp.NavRoutes
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
               startDestination: String,
               onLoginSuccess: (String) -> Unit) {
    val navStoreOwner = rememberViewModelStoreOwner()

    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {

        Scaffold(
            topBar = {
                if(navUserViewModel.loginStatus.value)
                    TopAppBar(
                        title = { Text(text = "테스트용 탑바") }
                    )
            },
            bottomBar = {
                if(navUserViewModel.loginStatus.value)
                    BottomAppBar(
                    ){
                        Text(text = "테스트용 버텀바")
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
                        ClubListScreen()
                    }

                    composable(NavRoutes.Setting.route) {
                        SettingScreen(navController, navUserViewModel)
                    }
                }
            }

        }
    }

}