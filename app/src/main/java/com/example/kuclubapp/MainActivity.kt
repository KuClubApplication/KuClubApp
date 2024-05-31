package com.example.kuclubapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.kuclubapp.screens.MainScreen
import com.example.kuclubapp.viewmodel.NavUserViewModel
import com.example.kuclubapp.viewmodel.UserRepository
import com.example.kuclubapp.viewmodel.UserViewModelFactory
import com.example.myauth2.RetrofitClient
import com.example.myauth2.VerifyTokenRequest
import com.example.myauth2.VerifyTokenResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val db = Firebase.firestore
            val db = Firebase.database

            val context = LocalContext.current
            val tokenFlow = DataStoreManager.getToken(context).collectAsState(initial = null)
            val autoLoginFlow = DataStoreManager.getAutoLoginStatus(context).collectAsState(initial = null)

            val viewModel: NavUserViewModel =
                viewModel(factory = UserViewModelFactory(UserRepository(db)))
            val navController = rememberNavController()

            var startDestination by remember { mutableStateOf(NavRoutes.Login.route) }

            LaunchedEffect(tokenFlow.value) {
                tokenFlow.value?.let { token ->
                    Log.i("token", "LaunchedEffect:tokenFlow")
                    verifyToken(context, token) { isValid ->
                        if (isValid) {
                            val userId = getUserIdFromToken(token)
                            viewModel.setUserInfo(userId)
                            startDestination = NavRoutes.ClubList.route
                        } else {
                            startDestination = NavRoutes.Login.route
                        }
                        navController.navigate(startDestination) {
                            if (!isValid) {
                                popUpTo(NavRoutes.Login.route) { inclusive = true }
                            }
                        }
                    }
                }
            }

            MainScreen(
                navController = navController,
                navUserViewModel = viewModel,
                startDestination = startDestination,
                onLoginSuccess = { token ->
                    lifecycleScope.launch {
                        Log.i("token", "LoginScreen")
                        if (autoLoginFlow.value == true) {
                            DataStoreManager.saveToken(context, token)
                        }
                        navController.navigate(NavRoutes.ClubList.route) {
                            popUpTo(NavRoutes.Login.route) { inclusive = true }
                        }
                    }
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun getUserIdFromToken(token: String): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid
        return uid.toString()
    }

    private fun verifyToken(context: Context, token: String, callback: (Boolean) -> Unit) {
        Log.i("token", "verifyToken")
        val verifyTokenRequest = VerifyTokenRequest(token)
        RetrofitClient.instance.verifyToken(verifyTokenRequest).enqueue(object :
            Callback<VerifyTokenResponse> {
            override fun onResponse(call: Call<VerifyTokenResponse>, response: Response<VerifyTokenResponse>) {
                if (response.isSuccessful && response.body()?.message == "Valid JWT") {
                    Toast.makeText(context, "Token is valid", Toast.LENGTH_SHORT).show()
                    callback(true)
                } else {
                    Toast.makeText(context, "Token is invalid", Toast.LENGTH_SHORT).show()
                    callback(false)
                }
            }

            override fun onFailure(call: Call<VerifyTokenResponse>, t: Throwable) {
                Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
                callback(false)
            }
        })
    }
}
