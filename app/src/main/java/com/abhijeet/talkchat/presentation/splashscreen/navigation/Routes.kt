package com.abhijeet.talkchat.presentation.splashscreen.navigation


import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(val route: String) {

    @Serializable
    object Splash : Routes("splash")

    @Serializable
    object Welcome : Routes("welcome")
    @Serializable
    object Register : Routes("register")
    @Serializable
    object Home : Routes("home")
    @Serializable
    object Updates : Routes("updates")
    @Serializable
    object Communities : Routes("communities")
    @Serializable
    object Calls : Routes("calls")
    @Serializable
    object UserProfile : Routes("user_profile")


    @Serializable
    object SettingScreen : Routes("SettingScreen")

    @Serializable
    object ChatScreen : Routes("ChatScreen"){
        const val Routes = "chatScreen/{phoneNumber}"
        fun createRoute(phoneNumber: String)="chatScreen/{phoneNumber"
    }

     object Chat : Routes("chat/{userId}") {
       fun createRoute(userId: String) = "chat/$userId"
   }
}