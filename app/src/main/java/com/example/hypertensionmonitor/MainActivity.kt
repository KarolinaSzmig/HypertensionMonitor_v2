package com.example.hypertensionmonitor
//package com.example.vicky.notificationexample


import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

//powiadomienia
    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel //użytkownik moze zdezaktywowac
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.vicky.notificationexample"
    private val description = "Test notification"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // godzina aktualna, żeby ustawic powiadomienia
        val c = Calendar.getInstance()
        val cHour = c.get(Calendar.HOUR_OF_DAY).toString()
        val cMinute = c.get(Calendar.MINUTE).toString()

        //Przeniesienie zmiennych z inf o godzinie do MainActivity
        val testHour:String? = intent.getStringExtra("MNhour")
//
        ifMeassure.setText(testHour)












        goMeassure.setOnClickListener {
            val intent = Intent(this,meassure::class.java)
            startActivity(intent)
        }

        goShow.setOnClickListener {
            val intent=Intent(this,data::class.java)
            startActivity(intent)
        }

        goSettings.setOnClickListener {
            val intent=Intent(this,settings::class.java)
            startActivity(intent)
        }

        //powiadomienia cd
        notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



        tryN.setOnClickListener {


            val intent = Intent(this,LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                notificationChannel =
                    NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)


                builder = Notification.Builder(this, channelId)
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
            }else{

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
            notificationManager.notify(1234,builder.build())
        }






    }

}
