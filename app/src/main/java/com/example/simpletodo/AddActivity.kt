package com.example.simpletodo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        findViewById<Button>(R.id.addButton).setOnClickListener{
            val name = findViewById<EditText>(R.id.addTaskName).text.toString().trim()
            if(name == "") {
                Toast.makeText(this, "Please enter task name", Toast.LENGTH_SHORT).show()
            }
            else {
                val dueDate = findViewById<EditText>(R.id.addTaskDue).text.toString().trim()
                val note = findViewById<EditText>(R.id.addNote).text.toString().trim()
                onSubmit(name, dueDate, note)
            }
        }
    }

    private fun onSubmit(name: String, due: String, note: String) {
        // closes the activity and returns to first screen
        val data = Intent()
        data.putExtra("taskName", name)
        data.putExtra("dueDate", due)
        data.putExtra("note", note)
        setResult(RESULT_OK, data)
        finish()
    }
}