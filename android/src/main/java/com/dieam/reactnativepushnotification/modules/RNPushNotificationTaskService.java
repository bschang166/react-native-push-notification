package com.dieam.reactnativepushnotification.modules;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

import javax.annotation.Nullable;

public class RNPushNotificationTaskService extends HeadlessJsTaskService {
  public static final String TASK_ON_LOCAL_NOTIFICATION_ACTION = "onLocalNotificationAction";
  public static final String TASK_ON_REGISTER_REMOTE_NOTIFICATION = "onRegisterRemoteNotification";
  public static final String TASK_ON_RECEIVE_REMOTE_NOTIFICATION = "onReceiveRemoteNotification";

  @Nullable
  @Override
  protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
    String task = intent.getStringExtra("taskKey");

    Bundle bundle = intent.getExtras();
    if (bundle != null) {
      return new HeadlessJsTaskConfig(task, Arguments.fromBundle(bundle), 0, true);
    }
    return null;
  }


}
