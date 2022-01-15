package com.example.mytask.ui.screens.list

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytask.data.models.Priority
import com.example.mytask.data.models.TodoTask
import com.example.mytask.ui.theme.taskItemBackgroundColor
import com.example.mytask.ui.theme.taskItemTextColor
import com.example.mytask.util.RequestState

@ExperimentalMaterialApi
@Composable
fun ListContent(
    tasks: RequestState<List<TodoTask>>,
    navigateToTaskScreen: (taskId: Int) -> Unit
){
    if (tasks is RequestState.Success){
        if (tasks.data.isEmpty()){
            EmptyContent()
        }else{
            DisplayTasks(
                tasks = tasks.data,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }

}

@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    tasks: List<TodoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit
){

    LazyColumn{
        items(
            items = tasks,
            key = { task ->
                task.id
            }
        ){ task ->
            TaskItem(
                todoTask = task,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
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

@ExperimentalMaterialApi
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun TaskItemPreview(){
    TaskItem(
        todoTask = TodoTask(
            0,
            "Hello",
            "Long ass description which sucks and i hate tying random shit just to get maximum value so please fuck off",
            Priority.HIGH
        ),
        navigateToTaskScreen = {}
    )


}