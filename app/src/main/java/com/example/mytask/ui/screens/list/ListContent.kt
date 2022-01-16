package com.example.mytask.ui.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mytask.data.models.Priority
import com.example.mytask.data.models.TodoTask
import com.example.mytask.ui.theme.HighPriorityColor
import com.example.mytask.ui.theme.RedBackgroundColor
import com.example.mytask.ui.theme.taskItemBackgroundColor
import com.example.mytask.ui.theme.taskItemTextColor
import com.example.mytask.util.Action
import com.example.mytask.util.RequestState
import com.example.mytask.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListContent(
    allTasks: RequestState<List<TodoTask>>,
    searchedTasks: RequestState<List<TodoTask>>,
    lowPriorityTasks: List<TodoTask>,
    highPriorityTasks: List<TodoTask>,
    sortState: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    onSwipeToDelete: (Action, TodoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
){
    if(sortState is RequestState.Success) {
        when{
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = searchedTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDelete = onSwipeToDelete
                    )
                }
            }
            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = allTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDelete = onSwipeToDelete
                    )
                }
            }
            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete
                )
            }
            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    tasks: List<TodoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, TodoTask) -> Unit
){

    if (tasks.isEmpty()){
        EmptyContent()
    }else{
        DisplayTasks(
            tasks = tasks,
            navigateToTaskScreen = navigateToTaskScreen,
            onSwipeToDelete = onSwipeToDelete
        )
    }

}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    tasks: List<TodoTask>,
    onSwipeToDelete: (Action, TodoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
){
    LazyColumn{
        items(
            items = tasks,
            key = { task ->
                task.id
            }
        ){ task ->

            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

            if(isDismissed && dismissDirection == DismissDirection.EndToStart){
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(300)
                    onSwipeToDelete(Action.DELETE, task)
                }
            }

            val degrees by animateFloatAsState(
                targetValue = if(dismissState.targetValue == DismissValue.Default)
                    0f
                else
                    -45f
            )

            var itemAppeared by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = true){
                itemAppeared = true
            }

           AnimatedVisibility(
               visible = itemAppeared && !isDismissed,
               enter = expandVertically(
                   animationSpec = tween(
                       durationMillis = 300
                   )
               ),
               exit = shrinkVertically(
                   animationSpec = tween(
                       durationMillis = 300
                   )
               )
           ) {
               SwipeToDismiss(
                   state = dismissState,
                   directions = setOf(DismissDirection.EndToStart),
                   dismissThresholds = { FractionalThreshold(0.3f) },
                   background = {
                       RedBackground(degrees = degrees)
                   },
                   dismissContent = {
                       TaskItem(
                           todoTask = task,
                           navigateToTaskScreen = navigateToTaskScreen
                       )
                   }
               )
           }
        }
    }
}

@Composable
fun RedBackground(degrees: Float){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RedBackgroundColor)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete Icon",
            tint = Color.White
        )


    }

}


@ExperimentalMaterialApi
@Composable
fun TaskItem(
    todoTask: TodoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
){

    Surface(modifier = Modifier
        .fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = 2.dp,
        onClick = {
            navigateToTaskScreen(todoTask.id)
        }
    ) {

        Column(
            modifier = Modifier
                .padding(all = 12.dp)
                .fillMaxWidth()
        ) {

            Row {
               Text(
                   modifier = Modifier.weight(8f),
                   text = todoTask.title,
                   color = MaterialTheme.colors.taskItemTextColor,
                   style = MaterialTheme.typography.h5,
                   fontWeight = FontWeight.Bold,
                   maxLines = 1
               )
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ){
                    Canvas(
                        modifier = Modifier
                            .width(16.dp)
                            .height(16.dp)
                    ){
                        drawCircle(
                            color = todoTask.priority.color
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = todoTask.description,
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }

}

//@ExperimentalMaterialApi
//@Preview
//@Preview(uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
//@Composable
//fun TaskItemPreview(){
//    TaskItem(
//        todoTask = TodoTask(
//            0,
//            "Hello",
//            "Long ass description which sucks and i hate tying random shit just to get maximum value so please fuck off",
//            Priority.HIGH
//        ),
//        navigateToTaskScreen = {}
//    )
//}