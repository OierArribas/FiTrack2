package com.example.fittrack.ui.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem (
    val route: String,
    val icon: ImageVector
    ) {
    object ScreenPrincipal: NavItem(route = "screen_principal", icon = Icons.Default.Home)
    object ScreenEntrenamientos: NavItem(route = "screen_entrenamientos", icon = Icons.Default.Star)
    object ScreenSettings: NavItem(route = "screen_settings", icon = Icons.Default.Settings)
    object LoginScreen: NavItem(route = "screen_login", icon = Icons.Default.ArrowForward)
    object Camera: NavItem(route = "screen_camera", icon = Icons.Default.Camera)
}

