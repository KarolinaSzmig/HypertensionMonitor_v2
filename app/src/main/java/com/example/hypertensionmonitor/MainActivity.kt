package com.example.hypertensionmonitor


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

    //klasa służąca do wyświetlania powiadomień o zadanym czasie - użyta w 'settings'
    class Receiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            Log.d("settings", "Receiver: Twoje powiadomionkoooooooo  :>>>>>>")
            Toast.makeText(
                context,
                "TIME TO MEASSURE YOUR BLOOD PRESSURE!!!",
                android.widget.Toast.LENGTH_LONG


            ).show()


        }


    }


}
