package com.example.mytask.navigation.destinations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mytask.ui.screens.list.ListScreen
import com.example.mytask.ui.viewmodel.SharedViewModel
import com.example.mytask.util.Constants.LIST_SCREEN

@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
){
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(name = "action"){ type = NavType.StringType })
    ){
        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}