package com.abhijeet.talkchat.presentation.splashscreen.callScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.abhijeet.talkchat.R
import com.abhijeet.talkchat.presentation.splashscreen.bottomnavigation.BottomNavigation
import com.abhijeet.talkchat.presentation.splashscreen.navigation.Routes

@Composable
fun CallScreen(navHostController: NavHostController) {

    var isSearching by remember {
        mutableStateOf(false)
    }

    var search by remember {
        mutableStateOf("")
    }
    var showMenu by remember {
        mutableStateOf(false)
    }

    val sampleCalls = listOf(
       Call(image = R.drawable.sharadhakapoor,"sharadha","1:00 AM",true),
        Call(image = R.drawable.bhuvan_bam,"Bhuvan Bam","10:00 PM",false),
        Call(image = R.drawable.tripti_dimri,"tripti","10:00 AM",false),
        Call(image = R.drawable.girl2,"priya","11:00 AM",false),
        Call(image = R.drawable.kartik_aaryan,"kartik","20:00 PM",false),
        Call(image = R.drawable.carryminati,"carry","15:00 PM",false),
        Call(image = R.drawable.bhuvan_bam,"Bhuvan Bam","10:00 AM",false)

    )



    Scaffold(modifier = Modifier.background(color = Color.White).statusBarsPadding(),
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().background(color = Color.White)) {
                Column {

                    Row {

                        if (isSearching) {

                            TextField(
                                value = search,
                                onValueChange = {
                                    search = it

                                }, placeholder = {
                                    Text(text = "Search", color = Color.Black)

                                },
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black
                                ),
                                modifier = Modifier.padding(start = 12.dp),
                                singleLine = true
                            )
                        } else {
                            Text(
                                text = "Calls",
                                fontSize = 28.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        if (isSearching) {
                            IconButton(onClick = {isSearching = false
                                search = ""}) {
                                Icon(
                                    painter = painterResource(id = R.drawable.cross),
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = Color.Black
                                )
                            }
                        } else {

                            IconButton(onClick = { isSearching = true }) {

                                Icon(
                                    painter = painterResource(id = R.drawable.search),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Black

                                )
                            }
                            IconButton(onClick = {
                                showMenu = true
                            }) {

                                Icon(
                                    painter = painterResource(id = R.drawable.more),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Black
                                )

                                DropdownMenu(modifier = Modifier.background(color = Color.White),
                                    expanded = showMenu,
                                    onDismissRequest = { showMenu = false }) {
                                    DropdownMenuItem(
                                        text = { Text(text = "Status Privacy",color = Color.Black) },
                                        onClick = { showMenu = false })
                                    DropdownMenuItem(
                                        text = { Text(text = "Create channel",color = Color.Black) },
                                        onClick = { showMenu = false })
                                    DropdownMenuItem(
                                        text = { Text(text = "Settings",color = Color.Black) },
                                        onClick = { showMenu = false })

                                }
                            }
                        }
                    }
                    HorizontalDivider()
                }
            }
        }, bottomBar ={
            BottomNavigation(navHostController, selectedItem = 3, onClick = { index ->
                    when (index) {
                        0 -> {
                            navHostController.navigate(Routes.Home)
                        }

                        1 -> {
                            navHostController.navigate(Routes.Updates)
                        }

                        2 -> {
                            navHostController.navigate(Routes.Communities)
                        }

                        3 -> {
                            navHostController.navigate(Routes.Calls)
                        }

                    }
                })
        }

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .background(Color.White)
        ) {

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                FavouriteSection()
            }

            item {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.light_green)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Create a new call",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                Text(
                    text = "Recent Calls",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    color = Color.Black
                )
            }

            items(sampleCalls) { data ->
                CallItemDesign(data)
            }
        }

    }
}