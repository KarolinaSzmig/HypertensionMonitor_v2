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
import android.util.Log
import android.view.View
import android.widget.Switch
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
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

            val notifyIntent = Intent(this, Receiver::class.java)
            var pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val second = przykladowagodzina * 1000//toInt//*1000 bo milisekundy CZAAAAAS


            val alarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + second,
                1000 * 60 * 60 * 24.toLong(), pendingIntent
            )

//            val MNhour = MNTpicker.hour.toString()
//            val MNminute = MNTpicker.minute.toString()
//



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

//klasa służąca do wyświetlania powiadomień o zadanym czasie - użyta w 'settings'
class Receiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val intent1 = Intent(context, MyNewIntentService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(intent1)
        } else {
            context!!.startService(intent1)
        }
        Log.d("settings", "Receiver: Twoje powiadomionkoooooooo  :>>>>>>")
        Toast.makeText(
            context,
            "WIIIIIIIIIIIII",
            android.widget.Toast.LENGTH_SHORT
        ).show()

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
        val NOTIFICATION_CHANNEL_ID = "com.example.simpleapp"
        val channelName = "My Background Service"
        val chan = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_NONE
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
            //.setContentIntent(pendingIntent)
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
            //.setContentIntent(pendingIntent)
        }
        builder.setAutoCancel(true)
        builder.setContentIntent(pendingIntent)
        notificationManager.notify(1234, builder.build())

    }

    companion object {
        private const val NOTIFICATION_ID = 3
    }
}
