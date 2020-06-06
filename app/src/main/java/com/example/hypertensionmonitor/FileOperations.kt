package com.example.hypertensionmonitor

import android.content.Context
import android.os.Environment
import android.widget.Toast
import java.io.*
import java.util.ArrayList

class FileOperations {
        private var context: Context
    private var name: String? = null
    private var text: String? = null

    internal constructor(con: Context, name: String, text: String?) {
        context = con
        this.text = text
        this.name = "$name.txt"
    }

    internal constructor(context: Context) {
        this.context = context
    }

    fun saveSettingsData(append: Boolean) {
        try { //utworzenie pliku do zapisu
            val path = Environment.getExternalStorageDirectory()
            val myFile = File(path, name)
            val fOut = FileOutputStream(myFile, append)
            val out = OutputStreamWriter(fOut)
            //zapisanie do pliku
            out.write(text)
            out.flush()
            out.close()
            //wyswietlenie komunikatu, że zapisano dane
            Toast.makeText(context, "Data Saved", Toast.LENGTH_LONG).show()
        } catch (e: IOException) { //obsluga wyjatku
//w razie niepowodzenia zapisu do pliku zostaje wyswietlony komunikat a w konsoli zrzut stosu
            Toast.makeText(context, "Data Could not be added", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
///INTERESUJE MNIE
    fun saveData(append: Boolean) {
        try {
            val root = Environment.getExternalStorageDirectory()
            val dir = File(root.absolutePath + "/MES/")
            dir.mkdirs()
            val myFile = File(dir, name)
            val fOut = FileOutputStream(myFile, append)
            val out = OutputStreamWriter(fOut)
            //zapisanie do pliku
            out.write(text)
            out.flush()
            out.close()
            //wyswietlenie komunikatu, że zapisano dane
            Toast.makeText(context, "Data Saved", Toast.LENGTH_LONG).show()
        } catch (e: IOException) { //obsluga wyjatku
//w razie niepowodzenia zapisu do pliku zostaje wyswietlony komunikat a w konsoli zrzut stosu
            Toast.makeText(context, "Data Could not be added", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
//TROCHE MNIE INTERSUJE
    fun readSettings(): ArrayList<Int?>? {
        var output: ArrayList<Int?>? = null
        try {
            output = ArrayList()
            val path = Environment.getExternalStorageDirectory()
            //                    Environment.getExternalStoragePublicDirectory(
//                    Environment.DIRECTORY_DOCUMENTS);
            val myFile = File(path, "settings.txt")
            val inputStream: InputStream = FileInputStream(myFile)
            val reader =
                BufferedReader(InputStreamReader(inputStream))
            var line: String
            while (reader.readLine().also { line = it } != null) {
                for (s in line.split(";").toTypedArray()) {
                    output.add(Integer.valueOf(s))
                }
            }
            reader.close()
            inputStream.close()
        } catch (e: IOException) { //obsługa wyjątku wraz z wyswietleniem uzytkownikowi komunikatu
            Toast.makeText(context, "Cannot read data", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
        return output
    }
}