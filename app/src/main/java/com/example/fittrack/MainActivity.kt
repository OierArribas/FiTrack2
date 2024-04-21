package com.example.fittrack

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fittrack.login.RetrofitInstance
import com.example.fittrack.login.UserRepoImpl
import com.example.fittrack.ui.Navigation.AppNavigation
import com.example.fittrack.ui.ViewModels.MainViewModel
import com.example.fittrack.ui.ViewModels.SettingsViewModel
import com.example.fittrack.ui.theme.FitTrackTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import com.example.fittrack.ui.ViewModels.LoginViewModel


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val settingsViewModel by viewModels<SettingsViewModel>()

    private val mainViewModel by viewModels<MainViewModel>()

    private val loginViewModel by viewModels<LoginViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(UserRepoImpl(RetrofitInstance.api)) as T

            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }
        setContent {

            val postNotificationPermission=
                rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)
            val waterNotificationService=WaterNotificationService(this)



            val darkTheme = settingsViewModel.theme.collectAsState(initial = false)
            FitTrackTheme (darkTheme = darkTheme.value) {
                 Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    //Camera(applicationContext = applicationContext, loginViewModel = loginViewModel)

                    settingsViewModel.setLanguague()

                    AppNavigation(settingsViewModel = settingsViewModel, mainViewModel = mainViewModel, loginViewModel = loginViewModel, applicationContext = applicationContext)
                }
            }
        }
    }
    //Camera permissions

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.POST_NOTIFICATIONS
        )
    }

}







