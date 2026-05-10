package com.abhijeet.talkchat.presentation.splashscreen.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.abhijeet.talkchat.R
import com.abhijeet.talkchat.presentation.splashscreen.bottomnavigation.BottomNavigation
import com.abhijeet.talkchat.presentation.splashscreen.chat_box.ChatDesign
import com.abhijeet.talkchat.presentation.splashscreen.chat_box.ChatDesignModel
import com.abhijeet.talkchat.presentation.splashscreen.navigation.Routes
import com.abhijeet.talkchat.presentation.splashscreen.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeBaseViewModel: BaseViewModel
) {

    var showPopup by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val chatData by homeBaseViewModel.chatList.collectAsState()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    // Fetch chats
    LaunchedEffect(userId) {
        userId?.let {
            homeBaseViewModel.getChatForUser(it) {}
        }
    }

    // Filter chats
    val filteredChats = if (searchText.isEmpty()) {
        chatData
    } else {
        chatData.filter {
            it.name?.contains(searchText, ignoreCase = true) == true
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showPopup = true },
                containerColor = colorResource(id = R.color.light_green),
                modifier = Modifier.size(65.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.chat_icon),
                    contentDescription = null,
                    modifier = Modifier.size(55.dp),
                    tint = Color.White
                )
            }
        },
        bottomBar = {
            BottomNavigation(
                navHostController,
                selectedItem = 0,
                onClick =
                    { index ->
                        when (index) {
                            0 -> navHostController.navigate(Routes.Home)
                            1 -> navHostController.navigate(Routes.Updates)
                            2 -> navHostController.navigate(Routes.Communities)
                            3 -> navHostController.navigate(Routes.Calls)
                        }
                    })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .background(Color.White)
        ) {

            //  Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {

                if (isSearching) {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text("Search") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        singleLine = true
                    )
                } else {
                    Text(
                        text = "TalkChat",
                        fontSize = 28.sp,
                        color = colorResource(id = R.color.light_green),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                ) {

                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color.Black
                        )
                    }

                    if (isSearching) {
                        IconButton(onClick = {
                            isSearching = false
                            searchText = ""
                        }) {
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

                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                painter = painterResource(id = R.drawable.more),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = colorResource(R.color.black)
                            )

                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("New Group") },
                                    onClick = { showMenu = false }
                                )
                                DropdownMenuItem(
                                    text = { Text("Settings") },
                                    onClick = {
                                        showMenu = false
                                        navHostController.navigate(Routes.SettingScreen)
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 🔹 Chat List
                LazyColumn {
                    items(filteredChats) { chat ->
                        ChatDesign(
                            chat,
                            onClick = {
                                navHostController.navigate(
                                    Routes.ChatScreen.createRoute(
                                        phoneNumber = chat.phoneNumber ?: ""
                                    )
                                )
                            },
                            baseViewModel = homeBaseViewModel
                        )
                    }
                }
            }
        }



        if (showPopup) {
            Dialog(onDismissRequest = { showPopup = false }) {
                Surface(shape = MaterialTheme.shapes.medium) {
                    AddUserPopup(
                        onDismiss = { showPopup = false },
                        onUserAdd = { homeBaseViewModel.addChat(it) },
                        baseViewModel = homeBaseViewModel
                    )
                }
            }
        }
    }
}

    @Composable
    fun AddUserPopup(
        onDismiss: () -> Unit,
        onUserAdd: (ChatDesignModel) -> Unit,
        baseViewModel: BaseViewModel
    ) {
        var phoneNumber by remember { mutableStateOf("") }
        var isSearching by remember { mutableStateOf(false) }
        var userFound by remember { mutableStateOf<ChatDesignModel?>(null) }
        var hasSearched by remember { mutableStateOf(false) }

        Column(modifier = Modifier.padding(16.dp)) {

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Enter Phone Number") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                Button(
                    onClick = {
                        isSearching = true
                        hasSearched = true

                        baseViewModel.searchUserByPhoneNumber(phoneNumber) { user ->
                            isSearching = false
                            userFound = user
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Search")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (isSearching) {
                Text("Searching...", color = Color.Gray)
            }

            if (userFound != null) {
                Column {
                    Text("User Found: ${userFound?.name}")

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = {
                        userFound?.let { onUserAdd(it) }
                        onDismiss()
                    }) {
                        Text("Add to Chat")
                    }
                }
            } else if (hasSearched && !isSearching) {
                Text("No User found", color = Color.Gray)
            }
        }
    }
