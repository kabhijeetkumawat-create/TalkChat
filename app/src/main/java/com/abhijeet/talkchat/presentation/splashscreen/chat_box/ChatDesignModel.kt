package com.abhijeet.talkchat.presentation.splashscreen.chat_box

import android.graphics.Bitmap

data class ChatDesignModel (
    val image: String? = null,
    val name: String? = null,
    val message: String? = null,
    val time: String? = null,
    val userId: String? = null,
    val  phoneNumber: String? = null,
    val profileImage:String? = null

){
    constructor(): this(name = null, phoneNumber = null, image = null, userId = null, time = null, message = null, profileImage = null)
}