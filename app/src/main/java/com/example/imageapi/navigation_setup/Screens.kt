package com.example.imageapi.navigation_setup

const val ROOT_ROUTE = "root"
const val HOME_ROUTE = "home"

sealed class Screens(val route:String) {
    data object FirstScreen:Screens("first_screen")
    data object CameraScreen:Screens("camera_screen")
}