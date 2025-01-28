package com.example.practicaroom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practicaroom.dao.TaskDao
import com.example.practicaroom.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase : RoomDatabase(){
    abstract fun taskDao(): TaskDao
}