package com.example.mytask.ui.screens.list

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mytask.R
import com.example.mytask.ui.theme.fabBackgroundColor
import com.example.mytask.ui.viewmodel.SharedViewModel
import com.example.mytask.util.SearchAppBarState

@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true){
        sharedViewModel.getAllTasks()
    }

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState
            by sharedViewModel.searchAppBarState
    val searchTextState: String
            by sharedViewModel.searchTextState

    Scaffold(
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
              ListContent(
                  tasks = allTasks,
                  navigateToTaskScreen = navigateToTaskScreen
              )
        },
        floatingActionButton = {
            ListFab(navigateToTaskScreen = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    navigateToTaskScreen: (taskId: Int) -> Unit
){
    FloatingActionButton(
        onClick = {
            navigateToTaskScreen(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(imageVector = Icons.Filled.Add ,
            contentDescription = stringResource(
                id = R.string.add_button
            ),
            tint = Color.White
        )
    }
}


