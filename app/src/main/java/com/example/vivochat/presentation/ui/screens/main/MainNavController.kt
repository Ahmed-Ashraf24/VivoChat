package com.example.vivochat.presentation.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberMainNavController(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MainNavController {
    return remember(
        navController,
        coroutineScope
    ) {
        MainNavController(navController, coroutineScope)
    }
}

@Stable
class MainNavController(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentBottomNav: BottomNav?
        @Composable get() {
            val currentDest = currentDestination
            // Check if current destination is part of bottom nav
            return BottomNav.entries.firstOrNull { bottomNav ->
                currentDest?.hasRoute(bottomNav.route::class) ?: false
            } ?: run {
                // If not in bottom nav, check parent backstack entry for previous bottom nav
                navController.previousBackStackEntry?.destination?.let { prevDest ->
                    BottomNav.entries.firstOrNull { bottomNav ->
                        prevDest.hasRoute(bottomNav.route::class)
                    }
                }
            }
        }

    val bottomNavs: List<BottomNav> = BottomNav.entries

    fun navigateBack() = navController.navigateUp()

    fun <T : Any> navigateToScreen(screen: T) {
        val isBottomNavDestination = BottomNav.entries.any { it.route::class == screen::class }

        val navOptions = if (isBottomNavDestination) {
            navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        } else {
            null
        }

        navController.navigate(screen, navOptions)
    }
}