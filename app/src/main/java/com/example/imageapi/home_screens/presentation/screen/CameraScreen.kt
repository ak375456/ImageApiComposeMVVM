package com.example.imageapi.home_screens.presentation.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.imageapi.home_screens.presentation.CameraViewModel


@Composable
fun CameraScreen(navHostController: NavHostController) {

    val context = LocalContext.current
    val applicationContext = context.applicationContext

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val controller = remember {
            
            LifecycleCameraController(applicationContext).apply {
                setEnabledUseCases(
                    CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE
                )
            }
        }
        val viewModel = viewModel<CameraViewModel>()


        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            CameraPreview(
                controller = controller,
                modifier = Modifier.fillMaxSize()
            )
            IconButton(
                onClick = {
                    controller.cameraSelector = if(controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                        CameraSelector.DEFAULT_FRONT_CAMERA
                    } else {
                        CameraSelector.DEFAULT_BACK_CAMERA
                    }
                }
            ) {
                Icon(Icons.Default.Refresh, "")
            }
        }
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.BottomCenter
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(
                    onClick = {
                        takePhoto(
                            context = context,
                            controller = controller,
                            onPhotoTaken = viewModel::onTakePhoto
                        )
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, "")
                }
            }
        }
    }
}


fun takePhoto(
    context: Context,
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit
) {
    controller.takePicture(

        ContextCompat.getMainExecutor(context),
        object: ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }
                val rotatedBitmap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )
                onPhotoTaken(rotatedBitmap)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("CAMERA", "Error capturing image", exception)
            }
        }
    )
}

