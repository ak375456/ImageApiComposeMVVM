package com.example.imageapi

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.imageapi.ui.theme.ImageApiTheme
import androidx.navigation.compose.rememberNavController
import com.example.imageapi.navigation_setup.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            if(!hasRequiredPermission()){
                ActivityCompat.requestPermissions(
                    this, CAMERAX_PERMISSION,0
                )
            }
            ImageApiTheme {
                SetupNavGraph(navHostController = rememberNavController())
            }
        }
    }
    private fun hasRequiredPermission():Boolean {
        return CAMERAX_PERMISSION.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object{
        private val CAMERAX_PERMISSION = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO,
        )
    }
}
