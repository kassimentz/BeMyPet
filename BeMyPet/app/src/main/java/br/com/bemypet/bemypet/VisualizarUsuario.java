package br.com.bemypet.bemypet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.model.Usuario;

public class VisualizarUsuario extends AppCompatActivity {

    public TextView txtText;
    String cpfAdotante;
    List<Usuario> usuarioList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_usuario);

        txtText = (TextView) findViewById(R.id.textView2);

        cpfAdotante = getBundle();
        if(!StringUtils.isNullOrEmpty(cpfAdotante)){
            getUser(cpfAdotante);
        }

    }

    private String getBundle() {

        if (getIntent().getExtras().getString("cpfAdotante") != null) {
            return cpfAdotante = getIntent().getExtras().getString("cpfAdotante");
        }else{
            return null;
        }

    }

    private void getUser(String cpf) {

        final String cpfUser = cpf;
        CadastroUsuario.dbRef.child("usuario").child(cpfUser).addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //usuarioList.add(dataSnapshot.getValue(Usuario.class));
                    txtText.setText(dataSnapshot.getValue(Usuario.class).getNome());
                    Log.i("usuarioList", usuarioList.toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("onCancelled", "getUser:onCancelled", databaseError.toException());
                }
            });
    }
}
