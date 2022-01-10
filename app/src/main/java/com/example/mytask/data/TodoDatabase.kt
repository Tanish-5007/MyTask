package com.example.mytask.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mytask.data.models.TodoTask

@Database(entities = [TodoTask::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {

    abstract fun todoDao(): TodoDao



}