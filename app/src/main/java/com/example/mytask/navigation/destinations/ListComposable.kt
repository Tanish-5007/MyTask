package com.example.mytask.navigation.destinations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mytask.ui.screens.list.ListScreen
import com.example.mytask.ui.viewmodel.SharedViewModel
import com.example.mytask.util.Constants.LIST_SCREEN
import com.example.mytask.util.toAction


@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
){
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(name = "action"){ type = NavType.StringType })
    ){ navBackStackEntry ->

        val action = navBackStackEntry.arguments!!.getString("action").toAction()

        LaunchedEffect(key1 = action){
            sharedViewModel.action.value = action
        }

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}