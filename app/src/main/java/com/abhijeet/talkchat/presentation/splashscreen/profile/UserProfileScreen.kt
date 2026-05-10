package com.abhijeet.talkchat.presentation.splashscreen.profile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhijeet.talkchat.R
import com.abhijeet.talkchat.presentation.splashscreen.navigation.Routes
import com.abhijeet.talkchat.presentation.splashscreen.viewmodel.PhoneAuthViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun UserProfileScreen(phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel(), navController: NavController){

    var name by remember { mutableStateOf("") }
    var  status by remember { mutableStateOf("") }
    var profileImageUri: Uri? by remember { mutableStateOf(null) }
    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }

    val firebaseAuth = Firebase.auth
    val phoneNumber = firebaseAuth.currentUser?.phoneNumber?:""
    val userId = firebaseAuth.currentUser?.uid?:""

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {uri: Uri? ->

            profileImageUri = uri
            uri?.let{
                bitmapImage = if (Build.VERSION.SDK_INT<=28){
                    @Suppress("DEPRECATION")
                    android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver,it)
                }else{
                    val source = ImageDecoder.createSource(context.contentResolver,it)
                    ImageDecoder.decodeBitmap(source)
                }
            }
        }
    )

    Column(modifier = Modifier.fillMaxSize()
        .background(Color.White)
        .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {

        Box(modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(2.dp,
                color = Color.Gray,
                shape = CircleShape).clickable{imagePickerLauncher.launch("image/*")}
        ) {
            if (bitmapImage != null) {
                Image(
                    bitmap = bitmapImage!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profile_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
            }



        }
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = phoneNumber)

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = name,
                onValueChange = {name = it},
                label = {
                    Text("Name", color = colorResource(R.color.black))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = colorResource(id = R.color.light_green),
                    focusedIndicatorColor = colorResource(id = R.color.light_green),
                    focusedTextColor = colorResource(R.color.black),
                    unfocusedTextColor = colorResource(R.color.black)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = status,
                onValueChange = {status = it},
                label = {
                    Text("Status")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = colorResource(id = R.color.light_green),
                    focusedIndicatorColor = colorResource(id = R.color.light_green)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                    phoneAuthViewModel.saveUserProfile(userId, name, status, bitmapImage)
                    navController.navigate(Routes.Home)
                }, colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.light_green)
            )) {

                Text("Save", color = Color.White)
            }



        }
    }
