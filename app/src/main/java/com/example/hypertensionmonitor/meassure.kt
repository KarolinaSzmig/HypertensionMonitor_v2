package com.example.hypertensionmonitor

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_meassure.*
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.OutputStreamWriter
import java.time.LocalDateTime
import java.util.*
import android.widget.Toast as Toast1


class meassure : AppCompatActivity() {


    private val CSV_HEADER = "date,systolic,diastolic,ifTaken"


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meassure)


        //zaznaczenie czy leki zostały wzięte

//        medCheck.setOnCheckedChangeListener { view, ifTaken ->
//            if (ifTaken==true){
//            println(ifTaken.toString())}
//            else{
//            println(ifTaken.toString())}
//        }
        //println(ifTaken)


        //println(medCheck)

//        medCheck.setOnCheckedChangeListener({
//            view, ifTaken ->
//            if(ifTaken){
//                true
//            }else{
//                false
//            }
//        })

        //sprawdzenie, czy użytkownik wziął leki
        var ifTaken = false
        medCheck.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked == true) {
                ifTaken = true
            } else {
                ifTaken = false
            }
            println(ifTaken)
        }

        save.setOnClickListener {

            var SpressureV: String = Spressure.getText().toString()
            var DpressureV: String = editText2.getText().toString()



            //Ciśnienie skurczowe 90-135 mm Hg
            //Ciśnienie rozkurczowe 50-90 mm Hg
            if (SpressureV.toInt() < 70 || SpressureV.toInt() > 300) {
                Toast1.makeText(
                    applicationContext,
                    "The systolic pressure value is incorrect!",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            } else if (DpressureV.toInt() < 35 || DpressureV.toInt() > 170) {
                Toast1.makeText(
                    applicationContext,
                    "The diastolic pressure value is incorrect!",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            } else {
                //data pomiaru

//                val c = Calendar.getInstance()
//                val cHour = c.get(Calendar.HOUR_OF_DAY).toString()
//                val cMinute = c.get(Calendar.MINUTE).toString()
                //println("AAAAAAAAAAAAAAAAAAAAAAAAAA")
                //val day:String=c.get(Calendar.DAY_OF_YEAR).toString()
                val currently = LocalDateTime.now().toString()
                //println(day)
                //println(xddd)


                //var fileWriter: FileWriter? = null

                try {

                    //dopisywanie do pliku

                    val fOut = FileOutputStream(File(filesDir, "results.csv"), true) // TODO
                    val out = OutputStreamWriter(fOut)

                    out.write("\n")
                    out.write(currently)
                    out.write(";")
                    out.write(SpressureV)
                    out.write(";")
                    out.write(DpressureV)
                    out.write(";")
                    out.write(ifTaken.toString())


                    out.flush()
                    out.close()

                    println("Write CSV successfully!")

                } catch (e: Exception) {
                    println("Writing CSV error!")
                    e.printStackTrace()
                }


                //przejście do widoku 'data' oraz przekazanie zmiennych - niepotrzebne, do usunięcia
                val intent = Intent(this, WeekChart::class.java)
                intent.putExtra("Spressure", SpressureV)
                startActivity(intent)


                //alert - ciśnienie powyżej 180/120 mm Hg
                if (SpressureV.toInt() > 179 || DpressureV.toInt() > 119) {
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

