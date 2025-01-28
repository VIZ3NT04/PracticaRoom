package com.example.practicaroom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaroom.databinding.ItemTaskBinding
import com.example.practicaroom.entity.TaskEntity

class TaskAdapter(private val tasks: List<TaskEntity>)
    : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private lateinit var context: Context
    // ViewHolder que utiliza View Binding
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTaskBinding.bind(view)

    }

    // Crear nuevas vistas (invocado por el LayoutManager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        // Inflar el layout del elemento (item_ciudad.xml)
        val view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)

        // Retornar un nuevo ViewHolder con la vista inflada
        return ViewHolder(view)
    }

    // Reemplazar el contenido de las vistas (invocado por el LayoutManager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]

        with(holder) {
            binding.taskTitle.text = task.title
            binding.taskDescription.text = task.description
            binding.taskStatus.text = if (task.isCompleted) "Completed" else "Not Completed"
        }
    }

    // Tamaño total de los datos
    override fun getItemCount() = tasks.size
}
