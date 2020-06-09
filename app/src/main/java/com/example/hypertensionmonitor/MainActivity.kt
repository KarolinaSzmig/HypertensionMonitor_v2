package com.example.hypertensionmonitor


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //przyciski przejścia do kolejnych widoków
        goMeassure.setOnClickListener {
            val intent = Intent(this, meassure::class.java)
            startActivity(intent)
        }

        goShow.setOnClickListener {
            val intent = Intent(this, data::class.java)
            startActivity(intent)
        }

        goSettings.setOnClickListener {
            val intent = Intent(this, settings::class.java)
            startActivity(intent)
        }

    }


}


