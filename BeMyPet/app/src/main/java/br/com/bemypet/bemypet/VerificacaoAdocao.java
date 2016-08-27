package br.com.bemypet.bemypet;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.controller.ManagerPreferences;
import br.com.bemypet.bemypet.model.Pet;
import br.com.bemypet.bemypet.model.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VerificacaoAdocao extends AppCompatActivity {

    List<Usuario> usuarioList = new ArrayList<>();

    @BindView(R.id.rgOpcoesTemPets) public RadioGroup rgOpcoesTemPets;
    @BindView(R.id.rgOpcoesTemTela) public RadioGroup rgOpcoesTemTela;

    Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacao_adocao);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.verificacaoAdocaoToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        if(!StringUtils.isNullOrEmpty(ManagerPreferences.getString(this, Constants.USUARIO_CPF))) {
            getUser(ManagerPreferences.getString(this, Constants.USUARIO_CPF));
        }

        pet = new Pet();

        //pegar o pet selecionado
        getBundle();
    }


    private void getBundle() {

        if (getIntent().getSerializableExtra("pet") != null){
            pet = (Pet) getIntent().getSerializableExtra("pet");
        }

    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_prosseguir, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menuForward:
                //buscar o usuario, fazer update dos seus dados, salvar no bundle
                //enviar para a tela de termos de adocao
                Bundle bundle = updateAdotante();
                Intent i = new Intent(getApplicationContext(), TermosAdocao.class);
                i.putExtras(bundle);
                startActivity(i);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }


    private Bundle updateAdotante() {

        Usuario adotante = usuarioList.get(0);

        final String jaTevePet = ((RadioButton) findViewById(rgOpcoesTemPets.getCheckedRadioButtonId())).getText().toString();
        adotante.setJaTevePete(jaTevePet);

        final Boolean possuiTelas = ((RadioButton)findViewById(rgOpcoesTemTela.getCheckedRadioButtonId() )).getText().toString().equalsIgnoreCase("Sim");
        adotante.setPossuiTelaNasJanelas(possuiTelas);
        
        updateUser(adotante);


        Bundle bundle = new Bundle();
        if(!usuarioList.isEmpty()){
            bundle.putSerializable("adotante", adotante);
        }
        bundle.putSerializable("pet", pet);
        return bundle;
    }

    //essa atualizacao só sera feita quando o dono aceitar a adocao.
    //agora so atualizo o cadastro do usuario.
    private void updatePet(Usuario adotante) {
        pet.setAdotante(adotante);

        String key = CadastroUsuario.dbRef.child("pet").child(pet.getId()).getKey();
        Map<String, Object> userValues = pet.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/pet/" + key, userValues);
        CadastroUsuario.dbRef.updateChildren(childUpdates);
    }

    private void updateUser(Usuario adotante) {

        String key = CadastroUsuario.dbRef.child("usuario").child(adotante.getCpf()).getKey();
        Map<String, Object> userValues = adotante.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/usuario/" + key, userValues);
        CadastroUsuario.dbRef.updateChildren(childUpdates);
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
