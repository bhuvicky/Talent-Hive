package com.bhuvanesh.talenthive.fcm.service;

import com.bhuvanesh.talenthive.util.THPreference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class InstanceIdRefreshService extends FirebaseInstanceIdService {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */

    @Override
    public void onTokenRefresh() {
        String fcmKey = FirebaseInstanceId.getInstance().getToken();
        System.out.println("log refreshed token = " + fcmKey);

        // Update key to server
        THPreference.getInstance().setFcmKey(fcmKey);
        THPreference.getInstance().setFcmKeySent(false);
    }
}
