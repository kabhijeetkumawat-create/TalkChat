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
fun WhatsAppNavigationSystem(){

    val navController = rememberNavController()

    NavHost(startDestination = Routes.SplashScreen, navController = navController){

        composable<Routes.SplashScreen>{
            SplashScreen(navController)
        }

        composable<Routes.WelcomeScreen> {
            WelcomeScreen()
        }

        composable<Routes.UserRegistrationScreen> {
            UserRegistrationScreen()
        }

        composable<Routes.HomeScreen> {
            HomeScreen()
        }

        composable<Routes.UpdateScreen> {
            UpdateScreen()
        }
        composable<Routes.CommunitiesScreen> {
            CommuntiesScreen()
        }
        composable<Routes.CallScreen> {
            CallScreen()
        }


    }
}