package com.example.reminder.Music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.reminder.R;

import androidx.annotation.Nullable;

public class Media extends Service {
    MediaPlayer mediaPlayer[];
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //String music = intent.getExtras().getString("music");
        //Log.i("music","Media "+music);
        //int i = Integer.parseInt(music);
        mediaPlayer = new MediaPlayer[10];
        mediaPlayer[0] = MediaPlayer.create(this, R.raw.nhac);
        mediaPlayer[1] = MediaPlayer.create(this, R.raw.nhac);
        mediaPlayer[2] = MediaPlayer.create(this, R.raw.nhac);

        mediaPlayer[0].start();
        Log.i("Music","Music");
        return START_NOT_STICKY;
    }
}
