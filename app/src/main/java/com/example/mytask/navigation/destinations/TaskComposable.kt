package com.example.mytask.navigation.destinations

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mytask.ui.screens.task.TaskScreen
import com.example.mytask.ui.viewmodel.SharedViewModel
import com.example.mytask.util.Action
import com.example.mytask.util.Constants

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
){
    composable(
        route = Constants.TASK_SCREEN,
        arguments = listOf(navArgument(name = "taskId"){ type = NavType.IntType })
    ){ navBackStackEntry ->

        val taskId = navBackStackEntry.arguments!!.getInt("taskId")
        sharedViewModel.getSelectedTask(taskId = taskId)
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        TaskScreen(
            selectedTask = selectedTask,
            navigateToListScreen = navigateToListScreen
        )


    }
}