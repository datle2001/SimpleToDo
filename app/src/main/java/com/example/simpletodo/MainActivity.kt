package com.example.simpletodo


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
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

    var listOfTasks = mutableListOf<String>()
    var post = 0
    lateinit var adapter : TaskItemAdapter

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK){
            val value = it.data?.getStringExtra("alternative")
            listOfTasks.set(post, value.toString())
            adapter.notifyDataSetChanged()
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

                Handler().postDelayed(Runnable {
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

        findViewById<Button>(R.id.button).setOnClickListener{
            val addTaskField = findViewById<EditText>(R.id.addTaskField)
            val input = addTaskField.text.toString().trim()
            if(input.isNotEmpty()) {
                listOfTasks.add(input)
                adapter.notifyItemInserted(listOfTasks.size - 1)
                addTaskField.setText("")
                saveItems()
            }
        }
    }
    //save the data
    //create a method to get the data file
    fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }
    //Load the item by reading the file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }
        catch(ioException : IOException) {
            ioException.printStackTrace()
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
        intent.putExtra("taskName", listOfTasks.get(post))
        getResult.launch(intent)
    }
}