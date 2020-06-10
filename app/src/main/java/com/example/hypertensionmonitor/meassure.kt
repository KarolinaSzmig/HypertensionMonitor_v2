package com.example.hypertensionmonitor

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_meassure.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.time.LocalDateTime
import android.widget.Toast as Toast1


class meassure : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meassure)


        //sprawdzenie, czy użytkownik wziął leki
        var ifTaken = false
        medCheck.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked == true) {
                ifTaken = true
            } else {
                ifTaken = false
            }
        }


        //pobór zmiennych wprowadzonych przez użytkownika
        save.setOnClickListener {


            var SpressureV: String = Spressure.getText().toString()
            var DpressureV: String = editText2.getText().toString()

            //Zabezpieczenia przed wprowadzeniem nieprawidłowych danych
            //Ciśnienie skurczowe 90-135 mm Hg
            //Ciśnienie rozkurczowe 50-90 mm Hg
            if (SpressureV.toInt() < 70 || SpressureV.toInt() > 250) {
                Toast1.makeText(
                    applicationContext,
                    "The systolic pressure value is incorrect!",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            } else if (DpressureV.toInt() < 35 || DpressureV.toInt() > 150) {
                Toast1.makeText(
                    applicationContext,
                    "The diastolic pressure value is incorrect!",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            } else {
                //data pomiaru
                val currently = LocalDateTime.now().toString()

                //dopisywanie do pliku csv
                try {

                    val fOut = FileOutputStream(File(filesDir, "results.csv"), true)
                    val out = OutputStreamWriter(fOut)

                    out.write("\n")
                    out.write(currently)//data pomiaru
                    out.write(";")
                    out.write(SpressureV)//ciśnienie skurczowe
                    out.write(";")
                    out.write(DpressureV)//ciśnienie rozkorczowe
                    out.write(";")
                    out.write(ifTaken.toString())//czy leki zostały wzięte


                    out.flush()
                    out.close()

                    println("Write CSV successfully!")

                } catch (e: Exception) {
                    println("Writing CSV error!")
                    e.printStackTrace()
                }


                //alert - ciśnienie powyżej 180/120 mm Hg
                //pomiar zapisuje się, ale dodatkowo wyświetli się komunikat
                if (SpressureV.toInt() > 179 || DpressureV.toInt() > 119) {
                    val toastAlert = Toast1.makeText(
                        applicationContext,
                        "YOUR BLOOD PRESSURE IS TOO HIGH!!! CONTACT HOSPITAL!!!",
                        Toast1.LENGTH_LONG
                    )
                    val view: View = toastAlert.getView()
                    view.setBackgroundColor(Color.parseColor("#FF0000"))
                    toastAlert.show()
                }

                //po zatwierdzeniu reset w widoku
                Spressure.setText("")
                editText2.setText("")
                medCheck.isChecked = false

            }


        }

    }

}

