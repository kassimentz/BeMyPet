    package br.com.bemypet.bemypet.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import br.com.bemypet.bemypet.CadastroUsuario;
import br.com.bemypet.bemypet.MainActivity;
import br.com.bemypet.bemypet.R;
import br.com.bemypet.bemypet.VisualizarRotaPetActivity;
import br.com.bemypet.bemypet.VisualizarUsuario;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.model.Notificacao;
import br.com.bemypet.bemypet.model.Pet;
import br.com.bemypet.bemypet.model.Usuario;

    /**
 * Created by objectedge on 8/22/16.
 */

public class BeMyPetMessagingService extends FirebaseMessagingService {

    HashMap<String, Object> adotanteDoador = new HashMap<>();
    String cpfAdotante,cpfDoador, tipoNotificacao, idPet, message, origem, destino;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        ArrayMap<String, String> data = (ArrayMap<String, String>) remoteMessage.getData();
        cpfAdotante = data.get("cpfAdotante");
        cpfDoador = data.get("cpfDoador");
        idPet = data.get("idPet");
        tipoNotificacao = data.get("tipoNotificacao");
        message = data.get("message");
        origem = data.get("origem");
        destino = data.get("destino");

        //if tipoNotificacao == queroAdotar, montar notificacao de quero adotar --> VisualizarUsuario
        //if tipoNotificacao == adocaoReprovada, montar notificacao de adocao negada --> Mostra Alerta Informando Reprovacao e Direciona para Main
        //if tipoNotificacao == adocaoAprovada, montar notificacao de adocao aprovada --> Visualizar Rota Para Buscar o Pet

        HashMap<String, String> cpfs = new HashMap<>();
        cpfs.put("adotante",cpfAdotante);
        cpfs.put("doador",cpfDoador);

        getUser(cpfs);

        Bundle bundle = new Bundle();
        bundle.putString("cpfAdotante", cpfAdotante);
        bundle.putString("cpfDoador", cpfDoador);
        bundle.putString("idPet", idPet);
        bundle.putString("message", message);

        //Define sound URI
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_pets_black_24px)
                        .setContentTitle("Notificação Be My Pet")
                        .setSound(soundUri)
                        .setAutoCancel(true)
                        .setContentText(remoteMessage.getNotification().getBody());

        Intent resultIntent = null;

        if(tipoNotificacao.equalsIgnoreCase(Constants.ADOCAO_APROVADA)){
            resultIntent = new Intent(this, VisualizarRotaPetActivity.class);
            bundle.putString("origem", origem);
            bundle.putString("destino", destino);
        }else if (tipoNotificacao.equalsIgnoreCase(Constants.ADOCAO_REPROVADA)){
            resultIntent = new Intent(this, MainActivity.class);
            bundle.putString("tipoNotificacao", tipoNotificacao);
        }else if(tipoNotificacao.equalsIgnoreCase(Constants.QUERO_ADOTAR)){
            resultIntent = new Intent(this, VisualizarUsuario.class);
        }

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
        n.setId(System.currentTimeMillis());
        n.setCpfAdotante(((Usuario) adotanteDoador.get("adotante")).getCpf());
        n.setCpfDoador(((Usuario) adotanteDoador.get("doador")).getCpf());
        n.setIdPet(pet.getId());
        n.setData(System.currentTimeMillis());
        n.setImage(pet.getImagens().get(0));
        n.setStatusNotificacao(tipoNotificacao);
        n.setLida(false);
        n.setNomeAdotante(((Usuario) adotanteDoador.get("adotante")).getNome());
        n.setNomeDoador(((Usuario) adotanteDoador.get("doador")).getNome());
        n.setNomePet(pet.getNome());
        n.setEnderecoAdotante(((Usuario) adotanteDoador.get("adotante")).getEndereco().toString());
        n.setEnderecoDoador(((Usuario) adotanteDoador.get("doador")).getEndereco().toString());

        Usuario doador = (Usuario) adotanteDoador.get("doador");
        doador.addNotificacao(n);
        updateUser(doador);

        Usuario adotante = (Usuario) adotanteDoador.get("adotante");
        adotante.addNotificacao(n);
        updateUser(adotante);




        return n;
    }

    private void updateUser(final Usuario usuario) {

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    CadastroUsuario.dbRef.child("usuario").child(usuario.getCpf()).child("notificacoes").setValue(usuario.getNotificacoes());
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao salvar", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.i("Cancel", "Listener was cancelled");
            }
        });
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
