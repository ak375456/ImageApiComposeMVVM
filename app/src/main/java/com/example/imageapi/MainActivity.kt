package com.example.imageapi

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.asImageBitmap
import com.example.imageapi.ui.theme.ImageApiTheme
import java.io.ByteArrayInputStream
import androidx.navigation.compose.rememberNavController
import com.example.imageapi.navigation_setup.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageApiTheme {

                SetupNavGraph(navHostController = rememberNavController())

            }
        }
    }
}



fun base64ToImageBitmap(base64String: String): androidx.compose.ui.graphics.ImageBitmap? {
    return try {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeStream(ByteArrayInputStream(decodedBytes))
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}