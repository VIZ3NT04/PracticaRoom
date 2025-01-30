package com.example.practicaroom

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaroom.adapter.OnClickListener
import com.example.practicaroom.application.TaskApplication
import com.example.practicaroom.databinding.ActivityMainBinding
import com.example.practicaroom.entity.TaskEntity
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: TaskAdapter
    private lateinit var mLayout: LinearLayoutManager
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                showToast("Opción settings seleccionada")
                true
            }
            R.id.action_api -> {
                showToast("Opción API seleccionada")
                true
            }
            R.id.action_room -> {
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

        // Configura la Toolbar
        val toolBar: androidx.appcompat.widget.Toolbar =
            findViewById<androidx.appcompat.widget.Toolbar>(R.id.appbar)
        setSupportActionBar(toolBar)

        // Configura el DrawerLayout y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        // Configura el botón de hamburguesa
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolBar,
            R.string.app_name, R.string.app_name
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Manejar clics en el menú de navegación
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_api -> {
                    showToast("API seleccionada")
                    val intent = Intent(this, PokemonAPI::class.java)
                    startActivity(intent)

                }
                R.id.action_settings -> {
                    showToast("Settings seleccionado")
                    val intent = Intent(this, ActivitySettings::class.java)
                    startActivity(intent)
                }
                R.id.action_room -> {
                    showToast("ROOM seleccionado")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            // Cerrar el menú lateral después de seleccionar una opción
            drawerLayout.closeDrawers()
            true
        }

        // Configuración del padding para evitar superposición con el sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración del FAB
        binding.fab.setOnClickListener {
            val intent = Intent(this, InsertForm::class.java)
            startActivity(intent)
        }

        // Configuración del RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        Thread {
            // Obtener datos de la base de datos
            val taskEntities = TaskApplication.database.taskDao().getAll()
            setupRecyclerView(taskEntities)
        }.start()
    }

    fun setupRecyclerView(taskEntities: List<TaskEntity>) {
        mAdapter = TaskAdapter(taskEntities.toMutableList(), this)
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
