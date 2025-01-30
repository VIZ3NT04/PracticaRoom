package com.example.practicaroom

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaroom.adapter.OnClickListener
import com.example.practicaroom.application.TaskApplication
import com.example.practicaroom.databinding.ActivityMainBinding
import com.example.practicaroom.entity.TaskEntity

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: TaskAdapter
    private lateinit var mLayout: LinearLayoutManager

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.action_settings ->{
                showToast("Opción settings seleccionada")
                true
            }
            R.id.action_api ->{
                showToast("Opción API seleccionada")
                true
            }
            R.id.action_room ->{
                showToast("Opción ROOM seleccionada")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolBar: androidx.appcompat.widget.Toolbar =
            findViewById<androidx.appcompat.widget.Toolbar>(R.id.appbar)
        setSupportActionBar(toolBar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v,
                                                                             insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top,
                systemBars.right, systemBars.bottom)
            insets
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, InsertForm::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        Thread {
            // Obtener datos de la base de datos
            val taskEntities = TaskApplication.database.taskDao().getAll()
            setupRecyclerView(taskEntities)
            }.start()

    }

    fun setupRecyclerView(taskEntities: List<TaskEntity>) {
        mAdapter = TaskAdapter(taskEntities.toMutableList(),this)
        mLayout = LinearLayoutManager(this)
        binding.recycler.apply {
            setHasFixedSize(true)
            layoutManager = mLayout
            adapter = mAdapter
        }
    }
    override fun onClick(task: TaskEntity) {
        val intent = Intent(this, InsertForm::class.java)
        intent.putExtra("task", task)
        startActivity(intent)
    }

}
