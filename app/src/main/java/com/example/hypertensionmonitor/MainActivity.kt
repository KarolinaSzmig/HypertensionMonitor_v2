package com.example.hypertensionmonitor
//package com.example.vicky.notificationexample


import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

//    AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
//    Calendar calendar = Calendar.getInstance();
//    calendar.set(Calendar.HOUR_OF_DAY, 18);
//    Intent intent = new Intent(this, NotificationService.class);
//    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);




    //STAAACK
//    private fun handleNotification() {
//        val alarmIntent = Intent(this, AlarmReceiver::class.java)
//        val pendingIntent =
//            PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//        val alarmManager =
//            getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            System.currentTimeMillis(),
//            5000,
//            pendingIntent
//        )
//    }
//
//    class AlarmReceiver : BroadcastReceiver() {
//        override fun onReceive(
//            context: Context,
//            intent: Intent
//        ) {
//            val now = GregorianCalendar.getInstance()
//            val dayOfWeek = now[Calendar.DATE]
//            if (dayOfWeek != 1 && dayOfWeek != 7) {
//                val mBuilder = NotificationCompat.Builder(context)
//                    .setSmallIcon(R.drawable.ic_launcher_background)
//                    .setContentTitle(context.resources.getString(R.string.app_name))
//                    .setContentText(context.resources.getString(R.string.app_name))
//                val resultIntent = Intent(context, MainActivity::class.java)
//                val stackBuilder = TaskStackBuilder.create(context)
//                stackBuilder.addParentStack(MainActivity::class.java)
//                stackBuilder.addNextIntent(resultIntent)
//                val resultPendingIntent =
//                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
//                mBuilder.setContentIntent(resultPendingIntent)
//                val mNotificationManager =
//                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                mNotificationManager.notify(1, mBuilder.build())
//            }
//        }
//    }




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
        val testMinute:String? = intent.getStringExtra("MNminute")
//
        ifMeassure.setText(testMinute)
        ifTake.setText(cMinute)




        //println("cosiedzieje")





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


//if (testHour==cHour && testMinute==cMinute) {

    tryN.setOnClickListener {


        val intent = Intent(this, LauncherActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

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
                .setContentText("It's time for meassuring your blood pressure!")
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


//}



    }

}
