package com.dieam.reactnativepushnotification.modules;

import android.app.Application;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;

import static com.dieam.reactnativepushnotification.modules.RNPushNotification.LOG_TAG;

public class RNPushNotificationActions extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
      String intentActionPrefix = context.getPackageName() + ".ACTION_";

      Log.i(LOG_TAG, "RNPushNotificationBootEventReceiver loading scheduled notifications");

      if (null == intent.getAction() || !intent.getAction().startsWith(intentActionPrefix)) {
        return;
      }

      final Bundle bundle = intent.getBundleExtra("notification");

      // Dismiss the notification popup.
      NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
      int notificationID = Integer.parseInt(bundle.getString("id"));

      boolean autoCancel = bundle.getBoolean("autoCancel", true);

      if(autoCancel) {
        if (bundle.containsKey("tag")) {
            String tag = bundle.getString("tag");
            manager.cancel(tag, notificationID);
        } else {
            manager.cancel(notificationID);
        }
      }

      boolean invokeApp = bundle.getBoolean("invokeApp", true);

      // Notify the action.
      if(invokeApp) {
          RNPushNotificationHelper helper = new RNPushNotificationHelper((Application) context.getApplicationContext());

          helper.invokeApp(bundle);
      } else {
        Intent serviceIntent = new Intent(context, RNPushNotificationTaskService.class);
        serviceIntent.putExtra("taskKey", RNPushNotificationTaskService.TASK_ON_LOCAL_NOTIFICATION_ACTION);
        serviceIntent.putExtras(bundle);

        context.startService(serviceIntent);
        HeadlessJsTaskService.acquireWakeLockNow(context);
      }
    }
}