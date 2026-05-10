package com.abhijeet.talkchat.presentation.splashscreen.userregistrationscreen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhijeet.talkchat.R
import com.abhijeet.talkchat.presentation.splashscreen.navigation.Routes
import com.abhijeet.talkchat.presentation.splashscreen.viewmodel.AuthState
import com.abhijeet.talkchat.presentation.splashscreen.viewmodel.PhoneAuthViewModel

@Composable
fun UserRegistrationScreen(navController: NavController, phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel()) {

    val authState by phoneAuthViewModel.authState.collectAsState()
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    var otp by remember { mutableStateOf("")}
    var verificationId by remember {mutableStateOf<String?>(null)}



    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("India") }
    var countryCode by remember { mutableStateOf("+91") }
    var phoneNumber by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(10.dp))


        Text(
            text = "Enter your number",
            fontSize = 20.sp,
            color = colorResource(id = R.color.dark_green),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "WhatsApp will need to verify your phone number. What's my number?",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Country Selector
        TextButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = selectedCountry,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    tint = colorResource(id = R.color.dark_green)
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 66.dp),
            thickness = 2.dp,
            color = colorResource(id = R.color.light_green)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf("India", "USA", "Japan", "Russia", "China").forEach { country ->
                DropdownMenuItem(
                    text = { Text(text = country) },
                    onClick = {
                        selectedCountry = country
                        countryCode = when (country) {
                            "India" -> "+91"
                            "USA" -> "+1"
                            "Japan" -> "+81"
                            "Russia" -> "+7"
                            "China" -> "+86"
                            else -> "+91"
                        }
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {

            Text(
                text = countryCode,
                fontSize = 18.sp,
                modifier = Modifier.padding(end = 8.dp), color = Color.Black
            )

            TextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    errorText = ""
                    Color.Black

                },
                placeholder = { Text(text = "Phone Number", color = colorResource(R.color.black)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = colorResource(id = R.color.light_green),
                    focusedIndicatorColor = colorResource(id = R.color.light_green),
                    focusedTextColor = colorResource(R.color.black),
                    unfocusedTextColor = colorResource(R.color.black)
                )
            )
        }

        // Error Message
        if (errorText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = errorText,
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Carrier charges may apply",
            fontSize = 12.sp,
            color = colorResource(R.color.black)
        )


        /*Button(
            onClick = {
                if (phoneNumber.length < 10) {
                    errorText = "Enter valid phone number"
                } else {
                    errorText = ""

                }
            },
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.dark_green)
            )
        ) {
            Text(text = "Next", fontSize = 16.sp)
        }*/

        when (authState) {

            is AuthState.CodeSent -> {

                verificationId = (authState as AuthState.CodeSent).verificationId
            }

            is AuthState.Ideal -> {

                // do nothing
            }

            is AuthState.Loading -> {

            }

            is AuthState.Success -> {
                Log.d("PhoneAuth", "LoginSuccessful")

                phoneAuthViewModel.resetAuthState()

                navController.navigate(Routes.UserProfile) {

                    popUpTo<Routes.Register> {
                        inclusive = true
                    }
                }
            }
            is AuthState.Error ->{
                Toast.makeText(context,(authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
        }

            if (verificationId == null) {
                Spacer(modifier = Modifier.height(16.dp))

              /*  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextField(
                        value = countryCode,
                        onValueChange = { countryCode = it },
                        modifier = Modifier.width(70.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(R.color.light_green),
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    TextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        placeholder = { Text("Enter Phone Number") },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent

                        )
                    )
                }*/
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

                        if (phoneNumber.isNotEmpty()) {
                            val fullPhoneNumber = "$countryCode$phoneNumber"

                            phoneAuthViewModel.sendVerificationCode(fullPhoneNumber, activity)


                        } else {
                            Toast.makeText(
                                context,
                                "Please enter valid Phone number",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }, shape = RoundedCornerShape(6.dp), colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.dark_green)
                    )
                ) {
                    Text("Send OTP", color = colorResource(R.color.white))
                }

                if (authState is AuthState.Loading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }
            } else {
                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    "Enter Otp",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.dark_green)
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = otp,
                    onValueChange = { otp = it },
                    label = { Text("OTP", color = Color.Black) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Black,
                        focusedTextColor = Color.Black

                    )


                )
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (otp.isNotEmpty() && verificationId != null) {

                            phoneAuthViewModel.verifyCode(otp, context)
                        } else {
                            Toast.makeText(
                                context,
                                " Please enter a valid otp",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }, shape = RoundedCornerShape(6.dp), colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.dark_green)
                    )
                ) {

                    Text("Verify OTP", color = Color.White)
                }
                if (authState is AuthState.Loading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }


            }



        }


    }

