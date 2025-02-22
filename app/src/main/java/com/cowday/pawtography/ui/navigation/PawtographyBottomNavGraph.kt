package com.cowday.pawtography.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
        composable(
            route = PawtographyNavScreen.HOME.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(300)
                )
            }
        ) {
            HomeScreen(navController)
        }
        composable(
            route = PawtographyNavScreen.GENERATE.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(SLIDING_TRANSITION_DURATION_IN_MILLIS)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(SLIDING_TRANSITION_DURATION_IN_MILLIS)
                )
            }
        ) {
            GenerateScreen(navController, viewModel)
        }
        composable(
            route = PawtographyNavScreen.CACHE.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(SLIDING_TRANSITION_DURATION_IN_MILLIS)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(SLIDING_TRANSITION_DURATION_IN_MILLIS)
                )
            }
        ) {
            CacheScreen(navController, viewModel)
        }
    }
}

private const val SLIDING_TRANSITION_DURATION_IN_MILLIS = 400