package com.example.imageapi.home_screens.presentation.screen

import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

@Composable
fun CameraScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission().apply{
    }){
    }

    val imgUri by remember{mutableStateOf("${context.filesDir}/temp.jpg".toUri())}

    val captureLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()){
        Toast.makeText(context, "Image capture: ${if(it) "Successful" else "Failed"}", Toast.LENGTH_SHORT)
            .show()
    }

    Column {
        Button(onClick = {
            if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PERMISSION_GRANTED)
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
            else captureLauncher.launch(imgUri)
        }) {
            Text("Load")
        }
        Image(painter = rememberAsyncImagePainter(imgUri), null)
    }
}
