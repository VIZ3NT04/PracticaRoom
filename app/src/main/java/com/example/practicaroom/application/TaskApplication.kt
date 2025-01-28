package com.example.practicaroom.application

import android.app.Application
import androidx.room.Room
import com.example.practicaroom.database.TaskDatabase
import com.example.practicaroom.entity.TaskEntity

class TaskApplication : Application() {
    companion object{
        lateinit var database: TaskDatabase
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,
            TaskDatabase::class.java,
            "TaskDatabase")
            .build()
    }
}