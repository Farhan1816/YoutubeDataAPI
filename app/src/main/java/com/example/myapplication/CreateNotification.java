package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.session.MediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CreateNotification{
    public static final String CHANNELID = "channel1";

    public static final String PLAY = "play";
    public static final String PAUSE = "pause";
    public static Notification notification;
    private Context context;

    public static void createNotification(Context context, String title, String Artist) {
        Log.d("debug", "CreatingNotify");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.t1);
        PendingIntent pendingIntentPrevious;

        notification = new NotificationCompat.Builder(context, CHANNELID)
                .setSmallIcon(R.drawable.ic_music_note)
                .setContentTitle(title)
                .setContentText(Artist)
                .setLargeIcon(icon)
                .setOnlyAlertOnce(true)//show notification for only first time
                .setShowWhen(false)
                //.addAction(playbutton, "Play", pendingIntentPlay)
//                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
//                        .setShowActionsInCompactView(0, 1, 2)
//                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();


        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("debug", "hereprob");
            return;
        }
        Log.d("debug", "hereprobNo");
        notificationManagerCompat.notify(1, notification);
    }

}
