package com.abhijeet.talkchat.models


data class PhoneAuthUser(
    val phoneNumber: String,
    val userId: String,
    val name: String="",
    val status: String="",
    val profileImage: String? = null
)
