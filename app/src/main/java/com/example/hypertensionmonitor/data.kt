package com.example.hypertensionmonitor

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
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


        //zczytanie pliku
        val meassurment = dataReading()

        //Lista z rozłączoną zawartością pliku
        var splitRes: List<String>
        //Listy z wynikami pomiarów
        var sysRes = emptyArray<Double>()
        var diaRes = emptyArray<Double>()

        //do rozróżnienia działania leków
        var sysMedTaken = emptyArray<Double>()
        var sysMedNotTaken = emptyArray<Double>()
        var diaMedTaken = emptyArray<Double>()
        var diaMedNotTaken = emptyArray<Double>()

        for (i in 1..meassurment.size - 1) {
            splitRes = meassurment[i].split(";")
            sysRes = sysRes + splitRes[1].toDouble()//Wszystkie wyniki ciśnienia skurczowego
            diaRes = diaRes + splitRes[2].toDouble()//Wszystkie wyniki ciśnienia rozkuroczwego
            if (splitRes[3].toBoolean()==true){
                sysMedTaken=sysMedTaken+splitRes[1].toDouble()
                diaMedTaken=diaMedTaken+splitRes[2].toDouble()
            }else{
                sysMedNotTaken=sysMedNotTaken+splitRes[1].toDouble()
                diaMedNotTaken=diaMedNotTaken+splitRes[2].toDouble()
            }
        }

        //podsumowanie
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

        //podsumowanie dla wyników, kiedy leki zostały wzięte
        var meanSysT=sysMedTaken.average().roundToInt().toString()
        var meanDiaT=diaMedTaken.average().roundToInt().toString()
        var maxSysT=sysMedTaken.max()?.toInt().toString()
        var maxDiaT= diaMedTaken.max()?.toInt().toString()
        var minSysT=sysMedTaken.min()?.toInt().toString()
        var minDiaT= diaMedTaken.min()?.toInt().toString()

        //podsumowanie dla wyników, kiedy leki nie zostały wzięte
        var meanSysNT=sysMedNotTaken.average().roundToInt().toString()
        var meanDiaNT=diaMedNotTaken.average().roundToInt().toString()
        var maxSysNT=sysMedNotTaken.max()?.toInt().toString()
        var maxDiaNT= diaMedNotTaken.max()?.toInt().toString()
        var minSysNT=sysMedNotTaken.min()?.toInt().toString()
        var minDiaNT= diaMedNotTaken.min()?.toInt().toString()

        //Przyciski zmieniające grupę wziętą do podsumowania (wszystko/kiedy leki zostały wzięte/kiedy leki nie zostały wzięte)
        general.setOnClickListener {
            mean.setText("$meanSysG / $meanDiaG")
            mean.setTextColor(Color.parseColor("#000000"))
            max.setText("$maxSysG / $maxDiaG")
            max.setTextColor(Color.parseColor("#000000"))
            min.setText("$minSysG / $minDiaG")
            min.setTextColor(Color.parseColor("#000000"))
        }

        medsT.setOnClickListener {
            mean.setText("$meanSysT / $meanDiaT")
            mean.setTextColor(Color.parseColor("#0aad3f"))
            max.setText("$maxSysT / $maxDiaT")
            max.setTextColor(Color.parseColor("#0aad3f"))
            min.setText("$minSysT / $minDiaT")
            min.setTextColor(Color.parseColor("#0aad3f"))
        }

        medsNT.setOnClickListener {
            mean.setText("$meanSysNT / $meanDiaNT")
            mean.setTextColor(Color.parseColor("#ad0a1d"))
            max.setText("$maxSysNT / $maxDiaNT")
            max.setTextColor(Color.parseColor("#ad0a1d"))
            min.setText("$minSysNT / $minDiaNT")
            min.setTextColor(Color.parseColor("#ad0a1d"))
        }


        //Zakres wykresu - sprawdzenie, który radio button został wybrany; domyślnie 'all'
        fun getSelectedRadioButtonTxt(id: Int): CharSequence? {
            val group = findViewById<RadioGroup>(id)
            val selected = findViewById<RadioButton>(group.checkedRadioButtonId)

            return selected.text
        }

        //Przejście do widoku z wykresem
        weekChartShow.setOnClickListener {

            //Zabezpieczenie jeśli plik jest zbyt krótki żeby wyświetlić wykres
            if (sysRes.size < 3) {
                Toast.makeText(
                    applicationContext,
                    "Not enought data to make a graph! Add more meassurments!",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            } else {
                var status = getSelectedRadioButtonTxt(R.id.graphRange)

                //wysłanie informacji o statusie do layoutu z wykresem
                val intent = Intent(this, WeekChart::class.java)
                intent.putExtra("status", status)
                startActivity(intent)
            }
        }

        //udostępnianie pliku
        val results = dataReading().toString()
        getFile.setOnClickListener {

            val intent =Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, results)
            intent.type = "text/csv"
            startActivity(Intent.createChooser(intent,"Share:"))


        }

        //Usuwanie danych z pliku
        delete.setOnClickListener {


            try {


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

