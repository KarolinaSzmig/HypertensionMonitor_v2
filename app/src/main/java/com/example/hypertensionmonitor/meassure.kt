package com.example.hypertensionmonitor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_meassure.*
import java.io.PrintWriter


class meassure : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meassure)

//        backButton.setOnClickListener {
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//        }

        //Utworzenie list przechowujących wynik pomiarów ciśnienia
        var SpressureList = emptyArray<String>()
        SpressureList=SpressureList+"Systolic pressure:"
        var DpressureList = emptyArray<String>()



        save.setOnClickListener {

            var SpressureSt: String =Spressure.getText().toString()

             //przejście do widoku 'data' oraz przekazanie zmiennych
             val intent = Intent(this, data::class.java)
             intent.putExtra("Spressure", SpressureSt)
             startActivity(intent)


}

        }

    }

