package com.example.hypertensionmonitor

import android.R.attr.name
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
//import sun.text.normalizer.UTF16.append
import java.io.*


class WeekChart : AppCompatActivity() {



lateinit var series: LineGraphSeries<DataPoint>
//csvheader

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week_chart)


        var SpressureList = emptyArray<Double>()
        //sprawdzenie, czy przekazano zmienną z meassurre
        val sPressureResult:String? = intent.getStringExtra("Spressure")

       //tu bylo z csv

//        //czytanie z pliku
//
//        var fileReader: BufferedReader? = null
//        try {
//            var line:String?
//
//            fileReader = BufferedReader(FileReader("pressure_results.csv"))
//            fileReader.readLine()
//
//            line=fileReader.readLine()
//            println(line)
//
//        }catch (e:Exception){
//            println("Reading CSV error!")
//            e.printStackTrace()
//        }


        //Lista z wynikami - wersja robocza
        if (sPressureResult != null) {
            SpressureList = SpressureList + sPressureResult.toDouble()
            SpressureList = SpressureList + sPressureResult.toDouble()
            SpressureList = SpressureList + sPressureResult.toDouble()
        }


        //tworzenie wykresu
        var x:Double=5.0
        var y:Double=4.0

        val graph :GraphView= findViewById (R.id.weekData)
        series =  LineGraphSeries < DataPoint >();

        for (i in 0..2){
            x += 1
            y=SpressureList[i]-x
//            if (sPressureResult != null) {
//                y=sPressureResult.toDouble()-x
//            }
            series.appendData(DataPoint(x,y),true,101)
        }
        graph.addSeries(series)





    }
}
