package com.example.hypertensionmonitor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_data.*
//import kotlinx.android.synthetic.main.activity_data//.backButton
import kotlinx.android.synthetic.main.activity_meassure.*

class data : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        //sprawdzenie, czy przekazano zmienną z meassurre
        val ss:String = intent.getStringExtra("Spressure")
 //       print(Spressure)
   //     print(ss)
        weekData.setText(ss)












    }
}

