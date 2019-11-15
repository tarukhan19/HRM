package com.hrm.hrm.FCM;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hrm.hrm.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    int MESSAGE_NOTIFICATION_ID = 0;
    Intent intent;
    private static final int BADGE_ICON_SMALL = 1;

    String message, pushType;
 //   int studioID, freelancerId, quote_id;
    String reciver_id, sender_id, product_id,productname;

    private SharedPreferences sp;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        pushType = remoteMessage.getData().get("type");
        message = remoteMessage.getData().get("message").replace("\"", "");



        sendNotification(getResources().getString(R.string.app_name), message);
    }

    private void sendNotification(String title, String body) {
        Context context = getBaseContext();


        String id = "my_channel_01";



        MESSAGE_NOTIFICATION_ID = (int) (System.currentTimeMillis() & 0xfffffff);

        PendingIntent pIntent = PendingIntent.getActivity(context, MESSAGE_NOTIFICATION_ID, intent, MESSAGE_NOTIFICATION_ID);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setColor(getResources().getColor(R.color.colorPrimary));
        } else {
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setColor(getResources().getColor(R.color.colorPrimary));
        }


        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("Htlez")

                .setContentText(body)
                .setContentIntent(pIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                        .setChannelId(id);


        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);//
            int importance = NotificationManager.IMPORTANCE_HIGH;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(id, name, importance);
                mNotificationManager.createNotificationChannel(mChannel);
                mChannel.setShowBadge(true);

            }
        }
        mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;

        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }
}