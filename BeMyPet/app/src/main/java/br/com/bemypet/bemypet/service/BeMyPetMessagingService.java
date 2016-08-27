    package br.com.bemypet.bemypet.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import br.com.bemypet.bemypet.R;
import br.com.bemypet.bemypet.VisualizarPet;
import br.com.bemypet.bemypet.VisualizarUsuario;
import br.com.bemypet.bemypet.model.Usuario;

    /**
 * Created by objectedge on 8/22/16.
 */

public class BeMyPetMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        ArrayMap<String, String> data = (ArrayMap<String, String>) remoteMessage.getData();
        String cpfAdotante = data.get("cpfAdotante");

        Bundle bundle = new Bundle();
        bundle.putString("cpfAdotante", cpfAdotante);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_pets_black_24px)
                        .setContentTitle("Notificação Be My Pet")
                        .setContentText(remoteMessage.getNotification().getBody());

        Intent resultIntent = new Intent(this, VisualizarUsuario.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(VisualizarPet.class);
        resultIntent.putExtras(bundle);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }




}
