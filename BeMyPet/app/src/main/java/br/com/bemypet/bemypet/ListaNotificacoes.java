package br.com.bemypet.bemypet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.bemypet.bemypet.adapter.RecyclerViewAdapter;
import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.controller.ManagerPreferences;
import br.com.bemypet.bemypet.model.Notificacao;



public class ListaNotificacoes extends AppCompatActivity {
    String usuarioCpf;
    List<Notificacao> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notificacoes);

        if(!StringUtils.isNullOrEmpty(ManagerPreferences.getString(this, Constants.USUARIO_CPF))) {
            usuarioCpf = ManagerPreferences.getString(this, Constants.USUARIO_CPF);
        }

        getNotificacoes(usuarioCpf);


    }


    private void getNotificacoes(String usuarioCpf) {
        Log.i("usuarioCpf", usuarioCpf);
        final String cpf = usuarioCpf;

        DatabaseReference myRef = CadastroUsuario.dbRef.child("usuario").child(cpf).child("notificacoes").getRef();
        Query query = myRef.orderByChild("id");

        query.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()){
                    Notificacao notificacao = snap.getValue(Notificacao.class);
                    Log.i("getNotificacao", notificacao.toString());
                    data.add(notificacao);
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(data, getApplication());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
            public void onCancelled(DatabaseError databaseError) { }
        });


    }


}
