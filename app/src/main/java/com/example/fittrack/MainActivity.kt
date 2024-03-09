package com.example.fittrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.LocaleListCompat
import com.example.fittrack.ui.DataStore.Language
import com.example.fittrack.ui.Navigation.AppNavigation
import com.example.fittrack.ui.Screens.ScreenSettings
import com.example.fittrack.ui.ViewModels.MainViewModel
import com.example.fittrack.ui.ViewModels.SettingsViewModel
import com.example.fittrack.ui.theme.FitTrackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val settingsViewModel by viewModels<SettingsViewModel>()

    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


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


