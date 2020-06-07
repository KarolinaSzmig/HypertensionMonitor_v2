package com.example.hypertensionmonitor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_data.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter


//import kotlinx.android.synthetic.main.activity_data//.backButton

class data : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)








        weekChartShow.setOnClickListener {
            val intent = Intent(this, WeekChart::class.java)
            startActivity(intent)
        }


        //val qponFile = File.createTempFile("results", "csv")
        //var fileWriter: FileWriter? = null
        delete.setOnClickListener {


            try {

                //usuwanie danych z pliku

                val fOut = FileOutputStream(File(filesDir, "results.csv"), false)
                val out = OutputStreamWriter(fOut)

                out.write("DATE;SYS_PRESSURE;DIA_PRESSURE;IF_MED_TAKEN")

                out.flush()
                out.close()

                println("Write CSV successfully!")

            } catch (e: Exception) {
                println("Writing CSV error!")
                e.printStackTrace()
            }
        }


    }
}

