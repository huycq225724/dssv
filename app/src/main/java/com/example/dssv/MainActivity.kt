package com.example.dssv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Student(
    var id: String,
    var name: String,
    var phone: String,
    var address: String
)

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val students = mutableListOf<Student>()

    private val ADD_REQUEST = 1
    private val UPDATE_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        // load mẫu
        students.addAll(
            listOf(
                Student("20200001", "Nguyễn Văn A", "0901111001", "Hà Nội"),
                Student("20200002", "Trần Thị B", "0901111002", "TP. Hồ Chí Minh"),
                Student("20200003", "Lê Văn C", "0901111003", "Đà Nẵng"),
                Student("20200004", "Phạm Thị D", "0901111004", "Hải Phòng"),
                Student("20200005", "Hoàng Văn E", "0901111005", "Cần Thơ"),
                Student("20200006", "Vũ Thị F", "0901111006", "Nam Định"),
                Student("20200007", "Đặng Văn G", "0901111007", "Thái Bình"),
                Student("20200008", "Bùi Thị H", "0901111008", "Thanh Hóa"),
                Student("20200009", "Hồ Văn I", "0901111009", "Huế")
            )
        )



        adapter = StudentAdapter(students) { student, position ->
            val intent = Intent(this, StudentDetailActivity::class.java)
            intent.putExtra("id", student.id)
            intent.putExtra("name", student.name)
            intent.putExtra("phone", student.phone)
            intent.putExtra("address", student.address)
            intent.putExtra("position", position)
            startActivityForResult(intent, UPDATE_REQUEST)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK || data == null) return

        if (requestCode == ADD_REQUEST) {
            val student = Student(
                data.getStringExtra("id")!!,
                data.getStringExtra("name")!!,
                data.getStringExtra("phone")!!,
                data.getStringExtra("address")!!
            )
            students.add(student)
            adapter.notifyItemInserted(students.size - 1)
        }

        if (requestCode == UPDATE_REQUEST) {
            val pos = data.getIntExtra("position", -1)
            if (pos != -1) {
                students[pos].id = data.getStringExtra("id")!!
                students[pos].name = data.getStringExtra("name")!!
                students[pos].phone = data.getStringExtra("phone")!!
                students[pos].address = data.getStringExtra("address")!!
                adapter.notifyItemChanged(pos)
            }
        }
    }
}
