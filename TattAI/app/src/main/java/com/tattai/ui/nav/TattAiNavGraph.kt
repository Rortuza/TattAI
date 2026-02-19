package com.tattai.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tattai.ui.screens.ChatScreen
import com.tattai.ui.screens.HomeScreen
import com.tattai.ui.screens.MenuScreen
import com.tattai.ui.screens.OnboardingScreen

@Composable
fun TattAiNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Onboarding) {
        composable(Routes.Onboarding) {
            OnboardingScreen(onDone = {
                navController.navigate(Routes.Home) {
                    popUpTo(Routes.Onboarding) { inclusive = true }
                }
            })
        }
        composable(Routes.Home) {
            HomeScreen(
                onOpenChat = { navController.navigate(Routes.Chat) },
                onOpenMenu = { navController.navigate(Routes.Menu) }
            )
        }
        composable(Routes.Menu) { MenuScreen() }
        composable(Routes.Chat) { ChatScreen() }
    }
}
