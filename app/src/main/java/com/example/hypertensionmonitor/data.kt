package com.example.hypertensionmonitor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_data.*
import java.io.*
import kotlin.math.roundToInt



class data : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        //funkcja czytająca zawartość pliku csv
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



        //dane do podsumowania
        val meassurment = dataReading()

        var splitRes: List<String>
        var sysRes = emptyArray<Double>()
        var diaRes = emptyArray<Double>()

        //do rozróżnienia działania leków
        //var medicinesMonitor= emptyArray<Boolean>()
        var sysMedTaken = emptyArray<Double>()
        var sysMedNotTaken = emptyArray<Double>()
        var diaMedTaken = emptyArray<Double>()
        var diaMedNotTaken = emptyArray<Double>()

        for (i in 1..meassurment.size - 1) {
            splitRes = meassurment[i].split(";")
            sysRes = sysRes + splitRes[1].toDouble()//Wszystkie wyniki ciśnienia skurczowego
            diaRes = diaRes + splitRes[2].toDouble()//Wszystkie wyniki ciśnienia rozkuroczwego
            //medicinesMonitor=medicinesMonitor+splitRes[3].toBoolean()
            if (splitRes[3].toBoolean()==true){
                sysMedTaken=sysMedTaken+splitRes[1].toDouble()
                diaMedTaken=diaMedTaken+splitRes[2].toDouble()
            }else{
                sysMedNotTaken=sysMedNotTaken+splitRes[1].toDouble()
                diaMedNotTaken=diaMedNotTaken+splitRes[2].toDouble()
            }
        }
        //domyślnie - dane ze wszystkich pomiarów
        var meanSysG = sysRes.average().roundToInt().toString()
        var meanDiaG=diaRes.average().roundToInt().toString()
        mean.setText("$meanSysG / $meanDiaG")
        var maxSysG=sysRes.max()?.toInt().toString()
        var maxDiaG= diaRes.max()?.toInt().toString()
        max.setText("$maxSysG / $maxDiaG")
        var minSysG=sysRes.min()?.toInt().toString()
        var minDiaG= diaRes.min()?.toInt().toString()
        min.setText("$minSysG / $minDiaG")

        var meanSysT=sysMedTaken.average().roundToInt().toString()
        var meanDiaT=diaMedTaken.average().roundToInt().toString()

        var maxSysT=sysMedTaken.max()?.toInt().toString()
        var maxDiaT= diaMedTaken.max()?.toInt().toString()

        var minSysT=sysMedTaken.min()?.toInt().toString()
        var minDiaT= diaMedTaken.min()?.toInt().toString()


        var meanSysNT=sysMedNotTaken.average().roundToInt().toString()
        var meanDiaNT=diaMedNotTaken.average().roundToInt().toString()

        var maxSysNT=sysMedNotTaken.max()?.toInt().toString()
        var maxDiaNT= diaMedNotTaken.max()?.toInt().toString()

        var minSysNT=sysMedNotTaken.min()?.toInt().toString()
        var minDiaNT= diaMedNotTaken.min()?.toInt().toString()



        general.setOnClickListener {
            mean.setText("$meanSysG / $meanDiaG")
            max.setText("$maxSysG / $maxDiaG")
            min.setText("$minSysG / $minDiaG")
        }

        medsT.setOnClickListener {
            mean.setText("$meanSysT / $meanDiaT")

            max.setText("$maxSysT / $maxDiaT")
            min.setText("$minSysG / $minDiaG")
        }

        medsNT.setOnClickListener {
            mean.setText("$meanSysNT / $meanDiaNT")
            max.setText("$maxSysNT / $maxDiaNT")
            min.setText("$minSysNT / $minDiaNT")
        }




        //Zakres wykresu
        fun getSelectedRadioButtonTxt(id: Int): CharSequence? {
            val group = findViewById<RadioGroup>(id)
            val selected = findViewById<RadioButton>(group.checkedRadioButtonId)

            return selected.text
        }


        weekChartShow.setOnClickListener {

            var status = getSelectedRadioButtonTxt(R.id.graphRange)

            //wysłanie informacji o statusie do layoutu z wykresem
            val intent = Intent(this, WeekChart::class.java)
            intent.putExtra("status", status)
            startActivity(intent)
        }

        val results = dataReading().toString()
        //udostępnianie pliku
        getFile.setOnClickListener {


            val intent =Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,results )//mozliwe ze tylko tego duzego stringa
            intent.type = "text/csv"

            startActivity(Intent.createChooser(intent,"Share:"))


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

    fun onRadioButtonClicked(view: View) {}
}

