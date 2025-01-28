package com.example.practicaroom.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.practicaroom.entity.TaskEntity

@Dao
interface TaskDao {
    @Insert
    fun insert(task: TaskEntity)

    @Query("SELECT * FROM task_entity")
    fun getAll(): List<TaskEntity>

    @Update
    fun update(task: TaskEntity)

    @Delete
    fun delete(task: TaskEntity)



}