package com.example.simpletodo


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<Task>()
    var post = 0
    lateinit var adapter : TaskItemAdapter

    private val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK){
            val name = it.data?.getStringExtra("editedTaskName").toString()
            val due = it.data?.getStringExtra("editedDueDate").toString()
            val note = it.data?.getStringExtra("editedNote").toString()
            listOfTasks[post].taskName = name
            listOfTasks[post].dueDate = due
            listOfTasks[post].note = note
            adapter.notifyDataSetChanged()
            saveItems()
        }
    }
    private val addTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK){
            val taskName = it.data?.getStringExtra("taskName").toString()
            val dueDate = it.data?.getStringExtra("dueDate").toString()
            val note = it.data?.getStringExtra("note").toString()
            val task = Task(taskName, dueDate, note)
            listOfTasks.add(task)
            adapter.notifyItemInserted(listOfTasks.size - 1)
            saveItems()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val onLongClickListener = object: TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                val fadeOut = AlphaAnimation(1f, 0f)
                fadeOut.interpolator = DecelerateInterpolator() //add this
                fadeOut.duration = 1000
                findViewById<RecyclerView>(R.id.recyclerView)[position].startAnimation(fadeOut)

                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    listOfTasks.removeAt(position)
                    adapter.notifyDataSetChanged()
                    saveItems()
                }, 1000)

            }
        }
        val onShortClickListener = object: TaskItemAdapter.OnShortClickListener{
            override fun onItemShortClicked(position: Int) {
                post = position
                launchEditView()
            }
        }
        loadItems()

        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener, onShortClickListener)
        //val adapter = ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,listOfTasks)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.addButton).setOnClickListener{
            launchAddView()
        }
    }
    //save the data
    //create a method to get the data file
    private fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }
    //Load the item by reading the file
    private fun loadItems() {
        try {
            makeList(FileUtils.readLines(getDataFile(), Charset.defaultCharset()))
        }
        catch(ioException : IOException) {
            ioException.printStackTrace()
        }
    }

    private fun makeList(list: List<String>) {
        for(line in list) {
            val info = line.split("|")
            val taskName = info[0]
            val dueDate = info[1]
            val note = info[2]
            val task = Task(taskName, dueDate, note)
            listOfTasks.add(task)
        }
    }
    //Save items by writing them into a file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        }
        catch(ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    fun launchEditView() {
        // first parameter is the context, second is the class of the activity to launch
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra("taskName", listOfTasks[post].taskName)
        intent.putExtra("dueDate", listOfTasks[post].dueDate)
        intent.putExtra("note", listOfTasks[post].note)
        editTask.launch(intent)
    }
    private fun launchAddView() {
        // first parameter is the context, second is the class of the activity to launch
        val intent = Intent(this, AddActivity::class.java)
        addTask.launch(intent)
    }
}