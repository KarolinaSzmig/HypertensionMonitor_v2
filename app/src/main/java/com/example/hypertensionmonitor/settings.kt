package com.example.hypertensionmonitor

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*


class settings : AppCompatActivity() {

    //powiadomienia
    lateinit var notificationManager: NotificationManager
    //alarm manager
    lateinit var context: Context
    lateinit var alarmManager: AlarmManager

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //alarm manager
        context = this
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager


        //przełącznik - gdy 'ON', pojawia się TmiePicker i przycisk 'set'
        val switchMorning: Switch = findViewById(R.id.switchMorning)
        switchMorning.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchMorning.setText("ON")
                MNTpicker.visibility = View.VISIBLE
                setMN.visibility = View.VISIBLE


            } else {
                switchMorning.setText("OFF")
                MNTpicker.visibility = View.INVISIBLE
                setMN.visibility = View.INVISIBLE
            }
        }

        //notification manager - do powiadomień w pasku u góry
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //set - ustawianie przypomnień pomiru ciśnienia
        setMN.setOnClickListener {

            //określenie godziny powiadomień
            var MNhour = MNTpicker.hour.toInt() * 60 * 60 * 1000//ms
            var MNminute = MNTpicker.minute.toInt() * 60 * 1000
            var sumMesNotAddTime =
                MNhour + MNminute//suma milisekund od północy do momentu przypomnienia
            //Sprawdzenie ile trzeba dodać do godziny aktualnej
            //teraz
            val c = Calendar.getInstance()
            val cHour = c.get(Calendar.HOUR_OF_DAY)
            val cMinute = c.get(Calendar.MINUTE)
            var nowMili =
                cHour * 60 * 60 * 1000 + cMinute * 60 * 1000//Aktualny czas dziś w milisekundach
            var timeToAdd = 0// Czas do dodania od chwili ustawiania powiadomienia
            if (sumMesNotAddTime > nowMili) {
                timeToAdd = sumMesNotAddTime - nowMili
            } else {
                timeToAdd = (24 * 60 * 60 * 1000 - nowMili) + sumMesNotAddTime
            }


            //Powiadomienie na pasku u góry
            val notifyIntent = Intent(this, Receiver::class.java)
            var pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            //Ustawienie godziny oraz interwału przypomnień
            val alarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeToAdd,
                1000 * 60 * 60 * 24.toLong(), pendingIntent//powtarzanie powiadomienia co 24h
            )


        }


    }

}

//klasa służąca do wyświetlania powiadomień o pomiarach zadanym czasie
class Receiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val intent1 = Intent(context, MyNewIntentService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(intent1)
        } else {
            context!!.startService(intent1)
        }


    }


}

class MyNewIntentService : IntentService("MyNewIntentService") {


    private val channelId = "com.example.vicky.notificationexample"
    private val description = "Test notification"

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground()
        else startForeground(1, Notification())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startMyOwnForeground() {
        val NOTIFICATION_CHANNEL_ID = "com.example.hypertensionmonitor"
        val channelName = "My Background Service"
        val chan = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager =
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)
        val notificationBuilder =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("App is running in background")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(2, notification)
    }

    //budowanie powiadomień w pasku
    override fun onHandleIntent(intent: Intent?) {
        var builder = Notification.Builder(this)
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intentN = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intentN, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setContentTitle("Meassure reminder")
                .setContentText("It's time to meassure your blood pressure!")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.mipmap.ic_launcher
                    )
                )
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
        }
        builder.setAutoCancel(true)
        builder.setContentIntent(pendingIntent)
        notificationManager.notify(1234, builder.build())

    }

}







