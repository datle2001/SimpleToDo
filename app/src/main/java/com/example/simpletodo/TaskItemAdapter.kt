package com.example.simpletodo

import android.R
import android.R.attr
import android.R.attr.*
import android.content.res.AssetManager
import android.content.res.AssetManager.AssetInputStream
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.GradientDrawable
import androidx.core.view.marginBottom

import android.graphics.Typeface
import android.util.Log
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import org.w3c.dom.Text
import java.security.AccessController.getContext
import java.util.logging.Handler as Handler


class TaskItemAdapter(val listOfItems: List<Task>, val longClickListener: OnLongClickListener, val shortClickListener: OnShortClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }
    interface OnShortClickListener {
        fun onItemShortClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.simple_list_item_1, parent, false)
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
        val taskNameView : TextView = itemView.findViewById(R.id.text1)
        val dueDateView : TextView = itemView.findViewById(R.id.text1)
        val noteView : TextView = itemView.findViewById(R.id.text1)

        init{
            styleTextView(taskNameView)

            itemView.setOnLongClickListener{
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
            itemView.setOnClickListener{
                shortClickListener.onItemShortClicked(adapterPosition)
                true
            }
        }

        private fun styleTextView(view: TextView) {
            view.setTextColor(Color.parseColor("#ffdf00"))
            view.setTextSize(25F)
            view.typeface = Typeface.SANS_SERIF
            view.minHeight = 150

            if (view.getLayoutParams() is MarginLayoutParams) {
                (view.getLayoutParams() as MarginLayoutParams).setMargins(20, 20, 20, 0)
                view.requestLayout()
            }
            val gd = GradientDrawable()
            gd.setColor(Color.WHITE)
            gd.cornerRadius = 20f
            gd.setSize(500, 400)
            view.background = gd
        }
    }
}

