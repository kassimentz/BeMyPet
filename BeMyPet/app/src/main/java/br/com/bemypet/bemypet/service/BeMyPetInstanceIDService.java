package br.com.bemypet.bemypet.service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bemypet.bemypet.CadastroUsuario;
import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.controller.ManagerPreferences;
import br.com.bemypet.bemypet.model.Usuario;

/**
 * Created by objectedge on 8/22/16.
 */

public class BeMyPetInstanceIDService extends FirebaseInstanceIdService {

    Usuario user;
    List<Usuario> usuarioList = new ArrayList<>();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.

        //sendRegistrationToServer(refreshedToken);
        //get usuario atual e salvar o token nele
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("TESTEFCM", "token no service: " + token);

        if(!StringUtils.isNullOrEmpty(ManagerPreferences.getString(this, Constants.USUARIO_CPF))) {
            getUser(ManagerPreferences.getString(this, Constants.USUARIO_CPF));
        }

        updateUser(token);

    }

    private void updateUser(String refreshedToken) {

        if(!usuarioList.isEmpty()){
            user = usuarioList.get(0);
            user.setTokenFCM(refreshedToken);

            String key = CadastroUsuario.dbRef.child("usuario").child(user.getCpf()).getKey();
            Map<String, Object> userValues = user.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/usuario/" + key, userValues);

            CadastroUsuario.dbRef.updateChildren(childUpdates);
        }
    }

    private void getUser(String cpf) {

        final String cpfUser = cpf;
        CadastroUsuario.dbRef.child("usuario").child(cpfUser).addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    usuarioList.add(dataSnapshot.getValue(Usuario.class));
                    Log.i("usuarioList", usuarioList.toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("onCancelled", "getUser:onCancelled", databaseError.toException());
                }
            });
    }


}
