package com.abhijeet.talkchat.presentation.splashscreen.updatescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhijeet.talkchat.R
import com.abhijeet.talkchat.presentation.splashscreen.bottomnavigation.BottomNavigation

@Composable
@Preview(showSystemUi = true)
fun UpdateScreen(){

    val scrollState = rememberScrollState()
    val sampleData = listOf(
        StatusData(image = R.drawable.akshay_kumar, name = "Akshay", time = "1 min ago"),
        StatusData(image = R.drawable.disha_patani, name = "Disha", time = "10 min ago"),
        StatusData(image = R.drawable.rashmika, name = "Rashmika", time = "30 min ago")
        )
    val channelData = listOf(
        ChannelsData(image = R.drawable.meta, name = "Meta", description = "Facebook"),
        ChannelsData(image = R.drawable.neat_roots, name = "Neet Root", description = "hello"),
        ChannelsData(image = R.drawable.tripti_dimri, name = "Tripti Dimri", description = "Watch"),
        )
    

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ },
                containerColor = colorResource(id = R.color.light_green),
                modifier = Modifier.size(65.dp),
                contentColor = Color.White
            ){

                Icon(painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp))
            }
        },
        bottomBar ={
            BottomNavigation()
        },
        topBar = {
            TopBar()
        }
    ) {
        Column (modifier = Modifier
            .padding(it)
            .fillMaxSize()
            .verticalScroll(scrollState)){


            Text(text = "Staus",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
            MyStatus()

            sampleData.forEach {
                StatusItem(statusData = it)
            }
            HorizontalDivider()

            Text(text = "Community",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(text = "Stay updated on topic that matter to you.Find channels to follow below")

                Spacer(modifier = Modifier.height(32.dp))
                Text(text = "Find channels to follow")
            }

            Spacer(modifier = Modifier.height(16.dp))

            channelData.forEach {
                ChannelItemDesign(channelsData = it)
            }

        }
    }
}