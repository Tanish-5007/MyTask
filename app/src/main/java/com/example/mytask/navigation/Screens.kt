package com.example.mytask.navigation

import androidx.navigation.NavHostController
import com.example.mytask.util.Action
import com.example.mytask.util.Constants

class Screens(navController: NavHostController) {
    val list: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action.name}"){
            popUpTo(route = Constants.LIST_SCREEN){ inclusive = true }
        }
    }
    val task: (Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")
    }

}