package com.cowday.pawtography.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cowday.pawtography.MainViewModel
import com.cowday.pawtography.ui.screens.cache.CacheScreen
import com.cowday.pawtography.ui.screens.generate.GenerateScreen
import com.cowday.pawtography.ui.screens.home.HomeScreen

@Composable
fun PawtographyNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = PawtographyNavScreen.HOME.route,
        modifier = modifier,
    ) {
        composable(route = PawtographyNavScreen.HOME.route) {
            HomeScreen(navController)
        }
        composable(route = PawtographyNavScreen.GENERATE.route) {
            GenerateScreen(navController, viewModel)
        }
        composable(route = PawtographyNavScreen.CACHE.route) {
            CacheScreen(navController)
        }
    }
}