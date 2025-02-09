package com.example.imageapi.navigation_setup

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.imageapi.home_screens.presentation.CameraViewModel
import com.example.imageapi.navigation_setup.navigation_graph.homeNavigationGraph

@Composable
fun SetupNavGraph(navHostController: NavHostController){
    val viewModel: CameraViewModel = hiltViewModel()
    NavHost(
        navController = navHostController,
        startDestination = HOME_ROUTE,
        route = ROOT_ROUTE,
    ){
        homeNavigationGraph(navHostController, viewModel)
    }
}