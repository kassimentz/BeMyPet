package br.com.bemypet.bemypet;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.bemypet.bemypet.adapter.NotificacaoViewHolder;
import br.com.bemypet.bemypet.adapter.RecyclerViewAdapter;
import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.controller.ManagerPreferences;
import br.com.bemypet.bemypet.model.Notificacao;



public class ListaNotificacoes extends AppCompatActivity {
    String usuarioCpf;
    List<Notificacao> data = new ArrayList<>();
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notificacoes);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.listaNotificacoesToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        if(!StringUtils.isNullOrEmpty(ManagerPreferences.getString(this, Constants.USUARIO_CPF))) {
            usuarioCpf = ManagerPreferences.getString(this, Constants.USUARIO_CPF);
        }

        init(usuarioCpf);


    }



    private void init(String cpf) {
        DatabaseReference myRef = CadastroUsuario.dbRef.child("usuario").child(cpf).child("notificacoes").getRef();
        RecyclerView rvNotificacoes = (RecyclerView) findViewById(R.id.recyclerview);
        rvNotificacoes.setHasFixedSize(true);
        rvNotificacoes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RecyclerViewAdapter(
                Notificacao.class,
                R.layout.row_layout,
                NotificacaoViewHolder.class,
                myRef);
        rvNotificacoes.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

//    private void getNotificacoes(String usuarioCpf) {
//        Log.i("usuarioCpf", usuarioCpf);
//        final String cpf = usuarioCpf;
//
//        DatabaseReference myRef = CadastroUsuario.dbRef.child("usuario").child(cpf).child("notificacoes").getRef();
//        Query query = myRef.orderByChild("id");
//
//        query.addValueEventListener(new ValueEventListener() {
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snap : dataSnapshot.getChildren()){
//                    Notificacao notificacao = snap.getValue(Notificacao.class);
//                    data.add(notificacao);
//                }
//
//                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//                RecyclerViewAdapter adapter = new RecyclerViewAdapter(data, getApplication());
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//            }
//            public void onCancelled(DatabaseError databaseError) { }
//        });
//
//
//    }


}
