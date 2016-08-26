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
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d("onMessageReceived", "From: " + remoteMessage.getFrom());
        Log.d("onMessageReceived", "Notification Message Body: " + remoteMessage.getNotification().getBody());
        ArrayMap<String, String> data = (ArrayMap<String, String>) remoteMessage.getData();
        String cpfAdotante = data.get("cpfAdotante");

        Bundle bundle = new Bundle();
        bundle.putString("cpfAdotante", cpfAdotante);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_pets_black_24px)
                        .setContentTitle("Notificação Be My Pet")
                        .setContentText(remoteMessage.getNotification().getBody());
        /**
         * TODO arrumar para a intent receber o bundle do pet antes de abrir a visualizacao do pet.
         * talvez receber o id do pet na notificacao para fazer o select
         */
        // Creates an explicit intent for an Activity in your app
        // como trazer o pet na notificacao?
        Intent resultIntent = new Intent(this, VisualizarUsuario.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(VisualizarPet.class);
        // Adds the Intent that starts the Activity to the top of the stack
        resultIntent.putExtras(bundle);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }




}
