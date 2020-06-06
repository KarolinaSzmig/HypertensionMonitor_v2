package com.example.hypertensionmonitor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_meassure.*
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.OutputStreamWriter
import android.widget.Toast as Toast1


class meassure : AppCompatActivity() {


    private val CSV_HEADER = "date,systolic,diastolic,ifTaken"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meassure)

//        backButton.setOnClickListener {
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//        }



        save.setOnClickListener {

            var SpressureV: String = Spressure.getText().toString()
            var DpressureV: String = editText2.getText().toString()
            // tu jeszcze true/false if taken

            //Ciśnienie skurczowe 90-135 mm Hg
            //Ciśnienie rozkurczowe 50-90 mm Hg
            if (SpressureV.toInt() < 70 || SpressureV.toInt() > 300) {
                Toast1.makeText(
                    applicationContext,
                    "The systolic pressure value is incorrect!",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
            else if (DpressureV.toInt() < 35 || DpressureV.toInt() > 170) {
                    Toast1.makeText(
                        applicationContext,
                        "The diastolic pressure value is incorrect!",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
            }
            else {

                var fileWriter: FileWriter? = null

                try {

                    //dopisywanie do pliku

                    val fOut = FileOutputStream(File(filesDir, "results.csv"), true) // TODO
                    val out = OutputStreamWriter(fOut)

                    out.write("\n")
                    out.write("data")
                    out.write(",")
                    out.write(SpressureV)
                    out.write(",")
                    out.write(DpressureV)
                    out.write(",")
                    out.write("ifTaken")


                    out.flush()
                    out.close()

                    println("Write CSV successfully!")

                } catch (e: Exception) {
                    println("Writing CSV error!")
                    e.printStackTrace()
                }










                //przejście do widoku 'data' oraz przekazanie zmiennych
                val intent = Intent(this, WeekChart::class.java)
                intent.putExtra("Spressure", SpressureV)
                startActivity(intent)


                //alert - ciśnienie powyżej 180/120 mm Hg
                if(SpressureV.toInt()>179 || DpressureV.toInt()>119){
                    Toast1.makeText(
                        applicationContext,
                        "YOUR BLOOD PRESSURE IS TOO HIGH!!! CONTACT YOUR DOCTOR!!!",
                        android.widget.Toast.LENGTH_LONG


                    ).show()
                }




            }







        }

    }

}

