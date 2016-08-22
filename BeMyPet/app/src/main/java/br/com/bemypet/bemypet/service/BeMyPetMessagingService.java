package br.com.bemypet.bemypet.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by objectedge on 8/22/16.
 */

public class BeMyPetMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d("onMessageReceived", "From: " + remoteMessage.getFrom());
        Log.d("onMessageReceived", "Notification Message Body: " + remoteMessage.getNotification().getBody());

    }
}
