package com.example.mytask.navigation

import androidx.navigation.NavHostController
import com.example.mytask.util.Action
import com.example.mytask.util.Constants

class Screens(navController: NavHostController) {
    val splash: () -> Unit = {
        navController.navigate(route = "list/${Action.NO_ACTION.name}"){
            popUpTo("splash") { inclusive = true }
        }
    }
    val list: (Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")
    }
    val task: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action.name}"){
            popUpTo(route = Constants.LIST_SCREEN){ inclusive = true }
        }
    }

}