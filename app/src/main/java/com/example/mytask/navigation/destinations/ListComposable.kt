package com.example.mytask.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mytask.util.Constants.LIST_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit
){
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(name = "action"){ type = NavType.StringType })
    ){

    }
}