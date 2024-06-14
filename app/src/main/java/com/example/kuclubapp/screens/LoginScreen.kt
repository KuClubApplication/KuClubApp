package com.example.kuclubapp.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kuclubapp.DataStoreManager
import com.example.kuclubapp.IdTokenRequest
import com.example.kuclubapp.LoginRequest
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.R
import com.example.kuclubapp.RetrofitClient
import com.example.kuclubapp.ServerResponse
import com.example.kuclubapp.TokenResponse
import com.example.kuclubapp.viewmodel.NavUserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(navController: NavHostController,
                navUserViewModel: NavUserViewModel,
                onLoginSuccess: (String) -> Unit) {

    val context = LocalContext.current

    var userId by remember {
        mutableStateOf("")
    }

    var userPasswd by remember {
        mutableStateOf("")
    }

    var isChecked by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isChecked) {
        DataStoreManager.saveAutoLoginStatus(context, isChecked)
    }

    LaunchedEffect(Unit) {
        val autoLoginStatus = DataStoreManager.getAutoLoginStatus(context).first()
        if (autoLoginStatus != null) {
            isChecked = autoLoginStatus
        }
    }

    val colors = listOf(Color(0xFF7FFF00), Color.White, Color.White, Color.White)
    val brush = Brush.verticalGradient(colors)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box{
                Image(
                    painter = painterResource(id = R.drawable.konkuk_logo),
                    contentDescription = "main_logo",
                    modifier = Modifier
                        .padding(20.dp)
                        .graphicsLayer(alpha = 0.5f)
                )
                Text("KU 동아리",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Serif),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            OutlinedTextField(
                value = userId?:"",
                onValueChange = {
                    if (!it.endsWith("@konkuk.ac.kr")) {
                        userId = it.removeSuffix("@konkuk.ac.kr") + "@konkuk.ac.kr"
                    } else {
                        userId = it
                    }
                },
                label = {Text("아이디 입력")},
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.outline_person_24),
                        contentDescription = "id_icon"
                    )
                },
                modifier = Modifier.padding(10.dp)
            )

            OutlinedTextField(
                value = userPasswd?:"",
                onValueChange = { userPasswd = it },
                label = { Text("비밀번호 입력") },
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_lock_outline_24),
                        contentDescription = "pw_icon"
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.padding(10.dp)
            )

            Button(onClick = {
                navUserViewModel.getValidUser(userId, userPasswd) { success ->
                    if (success) {
                        Toast.makeText(context, "사용자 인증을 위해 잠시만 기다려주세요.", Toast.LENGTH_SHORT).show()
                        navUserViewModel.setUserInfo(userId)
                        login(context, userId, userPasswd, onLoginSuccess)
                    } else {
                        Toast.makeText(context, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            },
                modifier = Modifier
                    .width(300.dp)
                    .height(80.dp)
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0A3711),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("LOGIN", fontSize = 25.sp)
            }

            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF7FFF00))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "자동 로그인")

                Spacer(modifier = Modifier.width(60.dp))
                Row(
                    modifier = Modifier
                        .clickable(onClick = {
                            navController.navigate(NavRoutes.Register.route)
                        })
                        .padding(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_person_add_alt_1_24),
                        contentDescription = "register_icon"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "회원가입")
                }
            }
        }
    }
}

private fun login(context: Context, userId: String, password: String, onLoginSuccess: (String) -> Unit) {
    val loginRequest = LoginRequest(userId, password)
    RetrofitClient.instance.login(loginRequest).enqueue(object : Callback<TokenResponse> {
        override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
            if (response.isSuccessful) {
                val customToken = response.body()?.token
                if (customToken != null) {
                    // 커스텀 토큰으로 Firebase에 로그인
                    FirebaseAuth.getInstance().signInWithCustomToken(customToken)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Firebase 로그인 성공
                                val user = FirebaseAuth.getInstance().currentUser
                                user?.getIdToken(true)?.addOnCompleteListener { tokenTask ->
                                    if (tokenTask.isSuccessful) {
                                        val idToken = tokenTask.result?.token
                                        if (idToken != null) {
                                            // ID 토큰을 서버로 전송
                                            sendIdTokenToServer(context, idToken)
                                            onLoginSuccess(idToken)
                                        }
                                    } else {
                                        // ID 토큰 획득 실패 처리
                                        Toast.makeText(context, "Failed to get ID token", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                // Firebase 로그인 실패 처리
                                Toast.makeText(context, "Custom token login failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } else {
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
            Log.e("LoginError", "onFailure: ", t)
        }
    })
}

private fun sendIdTokenToServer(context: Context, idToken: String) {
    val idTokenRequest = IdTokenRequest(idToken)
    RetrofitClient.instance.sendIdToken(idTokenRequest).enqueue(object : Callback<ServerResponse> {
        override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
            if (response.isSuccessful) {
                val serverResponse = response.body()
                if (serverResponse != null && serverResponse.success) {
                    Log.d("ServerResponse", "ID token successfully sent to server: ${serverResponse.message}")
                    Toast.makeText(context, "ID token successfully sent to server", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ServerResponse", "Failed to send ID token to server: ${serverResponse?.message}")
                    Toast.makeText(context, "Failed to send ID token to server", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("ServerResponse", "Failed to send ID token to server: ${response.errorBody()?.string()}")
                Toast.makeText(context, "Failed to send ID token to server", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
            Log.e("ServerResponse", "Error sending ID token to server", t)
            Toast.makeText(context, "Error sending ID token to server", Toast.LENGTH_SHORT).show()
        }
    })
}
