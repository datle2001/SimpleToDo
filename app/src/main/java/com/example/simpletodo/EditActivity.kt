package com.example.simpletodo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val editTaskField = findViewById<EditText>(R.id.editTaskField)
        editTaskField.setText(getIntent().getStringExtra("taskName"))
        editTaskField.setTextSize(25f)

        findViewById<Button>(R.id.editButton).setOnClickListener{
            val input = editTaskField.text.toString()
            if(input.isNotEmpty()) {
                onSubmit(input)
            }
        }
    }
    fun onSubmit(input: String) {
        // closes the activity and returns to first screen
        val data = Intent()
        data.putExtra("alternative", input)
        setResult(RESULT_OK, data)
        finish()
    }
}