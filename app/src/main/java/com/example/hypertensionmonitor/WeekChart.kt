package com.example.hypertensionmonitor


import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.io.*


class WeekChart : AppCompatActivity() {


    lateinit var series: LineGraphSeries<DataPoint>
    lateinit var series2: LineGraphSeries<DataPoint>


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week_chart)

        //Czytanie z pliku
        fun dataReading(): List<String> {
            var sysRes = emptyArray<Int>()
            var meassurment = emptyList<String>()
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
        var diaRes = emptyArray<Double>()
        for (i in 1..meassurment.size - 1) {
            splitRes = meassurment[i].split(";")
            sysRes = sysRes + splitRes[1].toDouble()//Wszystkie wyniki ciśnienia skurczowego
            diaRes = diaRes + splitRes[2].toDouble()//Wszystkie wyniki ciśnienia rozkuroczwego
        }


        //Pobór informacji o zakresie wykresu
        //Przeniesienie zmiennych z inf o statusie do wykresu
        val statusD: String = "all data"// w przypadku bezpośredniego przejścia do 'show data'
        var status = intent.getStringExtra("status")
        // w przypadku bezpośredniego przejścia do 'show data'
        if (status == null) status = statusD


        //Zmiana zakresu
        var range = when (status) {
            "all data" -> sysRes.size - 1
            "last week" -> 7
            "last month" -> 30
            else -> sysRes.size - 1
        }
        // jeśli wykonano za mało pomiarów na wykres z tygodnia/miesiąca - wyświetli się wykres ze wszystkich danych
        if (range > sysRes.size - 1) {
            range = sysRes.size - 1
            Toast.makeText(
                applicationContext,
                "Not enought data! You see all data graph instead.",
                android.widget.Toast.LENGTH_LONG
            ).show()
        }


        //tworzenie wykresu
        val graph: GraphView = findViewById(R.id.weekData)

        series = LineGraphSeries<DataPoint>();//systolic
        series2 = LineGraphSeries<DataPoint>();//diastolic

        var x: Double = 0.0
        for (i in 0..range) {
            x = i.toDouble()
            series.appendData(DataPoint(x, sysRes[i]), true, sysRes.size - 1)
            series2.appendData(DataPoint(x, diaRes[i]), true, sysRes.size - 1)
        }
        series2.setCustomPaint(Paint(50))
        graph.addSeries(series)
        graph.addSeries(series2)

        graph.getViewport().setMinX(0.0);
        graph.getViewport().setMaxX(range.toDouble());
        graph.getViewport().setMinY(35.0);
        graph.getViewport().setMaxY(260.0);

        val gridLabel: GridLabelRenderer = graph.getGridLabelRenderer()
        gridLabel.horizontalAxisTitle = "Meassurments"
        gridLabel.verticalAxisTitle = "Blood pressure [mmHg]"

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);


    }
}
