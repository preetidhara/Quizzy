package com.example.quizzy

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.media.RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FireBaseMsg : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    override fun onMessageReceived(remotemsg: RemoteMessage) {
        super.onMessageReceived(remotemsg)
            if (remotemsg!=null){
                showNotification(remotemsg.notification!!.title,
                remotemsg.notification!!.body)
            }
    }

    private fun showNotification(title: String?, Body: String?) {

        var intent = Intent(this,MainActivity::class.java)
        var pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT)

        var cId = "fcm_default_channel"

        val sound=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification=NotificationCompat.Builder(this,cId)
            .setSmallIcon(R.drawable.trophyicon)
            .setContentTitle(title)
            .setContentText(Body)
            .setSound(sound)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)

        val NotificationManger=getSystemService(Context.NOTIFICATION_SERVICE)
        as NotificationManager


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var oChannel = NotificationChannel(cId,"Customer",NotificationManager.IMPORTANCE_HIGH)
            NotificationManger.createNotificationChannel(oChannel)
        }
        NotificationManger.notify(0,notification.build())

    }
}