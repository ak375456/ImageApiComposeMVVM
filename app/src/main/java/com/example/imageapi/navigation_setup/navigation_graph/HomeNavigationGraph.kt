package com.example.imageapi.navigation_setup.navigation_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.imageapi.home_screens.presentation.screen.CameraScreen
import com.example.imageapi.home_screens.presentation.screen.FirstScreen
import com.example.imageapi.navigation_setup.HOME_ROUTE
import com.example.imageapi.navigation_setup.Screens

fun NavGraphBuilder.homeNavigationGraph(navController: NavHostController){
    navigation(
        startDestination = Screens.FirstScreen.route,
        route = HOME_ROUTE
    ){
        composable(
            route = Screens.FirstScreen.route
        ){
            FirstScreen(navController)
        }
        composable(
            route = Screens.CameraScreen.route
        ){
            CameraScreen(navHostController = navController)
        }
    }
}