package com.example.reminder.Reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.reminder.Music.Media;

import androidx.core.app.NotificationCompat;

public class Receiver extends BroadcastReceiver {
    String isMusic;
    @Override
    public void onReceive(Context context, Intent intent) {
        isMusic = "off";
        String s = intent.getExtras().getString("time");
        String music = intent.getExtras().getString("music");
        String on = intent.getExtras().getString("on");
        if(on!=null){
            isMusic = on;
        }
        Log.i("music","Receiver"+isMusic);


        //Notification
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(s);
        notificationHelper.getManager().notify(1, nb.build());

        //Music
        if(isMusic.equals("on")) {
            Intent myIntent = new Intent(context, Media.class);
            intent.putExtra("music", music);
            context.startService(myIntent);
        }
    }
}
