package com.abhijeet.talkchat.presentation.splashscreen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhijeet.talkchat.presentation.splashscreen.SplashScreen
import com.abhijeet.talkchat.presentation.splashscreen.callScreen.CallScreen
import com.abhijeet.talkchat.presentation.splashscreen.communtiesScreen.CommuntiesScreen
import com.abhijeet.talkchat.presentation.splashscreen.homescreen.HomeScreen
import com.abhijeet.talkchat.presentation.splashscreen.updatescreen.UpdateScreen
import com.abhijeet.talkchat.presentation.splashscreen.userregistrationscreen.UserRegistrationScreen
import com.abhijeet.talkchat.presentation.splashscreen.welcomescreen.WelcomeScreen

@Composable
fun WhatsAppNavigationSystem() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {

        composable(Routes.Splash.route) {
            SplashScreen(navController)
        }

        composable(Routes.Welcome.route) {
            WelcomeScreen(navController)
        }

        composable(Routes.Register.route) {
            UserRegistrationScreen()
        }

        composable(Routes.Home.route) {
            HomeScreen()
        }

        composable(Routes.Updates.route) {
            UpdateScreen()
        }

        composable(Routes.Communities.route) {
            CommuntiesScreen()
        }

        composable(Routes.Calls.route) {
            CallScreen()
        }

        // 🔥 Chat Screen with argument
      /*  composable(Routes.Chat.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            ChatScreen(userId)
        }*/
    }
}