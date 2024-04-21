package com.example.fittrack.ui.Screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fittrack.ui.Navigation.NavItem
import com.example.fittrack.ui.ViewModels.LoginResultHandler
import com.example.fittrack.ui.ViewModels.LoginViewModel




@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginSuccess by remember { mutableStateOf(loginViewModel.loginSuccess) }
    var reason by remember { mutableStateOf(loginViewModel.respuesta) }

    if (loginSuccess) {
        reason = "succes"
        navController.navigate(route = NavItem.ScreenPrincipal.route)
    }
    Surface(
        //color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Login", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {  }),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Row {
                Button(
                    onClick = {
                        loginViewModel.requestLogin(username, password, object : LoginResultHandler {
                            override fun onLoginResult(success: Boolean) {
                                // Actualiza el estado del inicio de sesión según el resultado
                                loginSuccess = success
                            }
                        })
                         },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Login")
                }
                Button(
                    onClick = {
                        loginViewModel.subscribeTopic()
                        navController.navigate(route = NavItem.ScreenPrincipal.route)},
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Guest")
                }
                Button(
                    onClick = { loginViewModel.requestRegister(username, password)},
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Register")
                }
            }
        }
    }
}

