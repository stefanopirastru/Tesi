package com.example.mirco.civichacking;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by mirco on 29/05/2018.
 */

public class Receiver extends BroadcastReceiver {

    String name, place, city, datein, datefi, time , category, description;

    @Override
    public void onReceive(Context context, Intent intent) {

        name = intent.getStringExtra("name");
        place = intent.getStringExtra("place");
        city = intent.getStringExtra("city");
        datein = intent.getStringExtra("datein");
        datefi = intent.getStringExtra("datefi");
        time = intent.getStringExtra("time");
        category = intent.getStringExtra("category");
        description = intent.getStringExtra("description");


        Intent i = new Intent(context, InfoFavorites.class);
        i.putExtra("name", name);
        i.putExtra("place", place);
        i.putExtra("city", city);
        i.putExtra("datein", datein);
        i.putExtra("datefi", datefi);
        i.putExtra("time", time);
        i.putExtra("category", category);
        i.putExtra("description", description);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(InfoFavorites.class);
        stackBuilder.addNextIntent(i);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification = builder.setContentTitle(name)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setVibrate(new long[]{0, 1500})
        .setSound(sound)
        .setContentText(place + "   " + city +"   " + time)
        .setContentIntent(pendingIntent).build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);


    }
}
