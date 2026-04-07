package com.abhijeet.talkchat.presentation.splashscreen.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.abhijeet.talkchat.R
import com.abhijeet.talkchat.presentation.splashscreen.navigation.Routes
@Composable
fun WelcomeScreen(navHostController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.whatsapp_sticker),
            contentDescription = "App Logo",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Welcome to TalkChat",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Read our ",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Privacy Policy ",
                color = colorResource(id = R.color.light_green),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tap to 'Agree and continue' to ",
                color = Color.Gray
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "accept the ",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Terms of Service",
                color = colorResource(id = R.color.light_green),
                fontWeight = FontWeight.Medium
            )

        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navHostController.navigate(Routes.Register.route) {
                    popUpTo(Routes.Welcome.route) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.size(200.dp, 50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.dark_green)
            )
        ) {
            Text(text = "Agree and continue", fontSize = 16.sp)
        }
    }
}
