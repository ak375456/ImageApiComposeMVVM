package com.example.imageapi.navigation_setup

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.imageapi.navigation_setup.navigation_graph.homeNavigationGraph

@Composable
fun SetupNavGraph(navHostController: NavHostController){
    NavHost(
        navController = navHostController,
        startDestination = HOME_ROUTE,
        route = ROOT_ROUTE,
    ){
        homeNavigationGraph(navHostController)
    }
}