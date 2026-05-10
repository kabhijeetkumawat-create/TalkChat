package com.abhijeet.talkchat.presentation.splashscreen.callScreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhijeet.talkchat.R

@Composable
@Preview
fun FavouriteSection(){

    val sampleFavourites = listOf(
        FavoriteContact( image = R.drawable.akshay_kumar,"akashy"),
        FavoriteContact(image = R.drawable.rashmika,"rashmika"),
        FavoriteContact( image = R.drawable.girl2,"hii"),
        FavoriteContact( image = R.drawable.ajay_devgn,"ajay"),
        FavoriteContact( image = R.drawable.mrbeast,"beast"),
        FavoriteContact( image = R.drawable.akshay_kumar,"akashy"),
        FavoriteContact( image = R.drawable.akshay_kumar,"akashy"),
        )


    Column (modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)){
        Text(text = "Favourites", color =  colorResource(R.color.black),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom=8.dp))

        Row (modifier = Modifier.
        fillMaxWidth().
        horizontalScroll(rememberScrollState())){

            sampleFavourites.forEach {

                FavouriteItem(it)
            }
        }
    }

}
data class FavoriteContact(
    val image:Int, val name:String
)