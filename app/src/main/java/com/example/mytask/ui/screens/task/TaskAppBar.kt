package com.example.mytask.ui.screens.task

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.mytask.components.DisplayAlertDialog
import com.example.mytask.data.models.TodoTask
import com.example.mytask.ui.theme.topAppBarBackgroundColor
import com.example.mytask.ui.theme.topAppBarContentColor
import com.example.mytask.util.Action

@Composable
fun TaskAppBar(
    selectedTask: TodoTask?,
    navigateToListScreen: (Action) -> Unit
){
    if(selectedTask == null){
        NewTaskAppBar(
            navigateToListScreen = navigateToListScreen
        )
    }else{
        ExistingTaskAppBar(
            selectedTask = selectedTask,
            navigateToListScreen = navigateToListScreen
        )
    }

}


@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit
){
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = "Add Task",
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            AddAction(onAddClicked = navigateToListScreen)
        }
    )

}
@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
){

    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back Arrow",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }

}
@Composable
fun AddAction(
    onAddClicked: (Action) -> Unit
){

    IconButton(onClick = { onAddClicked(Action.ADD) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Add Task",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }

}


@Composable
fun ExistingTaskAppBar(

    selectedTask: TodoTask,
    navigateToListScreen: (Action) -> Unit
){
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = selectedTask.title,
                color = MaterialTheme.colors.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            ExistingTaskAppBarActions(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        }
    )

}
@Composable
fun ExistingTaskAppBarActions(
    selectedTask: TodoTask,
    navigateToListScreen: (Action) -> Unit
){
    var openDialog by remember {
        mutableStateOf(false)
    }
    DisplayAlertDialog(
        title = "Remove '${selectedTask.title}'?",
        message = "Are you sure you want to remove '${selectedTask.title}'?",
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { navigateToListScreen(Action.DELETE) }
    )

    DeleteAction(onDeleteClicked = {
        openDialog = true
    })
    UpdateAction(onUpdateClicked = navigateToListScreen)
}
@Composable
fun DeleteAction(
    onDeleteClicked: () -> Unit
){
    IconButton(onClick = { onDeleteClicked() }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete Icon",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }

}
@Composable
fun CloseAction(
    onCloseClicked: (Action) -> Unit
){
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close Clicked",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }

}
@Composable
fun UpdateAction(
    onUpdateClicked: (Action) -> Unit
){
    IconButton(onClick = { onUpdateClicked(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Close Clicked",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }

}

@Composable
@Preview
fun NewTaskAppBarPreview(){
    NewTaskAppBar(navigateToListScreen = {})
}