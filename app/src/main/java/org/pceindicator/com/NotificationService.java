package org.pceindicator.com;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by ABHIJAY on 1/30/2018.
 */

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
            Job myJob = dispatcher.newJobBuilder()
                    .setService(JobService.class)
                    .setTag("my-job-tag")
                    .build();

            dispatcher.schedule(myJob);

        } else {

           Log.d("NS","Remote message not scheduled");

        }


        if (remoteMessage.getNotification() != null)

        {
            Log.e("Message", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            NotificationManager mNotificationManager = (NotificationManager) this
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder mBuilder = new Notification.Builder(
                    this).setSmallIcon(R.drawable.ic_mipmap_notification)
                    .setContentTitle("PCE Indicator")
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setAutoCancel(true)
                    .setContentText(remoteMessage.getNotification().getBody());


            mNotificationManager.notify(0, mBuilder.build());

        }

    }


}

