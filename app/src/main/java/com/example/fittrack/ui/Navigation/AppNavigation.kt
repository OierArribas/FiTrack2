package com.example.fittrack.ui.Navigation

import ScreenPrincipal
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fittrack.R
import com.example.fittrack.ui.DataStore.Language
import com.example.fittrack.ui.Screens.ScreenEntrenamientos
import com.example.fittrack.ui.Screens.ScreenSettings
import com.example.fittrack.ui.ViewModels.MainViewModel
import com.example.fittrack.ui.ViewModels.SettingsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    settingsViewModel: SettingsViewModel,
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()
    val lang by settingsViewModel.lang.collectAsState(initial = Language.Spanish)





    Scaffold(

        topBar = {
            val navStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navStackEntry?.destination?.route
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    if (currentRoute != null) {
                        when (currentRoute) {
                            NavItem.ScreenPrincipal.route -> {
                                Text(text = stringResource(R.string.main))
                            }
                            NavItem.ScreenEntrenamientos.route -> {
                                Text(text = stringResource(id = R.string.trainings))
                            }
                            NavItem.ScreenSettings.route -> {
                                Text(text = stringResource(id = R.string.settings))
                            }
                        }
                    }
                }
            )
        },

        bottomBar = {
            NavigationBar {
                val navStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navStackEntry?.destination


                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == NavItem.ScreenSettings.route} ==true,
                    onClick = {

                        if (currentDestination?.hierarchy?.any { it.route == NavItem.ScreenSettings.route} == false) {
                            navController.navigate(route = NavItem.ScreenSettings.route)
                        }

                    }, icon = {Icon(imageVector = NavItem.ScreenSettings.icon, contentDescription = "hola") })

                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == NavItem.ScreenPrincipal.route} == true,
                    onClick = {

                        if (currentDestination?.hierarchy?.any { it.route == NavItem.ScreenPrincipal.route} == false) {
                            navController.navigate(route = NavItem.ScreenPrincipal.route)
                        }

                    },
                    icon = { Icon(imageVector = NavItem.ScreenPrincipal.icon, contentDescription = "hola")})

                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == NavItem.ScreenEntrenamientos.route} ==true,
                    onClick = {

                        if (currentDestination?.hierarchy?.any { it.route == NavItem.ScreenEntrenamientos.route} == false) {
                            navController.navigate(route = NavItem.ScreenEntrenamientos.route)
                        }

                    }, icon = {Icon(imageVector = NavItem.ScreenEntrenamientos.icon, contentDescription = "hola") })
            }
        }
    ) { paddingValues ->

        NavHost(
                navController = navController,
                startDestination = NavItem.ScreenPrincipal.route,
                modifier = Modifier
                    .padding(paddingValues)
        ){
            composable( route = NavItem.ScreenSettings.route) {
                ScreenSettings(navController = navController, settingsViewModel = settingsViewModel)
            }
            composable( route = NavItem.ScreenPrincipal.route) {
                ScreenPrincipal(navController = navController, mainViewModel = mainViewModel)
            }
            composable( route = NavItem.ScreenEntrenamientos.route ){
                ScreenEntrenamientos(navController, mainViewModel = mainViewModel)
            }


        }



}



}
