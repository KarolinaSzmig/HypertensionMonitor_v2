package com.example.hypertensionmonitor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_settings.*

class settings : AppCompatActivity() {



    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)







        val MNTpicker: TimePicker = findViewById(R.id.MNTpicker)

        val switchMorning: Switch = findViewById(R.id.switchMorning)
        switchMorning.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchMorning.setText("ON")
                MNTpicker.visibility= View.VISIBLE

                setMN.visibility=View.VISIBLE



            } else {
                switchMorning.setText("OFF")
                MNTpicker.visibility= View.INVISIBLE
                setMN.visibility=View.INVISIBLE
            }
        }

        setMN.setOnClickListener {

            val MNhour = MNTpicker.hour.toString()
            val MNminute = MNTpicker.minute.toString()

            val timeIntent = Intent(this, MainActivity::class.java)
            timeIntent.putExtra("MNhour", MNhour)
            timeIntent.putExtra("MNminute", MNminute)
            startActivity(timeIntent)


            //val MNAMPM=MNTpicker.get
            //Ustawianie powiadomień
                //var MNH: Int= MNTpicker.hour
            //ZAPISYWANIE WARTOŚCI AM/PM
            //print(MNH)
                //var MNM=MNTpicker.minute
//testText.setText(MNAMPM)
        }








        val ENTpicker: TimePicker = findViewById(R.id.ENTpicker)
        val switchEvening: Switch = findViewById(R.id.switchEvening)
        switchEvening.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchEvening.setText("ON")
                ENTpicker.visibility=View.VISIBLE
            } else {
                switchEvening.setText("OFF")
                ENTpicker.visibility=View.INVISIBLE
            }
        }


        val MedTpicker: TimePicker = findViewById(R.id.MedTpicker)
        val switchMedicine: Switch = findViewById(R.id.switchMedicine)

        switchMedicine.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                MedTpicker.visibility=View.VISIBLE
                switchMedicine.setText("ON")
            } else {
                switchMedicine.setText("OFF")
                MedTpicker.visibility=View.INVISIBLE
            }
        }







    }





}
