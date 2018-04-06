package org.pceindicator.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.pceindicator.com.NotificationService;

/**
 * Created by ABHIJAY on 1/30/2018.
 */

public class StartService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            Intent serviceIntent = new Intent(context, NotificationService.class);
            context.startService(serviceIntent);
        }

    }
}
