package com.example.hypertensionmonitor

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
//import sun.text.normalizer.UTF16.append
import java.io.*
import java.util.ArrayList


class WeekChart : AppCompatActivity() {


    lateinit var series: LineGraphSeries<DataPoint>


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week_chart)

        //Czytanie z pliku
        fun dataReading(): List<String> {
            var sysRes = emptyArray<Int>()
            var meassurment= emptyList<String>()
            try {

                val inputStream: InputStream = FileInputStream(File(filesDir, "results.csv"))
                val reader = BufferedReader(InputStreamReader(inputStream))

                //Zczytanie zawartości pliku
                meassurment = reader.readLines()

                reader.close()
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return meassurment
        }


        val meassurment = dataReading()

        var splitRes: List<String>
        var sysRes = emptyArray<Double>()
        for (i in 1..meassurment.size - 1) {
            splitRes = meassurment[i].split(";")
            sysRes = sysRes + splitRes[1].toDouble()
        }
        println(sysRes.joinToString(" "))





        //tworzenie wykresu
        var x: Double = 0.0

        val graph: GraphView = findViewById(R.id.weekData)

        series = LineGraphSeries<DataPoint>();

        for (i in 0..sysRes.size-1) {
            x = i.toDouble()
            series.appendData(DataPoint(x, sysRes[i]), true, sysRes.size-1)
        }

        graph.addSeries(series)

        graph.getViewport().setMinX(0.0);
        graph.getViewport().setMaxX(sysRes.size.toDouble());
        graph.getViewport().setMinY(60.0);
        graph.getViewport().setMaxY(220.0);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);


    }
}
