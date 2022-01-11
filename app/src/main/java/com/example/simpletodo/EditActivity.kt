package com.example.simpletodo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val editName = findViewById<EditText>(R.id.editName)
        editName.setText(intent.getStringExtra("taskName"))
        val editDue = findViewById<EditText>(R.id.editDue)
        editDue.setText(intent.getStringExtra("dueDate"))
        val editNote = findViewById<EditText>(R.id.editNote)
        editNote.setText(intent.getStringExtra("note"))

        findViewById<Button>(R.id.editButton).setOnClickListener{
            val name = editName.text.toString()
            if(name.isNotEmpty()) {
                val dueDate = findViewById<EditText>(R.id.editDue).text.toString().trim()
                val note = findViewById<EditText>(R.id.editNote).text.toString().trim()
                onSubmit(name, dueDate, note)
            }
            else Toast.makeText(this, "Please enter task name", Toast.LENGTH_SHORT).show()
        }
    }
    private fun onSubmit( name: String, due: String, note: String) {
        // closes the activity and returns to first screen
        val data = Intent()
        data.putExtra("editedTaskName", name)
        data.putExtra("editedDueDate", due)
        data.putExtra("editedNote", note)
        setResult(RESULT_OK, data)
        finish()
    }
}