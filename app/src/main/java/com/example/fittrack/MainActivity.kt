package com.example.fittrack

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.fittrack.ui.Navigation.AppNavigation
import com.example.fittrack.ui.ViewModels.MainViewModel
import com.example.fittrack.ui.ViewModels.SettingsViewModel
import com.example.fittrack.ui.theme.FitTrackTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val settingsViewModel by viewModels<SettingsViewModel>()

    private val mainViewModel by viewModels<MainViewModel>()



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

                    settingsViewModel.setLanguague()
                    AppNavigation(settingsViewModel = settingsViewModel, mainViewModel)
                }
            }
        }
    }
}



