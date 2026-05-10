package com.abhijeet.talkchat.presentation.splashscreen.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhijeet.talkchat.presentation.splashscreen.SplashScreen
import com.abhijeet.talkchat.presentation.splashscreen.callScreen.CallScreen
import com.abhijeet.talkchat.presentation.splashscreen.communtiesScreen.CommuntiesScreen
import com.abhijeet.talkchat.presentation.splashscreen.homescreen.HomeScreen
import com.abhijeet.talkchat.presentation.splashscreen.profile.UserProfileScreen
import com.abhijeet.talkchat.presentation.splashscreen.updatescreen.UpdateScreen
import com.abhijeet.talkchat.presentation.splashscreen.userregistrationscreen.UserRegistrationScreen
import com.abhijeet.talkchat.presentation.splashscreen.viewmodel.BaseViewModel
import com.abhijeet.talkchat.presentation.splashscreen.welcomescreen.WelcomeScreen

@Composable
fun WhatsAppNavigationSystem() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {

        composable<Routes.Splash> {
            SplashScreen(navController)
        }

        composable<Routes.Welcome> {
            WelcomeScreen(navController)
        }

        composable<Routes.Register> {
            UserRegistrationScreen(navController)
        }

        composable<Routes.Home> {
           val baseViewModel : BaseViewModel = hiltViewModel()
            HomeScreen(navController,baseViewModel)
        }

        composable<Routes.Updates> {
            UpdateScreen(navController)
        }

        composable<Routes.Communities> {
            CommuntiesScreen(navController)
        }

        composable<Routes.Calls> {
            CallScreen(navController)
        }

        composable<Routes.UserProfile> {
            UserProfileScreen(navController = navController)
        }

        // 🔥 Chat Screen with argument
      /*  composable(Routes.Chat.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            ChatScreen(userId)
        }*/
    }
}