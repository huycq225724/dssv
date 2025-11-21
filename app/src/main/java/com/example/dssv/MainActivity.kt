package com.example.dssv

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Student(
    var id: String,
    var name: String
)

class MainActivity : AppCompatActivity() {
    private lateinit var edtStudentId: EditText
    private lateinit var edtStudentName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val students = mutableListOf<Student>()
    private var selectedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        edtStudentId = findViewById(R.id.edtStudentId)
        edtStudentName = findViewById(R.id.edtStudentName)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

        students.addAll(listOf(
            Student("20200001", "Nguyễn Văn A"),
            Student("20200002", "Trần Thị B"),
            Student("20200003", "Lê Văn C"),
            Student("20200004", "Phạm Thị D"),
            Student("20200005", "Hoàng Văn E"),
            Student("20200006", "Vũ Thị F"),
            Student("20200007", "Đặng Văn G"),
            Student("20200008", "Bùi Thị H"),
            Student("20200009", "Hồ Văn I")
        ))

        sortStudents()

        adapter = StudentAdapter(
            students = students,
            onItemClick = { student: Student, position: Int ->
                selectedPosition = position
                edtStudentId.setText(student.id)
                edtStudentName.setText(student.name)
            },
            onDeleteClick = { position: Int ->
                students.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, students.size)
                if (selectedPosition == position) {
                    clearInputs()
                    selectedPosition = -1
                }
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAdd.setOnClickListener {
            val id = edtStudentId.text.toString().trim()
            val name = edtStudentName.text.toString().trim()

            if (id.isEmpty() || name.isEmpty()) {
                return@setOnClickListener
            }

            students.add(Student(id, name))
            sortStudents()
            adapter.notifyDataSetChanged()
            clearInputs()
            selectedPosition = -1
        }

        btnUpdate.setOnClickListener {
            if (selectedPosition == -1) {
                return@setOnClickListener
            }

            val id = edtStudentId.text.toString().trim()
            val name = edtStudentName.text.toString().trim()

            if (id.isEmpty() || name.isEmpty()) {
                return@setOnClickListener
            }

            students[selectedPosition].id = id
            students[selectedPosition].name = name
            sortStudents()
            adapter.notifyDataSetChanged()
            clearInputs()
            selectedPosition = -1
        }
    }

    private fun clearInputs() {
        edtStudentId.setText("")
        edtStudentName.setText("")
    }

    private fun sortStudents() {
        students.sortWith(compareBy(
            { getLastName(it.name) },
            { it.name }
        ))
    }

    private fun getLastName(fullName: String): String {
        val parts = fullName.trim().split("\\s+".toRegex())
        return if (parts.isNotEmpty()) parts.last() else fullName
    }
}