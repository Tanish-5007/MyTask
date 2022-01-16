package com.example.mytask.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mytask.ui.screens.list.ListScreen
import com.example.mytask.ui.viewmodel.SharedViewModel
import com.example.mytask.util.Action
import com.example.mytask.util.Constants.LIST_SCREEN
import com.example.mytask.util.toAction


@ExperimentalAnimationApi
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

        var myAction by rememberSaveable {
            mutableStateOf(Action.NO_ACTION)
        }

        LaunchedEffect(key1 = myAction){
            if(action != myAction){
                myAction = action
                sharedViewModel.action.value = action
            }
        }

        val databaseAction by sharedViewModel.action

        ListScreen(
            action = databaseAction,
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}