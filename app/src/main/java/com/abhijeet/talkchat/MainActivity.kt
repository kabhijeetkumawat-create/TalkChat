package com.abhijeet.talkchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.abhijeet.talkchat.presentation.splashscreen.navigation.WhatsAppNavigationSystem
import com.abhijeet.talkchat.ui.theme.TalkChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TalkChatTheme {

                WhatsAppNavigationSystem()
                }
            }
        }
    }


