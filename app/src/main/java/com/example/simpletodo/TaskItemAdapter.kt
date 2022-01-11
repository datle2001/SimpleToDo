package com.example.simpletodo


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup.*
import androidx.core.view.setPadding


class TaskItemAdapter(val listOfItems: List<Task>, val longClickListener: OnLongClickListener, val shortClickListener: OnShortClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }
    interface OnShortClickListener {
        fun onItemShortClicked(position: Int)
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.task, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: TaskItemAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val task = listOfItems[position]
        val taskName: String = task.taskName
        val dueDate: String = task.dueDate
        val note: String = task.note

        // Set item views based on your views and data model
        viewHolder.dueDateView.text = dueDate
        viewHolder.noteView.text = note
        viewHolder.taskNameView.text = taskName
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //Store references to elements in our layout view
        val taskNameView: TextView = itemView.findViewById(R.id.taskName)
        val dueDateView: TextView = itemView.findViewById(R.id.dueDate)
        val noteView: TextView = itemView.findViewById(R.id.note)

        init{
            styleItemView()
            itemView.setOnLongClickListener{
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
            itemView.setOnClickListener{
                shortClickListener.onItemShortClicked(adapterPosition)
                true
            }
        }

        private fun styleItemView() {
            taskNameView.textSize = 23f
            taskNameView.setPadding(25)

            dueDateView.textSize = 20f
            dueDateView.setPadding(25)

            noteView.textSize = 15f
            noteView.setPadding(45)

            if (itemView.layoutParams is MarginLayoutParams) {
                (itemView.layoutParams as MarginLayoutParams).setMargins(20, 20, 20, 0)
                itemView.requestLayout()
            }
            val gd = GradientDrawable()
            gd.setColor(Color.WHITE)
            gd.cornerRadius = 20f
            gd.setSize(500, 400)
            itemView.background = gd
        }
    }
}

