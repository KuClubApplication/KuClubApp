package com.example.kuclubapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kuclubapp.GMailSender
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.R
import com.example.kuclubapp.firebaseDB.User
import com.example.kuclubapp.viewmodel.NavUserViewModel

@Composable
fun RegisterScreen(navController: NavHostController, navUserViewModel: NavUserViewModel) {

    val context = LocalContext.current

    var userId by remember {
        mutableStateOf("")
    }

    var userPasswd by remember {
        mutableStateOf("")
    }

    var userPasswdCheck by remember {
        mutableStateOf("")
    }

    var userNm by remember {
        mutableStateOf("")
    }

    var userMajor by remember {
        mutableStateOf("")
    }

    var userAuthNum by remember {
        mutableStateOf("")
    }

    var randomNum by remember {
        mutableStateOf("")
    }

    val user = User(userId, userPasswd, userNm, userMajor)

    val passwordRegex = Regex("^.{6,}\$")

    var expanded by remember { mutableStateOf(false) }
    var selectedMajor by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "아이디",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                "• 건국대학교 이메일 형식으로 입력하세요.",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            )

            OutlinedTextField(
                value = userId?:"",
                onValueChange = {
                    if (!it.endsWith("@konkuk.ac.kr")) {
                        userId = it.removeSuffix("@konkuk.ac.kr") + "@konkuk.ac.kr"
                    } else {
                        userId = it
                    }
                },
                label = { Text("ex) kuclub@konkuk.ac.kr") },
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_mail_outline_24),
                        contentDescription = "id_icon"
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.padding(10.dp)
            )
        }

        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "비밀번호",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                "• 정확한 비밀번호를 입력하세요.",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 15.sp
                )
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next),
                modifier = Modifier.padding(10.dp)
            )

            if (!passwordRegex.matches(userPasswd)) {
                Text("비밀번호는 최소 6자 이상이어야 합니다.", color = Color.Red,
                    modifier = Modifier.padding(horizontal = 10.dp))
            }
        }

        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "비밀번호 확인",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            OutlinedTextField(
                value = userPasswdCheck?:"",
                onValueChange = { userPasswdCheck = it },
                label = { Text("비밀번호 입력") },
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_lock_outline_24),
                        contentDescription = "pw_icon"
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next),
                modifier = Modifier.padding(10.dp)
            )

            if (userPasswd != userPasswdCheck) {
                Text("입력한 비밀번호가 일치하지 않습니다.", color = Color.Red,
                    modifier = Modifier.padding(horizontal = 10.dp))
            }
        }

        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "이름",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            OutlinedTextField(
                value = userNm?:"",
                onValueChange = { userNm = it },
                label = { Text("이름 입력") },
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.outline_person_24),
                        contentDescription = "nm_icon"
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }

        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "학과",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(horizontal = 55.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 55.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedMajor,
                    onValueChange = { selectedMajor = it },
                    label = { Text("학과 선택") },
                    leadingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_menu_book_24),
                            contentDescription = "major_icon"
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    readOnly = true
                )

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier
                        .size(56.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_drop_down_24),
                        contentDescription = "dropdown_icon"
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(200.dp)
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
            ) {
                listOf("컴퓨터공학부", "국어국문학과", "영어영문학과", "중어중문학과", "철학과", "사학과",
                    "지리학과", "미디어커뮤니케이션학과", "문화콘텐츠학과", "수학과", "물리학과", "화학과",
                    "건축학부", "사회환경공학부", "기계항공공학부", "전기전자공학부", "화학공학부",
                    "산업공학과", "생물공학과").forEach { major ->
                    DropdownMenuItem(
                        text = { Text(major) },
                        onClick = {
                            selectedMajor = major
                            userMajor = major
                            expanded = false
                        }
                    )
                }
            }
        }

        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "인증번호",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                "• 이메일로 받은 인증번호를 입력하세요.",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            )

            OutlinedTextField(
                value = userAuthNum?:"",
                onValueChange = { userAuthNum = it },
                label = { Text("인증번호 입력") },
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_cloud_queue_24),
                        contentDescription = "email_icon"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done),
                modifier = Modifier.padding(10.dp)
            )
        }

        Row {
            Button(
                onClick = {
                    randomNum = (100000..999999).random().toString()
                    Toast.makeText(context, "이메일 전송에 10초 정도 소요됩니다.", Toast.LENGTH_SHORT).show()
                    GMailSender().sendEmail(userId, randomNum)
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(85.dp)
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0A3711),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("이메일 \n인증 요청", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
            Button(
                onClick = {
                    if (!passwordRegex.matches(userPasswd)) {
                        Toast.makeText(context, "비밀번호는 최소 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                    }
                    else if (userPasswd != userPasswdCheck) {
                        Toast.makeText(context, "비밀번호가 서로 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else if (userAuthNum == randomNum) {
                        navUserViewModel.insertUser(user)
                        navController.navigate(NavRoutes.RegisterSuccess.route)
                    } else {
                        Toast.makeText(context, "인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(85.dp)
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0A3711),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("회원가입", fontSize = 20.sp)
            }
        }
    }
}