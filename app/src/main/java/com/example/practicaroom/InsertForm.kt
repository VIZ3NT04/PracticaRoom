package com.example.practicaroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicaroom.application.TaskApplication
import com.example.practicaroom.databinding.ActivityInsertFormBinding
import com.example.practicaroom.databinding.ActivityMainBinding
import com.example.practicaroom.entity.TaskEntity

class InsertForm : AppCompatActivity() {
    private lateinit var binding: ActivityInsertFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInsertFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val task = intent.getSerializableExtra("task") as? TaskEntity
        if (task == null) {
            binding.btnBorrar.visibility = View.INVISIBLE
            binding.btnActualizar.visibility = View.INVISIBLE
            binding.btnInsert.setOnClickListener {


                val title = binding.editTitle.text.toString()
                val description = binding.editDescription.text.toString()
                val isCompleted = binding.checkBox.isChecked
                val task = TaskEntity(title = title, description = description, isCompleted = isCompleted)
                Thread {
                    TaskApplication.database.taskDao().insert(task)
                }.start()

                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
            }
        } else {
            binding.btnInsert.visibility = View.INVISIBLE
            binding.txtInsert.setText("Update Task or Delete")

            binding.editTitle.setText(task.title)
            binding.editDescription.setText(task.description)
            binding.checkBox.isChecked = task.isCompleted

            binding.btnActualizar.setOnClickListener {


                val task = TaskEntity(id = task.id, title = binding.editTitle.text.toString(), description = binding.editDescription.text.toString(), isCompleted = binding.checkBox.isChecked)
                Thread {
                    TaskApplication.database.taskDao().update(task)
                }.start()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            binding.btnBorrar.setOnClickListener {
                Thread {
                    TaskApplication.database.taskDao().delete(task)
                }.start()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }




    }
}