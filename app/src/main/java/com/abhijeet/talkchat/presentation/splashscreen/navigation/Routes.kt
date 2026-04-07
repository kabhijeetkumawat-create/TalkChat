package com.abhijeet.talkchat.presentation.splashscreen.navigation


sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Welcome : Routes("welcome")
    object Register : Routes("register")
    object Home : Routes("home")
    object Updates : Routes("updates")
    object Communities : Routes("communities")
    object Calls : Routes("calls")


   // object Chat : Routes("chat/{userId}") {
     //   fun createRoute(userId: String) = "chat/$userId"
  //  }
}