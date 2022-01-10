package com.example.mytask.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mytask.util.Constants

@Entity(tableName = Constants.DATABASE_TABLE)
data class TodoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
){

}
