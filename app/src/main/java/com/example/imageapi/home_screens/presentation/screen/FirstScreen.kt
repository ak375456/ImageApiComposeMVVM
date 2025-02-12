package com.example.imageapi.home_screens.presentation.screen

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import com.example.imageapi.home_screens.presentation.CameraViewModel
import com.example.imageapi.navigation_setup.Screens
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun FirstScreen(navHostController: NavHostController, viewModel: CameraViewModel) {

    val context = LocalContext.current

    val scrollState = androidx.compose.foundation.rememberScrollState()

    val bitmap by viewModel.bitmap.collectAsState()
    Log.d("CAMERAAb",bitmap.toString())

    val base64StringToImage by viewModel.base64Image.collectAsState()
    val imageBitmap = base64StringToImage?.let { viewModel.base64ToImageBitmap(it) }

    var bitmapImage: Bitmap? = null

    var uriToBase64 by remember { mutableStateOf<String?>(null) }


    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
                uriToBase64 = viewModel.uriToBase64(context, it)
                Log.d("Cameraaa", uriToBase64.toString())
                uriToBase64?.let { encodedString ->
                    Log.d("EncodedString",encodedString)
                    viewModel.base64ToImageBitmap02(encodedString)
                }
            }
        }
    )

    Scaffold (modifier = Modifier.fillMaxSize()){innerPadding->
        Column (modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(scrollState)
        ){
            imageBitmap?.let {
                Image(it,"",contentScale = ContentScale.Fit)
            }
            Row {
                Button(onClick = {
                    navHostController.navigate(route = Screens.CameraScreen.route)
                }
                ) {
                    Text("Capture Image!")
                }
                Button(onClick = {
                    viewModel.clearBitmap()
                }
                ) {
                    Text("Clear Values!")
                }
                Button(onClick = {
                    launcher.launch("image/*")
                }
                ) {
                    Text("Select Image!")
                }
            }
            bitmap?.let {
                Image(bitmap = it.asImageBitmap(), "")
                viewModel.bitmapToBase64(bitmap = it)
                bitmapImage = BitmapFactory.decodeFile(it.toString())
            }
            viewModel.bitmapToBase64(bitmapImage)
            bitmap?.let {
                Image(bitmap = it.asImageBitmap(), "")
            }
            Log.d("UriToBase64", uriToBase64.toString())
            uriToBase64?.let { base64 ->
                val base64ToBitmap: Bitmap? = viewModel.base64ToImageBitmap02(base64)
                base64ToBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Converted Image"
                    )
                } ?: run {
                    Log.d("BitmapError", "Bitmap is null")
                    Text("Image decoding failed")
                }
            }
        }
    }
}





