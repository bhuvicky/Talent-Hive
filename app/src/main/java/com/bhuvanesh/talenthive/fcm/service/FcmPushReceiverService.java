package com.bhuvanesh.talenthive.fcm.service;

import android.text.TextUtils;

import com.bhuvanesh.talenthive.fcm.model.PushNotificationItem;
import com.bhuvanesh.talenthive.util.THPreference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

/**
 * Created by bhuvanesh on 02-05-2017.
 */

public class FcmPushReceiverService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message) {
        boolean isLoggedIn = THPreference.getInstance().isInAppLoggedIn() || THPreference.getInstance().isFBLoggedIn() ||
                THPreference.getInstance().isGoolgeLoggedIn();
        if (!isLoggedIn)
            return;

        String msgData = "";
        msgData = message.getData().get("default");
        System.out.println("log push notif data = " + msgData);
        if (TextUtils.isEmpty(msgData))
            return;

        PushNotificationItem notificationItem = new Gson().fromJson(msgData, PushNotificationItem.class);
    }

    public void showNotification(RemoteMessage message) {

    }
}
