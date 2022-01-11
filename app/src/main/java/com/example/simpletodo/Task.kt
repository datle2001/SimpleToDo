package com.example.simpletodo


class Task(var taskName: String, var dueDate: String = "", var note: String = "") {
    override fun toString(): String {
        return "$taskName|$dueDate|$note"
    }
}