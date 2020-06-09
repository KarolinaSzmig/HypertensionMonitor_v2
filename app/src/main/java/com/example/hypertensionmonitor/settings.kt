package com.example.hypertensionmonitor

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

class settings : AppCompatActivity() {

    //powiadomienia
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel //użytkownik moze zdezaktywowac
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.vicky.notificationexample"
    private val description = "Test notification"

    lateinit var context: Context
    lateinit var alarmManager: AlarmManager

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        context = this
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val przykladowagodzina = 12


        val MNTpicker: TimePicker = findViewById(R.id.MNTpicker)

        val switchMorning: Switch = findViewById(R.id.switchMorning)
        switchMorning.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchMorning.setText("ON")
                MNTpicker.visibility= View.VISIBLE

                setMN.visibility=View.VISIBLE



            } else {
                switchMorning.setText("OFF")
                MNTpicker.visibility= View.INVISIBLE
                setMN.visibility=View.INVISIBLE
            }
        }

        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager//do powiadomien w pasku


        setMN.setOnClickListener {

            val MNhour = MNTpicker.hour.toString()
            val MNminute = MNTpicker.minute.toString()

            val second = przykladowagodzina * 1000//toInt//*1000 bo milisekundy CZAAAAAS
            val intentN = Intent(context, MainActivity.Receiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, 0, intentN, PendingIntent.FLAG_UPDATE_CURRENT)
            Log.d("settings", "create: ")
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + second,
                pendingIntent
            )


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                notificationChannel =
                    NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)


                builder = Notification.Builder(this, channelId)
                    //.setShowWhen(testHour==cHour&& testMinute==cMinute)//spr
                    .setContentTitle("Meassure reminder")
                    .setContentText("It's time to meassure your blood pressure!")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            this.resources,
                            R.mipmap.ic_launcher
                        )
                    )
                    .setContentIntent(pendingIntent)
            } else {

                builder = Notification.Builder(this)
                    .setContentTitle("Meassure reminder")
                    .setContentText("It's time for meassuring your blood pressure!")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            this.resources,
                            R.mipmap.ic_launcher
                        )
                    )
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234, builder.build())




        }


        val MedTpicker: TimePicker = findViewById(R.id.MedTpicker)
        val switchMedicine: Switch = findViewById(R.id.switchMedicine)

        switchMedicine.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                MedTpicker.visibility=View.VISIBLE
                switchMedicine.setText("ON")
            } else {
                switchMedicine.setText("OFF")
                MedTpicker.visibility=View.INVISIBLE
            }
        }







    }




}
