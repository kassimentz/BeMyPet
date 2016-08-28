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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import br.com.bemypet.bemypet.CadastroUsuario;
import br.com.bemypet.bemypet.MainActivity;
import br.com.bemypet.bemypet.R;
import br.com.bemypet.bemypet.VisualizarPet;
import br.com.bemypet.bemypet.VisualizarUsuario;
import br.com.bemypet.bemypet.model.Notificacao;
import br.com.bemypet.bemypet.model.Pet;
import br.com.bemypet.bemypet.model.Usuario;

    /**
 * Created by objectedge on 8/22/16.
 */

public class BeMyPetMessagingService extends FirebaseMessagingService {

    List<Usuario> usuarioList = new ArrayList<>();
    List<Pet> pets = new ArrayList<>();
    HashMap<String, Object> adotanteDoador = new HashMap<>();
    String idPet;
    String cpfAdotante,cpfDoador;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        ArrayMap<String, String> data = (ArrayMap<String, String>) remoteMessage.getData();
        cpfAdotante = data.get("cpfAdotante");
        cpfDoador = data.get("cpfDoador");
        idPet = data.get("idPet");

        HashMap<String, String> cpfs = new HashMap<>();
        cpfs.put("adotante",cpfAdotante);
        cpfs.put("doador",cpfDoador);

        getUser(cpfs);

        Bundle bundle = new Bundle();
        bundle.putString("cpfAdotante", cpfAdotante);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_pets_black_24px)
                        .setContentTitle("Notificação Be My Pet")
                        .setContentText(remoteMessage.getNotification().getBody());


        Intent resultIntent = new Intent(this, VisualizarUsuario.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

        resultIntent.putExtras(bundle);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }

    private Notificacao criarNotificacao(Pet pet) {
        Notificacao n = new Notificacao();
        n.setCpfAdotante(((Usuario) adotanteDoador.get("adotante")).getCpf());
        n.setCpfDoador(((Usuario) adotanteDoador.get("doador")).getCpf());
        n.setIdPet(pet.getId());
        n.setData(System.currentTimeMillis());
        Log.i("notificacao", n.toString());

        Usuario doador = (Usuario) adotanteDoador.get("doador");
        doador.addNotificacao(n);
        updateUser(doador);
        return n;
    }

    private void updateUser(Usuario doador) {

        String key = CadastroUsuario.dbRef.child("usuario").child(doador.getCpf()).getKey();
        Map<String, Object> userValues = doador.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/usuario/" + key, userValues);
        CadastroUsuario.dbRef.updateChildren(childUpdates);
    }

    private void getPet(String id) {

        CadastroUsuario.dbRef.child("pet").child(id).addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Notificacao notificacao = criarNotificacao(dataSnapshot.getValue(Pet.class));
                    Log.i("criarNotificacao", notificacao.toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("onCancelled", "getPet:onCancelled", databaseError.toException());
                }

            }
        );
    }


    private void getUser(HashMap<String, String> cpfs) {

        for (Map.Entry<String,String> pair : cpfs.entrySet()) {
            final String key = pair.getKey();
            String cpf = pair.getValue();

            CadastroUsuario.dbRef.child("usuario").child(cpf).addListenerForSingleValueEvent(
                new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        adotanteDoador.put(key, dataSnapshot.getValue(Usuario.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("onCancelled", "getUser:onCancelled", databaseError.toException());
                    }
                }
            );
        }

        getPet(idPet);
    }

}
