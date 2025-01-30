package com.example.practicaroom

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaroom.adapter.OnClickListener
import com.example.practicaroom.databinding.ItemTaskBinding
import com.example.practicaroom.entity.TaskEntity

class TaskAdapter(private val tasks: MutableList<TaskEntity>,private val listener: OnClickListener )
    : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTaskBinding.bind(view)
        fun setListener(task: TaskEntity) {
            binding.root.setOnClickListener {
                listener.onClick(task)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]

        with(holder) {
            binding.taskTitle.text = task.title
            binding.taskDescription.text = task.description
            binding.taskStatus.text = if (task.isCompleted) "Completed" else "Not Completed"
            setListener(task)
        }
    }

    override fun getItemCount() = tasks.size
}
