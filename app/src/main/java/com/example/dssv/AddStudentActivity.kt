package com.example.dssv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

    private lateinit var edtId: EditText
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtAddress: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        edtId = findViewById(R.id.edtId)
        edtName = findViewById(R.id.edtName)
        edtPhone = findViewById(R.id.edtPhone)
        edtAddress = findViewById(R.id.edtAddress)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra("id", edtId.text.toString())
            intent.putExtra("name", edtName.text.toString())
            intent.putExtra("phone", edtPhone.text.toString())
            intent.putExtra("address", edtAddress.text.toString())

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
