package com.example.imageapi.home_screens.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor():ViewModel() {
    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap = _bitmap.asStateFlow()
    fun onTakePhoto(bitmap: Bitmap){
        Log.d("CAMERAA",bitmap.toString())
        _bitmap.value = bitmap
    }
}