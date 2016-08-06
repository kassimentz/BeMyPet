package br.com.bemypet.bemypet;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.com.bemypet.bemypet.model.Endereco;
import br.com.bemypet.bemypet.model.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroUsuario extends AppCompatActivity {

    @BindView(R.id.txtNomeUsuario) public EditText txtNomeUsuario;
    @BindView(R.id.txtEmailUsuario) public EditText txtEmailUsuario;
    @BindView(R.id.txtCpfUsuario) public EditText txtCpfUsuario;
    @BindView(R.id.txtLogradouroUsuario) public EditText txtLogradouroUsuario;
    @BindView(R.id.txtLogradouroNroUsuario) public EditText txtLogradouroNroUsuario;
    @BindView(R.id.txtLogradouroComplementoUsuario) public EditText txtLogradouroComplementoUsuario;
    @BindView(R.id.txtBairroUsuario) public EditText txtBairroUsuario;
    @BindView(R.id.txtCidadeUsuario) public EditText txtCidadeUsuario;
    @BindView(R.id.txtEstadoUsuario) public EditText txtEstadoUsuario;
    @BindView(R.id.txtPaisUsuario) public EditText txtPaisUsuario;
    @BindView(R.id.txtTelefoneUsuario) public EditText txtTelefoneUsuario;
    @BindView(R.id.txtCepUsuario) public EditText txtCepUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.cadastroUsuarioToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        getBundle();
    }

    private void getBundle() {

        Bundle extra = getIntent().getExtras();
        if (getIntent().getExtras().getString("nome") != null) {
            txtNomeUsuario.setText(getIntent().getExtras().getString("nome"));
        }

        if (getIntent().getExtras().getString("email") != null) {
            txtEmailUsuario.setText(getIntent().getExtras().getString("email"));
        }
    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menuSave:
                cadastrarUsuario();
                Toast.makeText(getApplicationContext(), "Usuário Cadastrado Com Sucesso", Toast.LENGTH_LONG);
                inicial();


        }
        return super.onOptionsItemSelected(item);
    }

    public void cadastrarUsuario(){

        Usuario user = new Usuario();
        user.setEmail(txtEmailUsuario.getText().toString());
        user.setNome(txtNomeUsuario.getText().toString());
        user.setCpf(txtCpfUsuario.getText().toString());
        user.setTelefone(txtTelefoneUsuario.getText().toString());

        Endereco endereco = new Endereco();
        endereco.setCep(Integer.valueOf(txtCepUsuario.getText().toString()));
        endereco.setComplemento(txtLogradouroComplementoUsuario.getText().toString());
        endereco.setLogradouro(txtLogradouroUsuario.getText().toString());
        endereco.setNumero(Integer.valueOf(txtLogradouroNroUsuario.getText().toString()));

        user.setEndereco(endereco);

        salvarUsuario(user);

    }

    public void inicial(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    private void salvarUsuario(Usuario user) {

        ((BeMyPetApplication)getApplication()).dbRef.child("usuario").child(user.getCpf()).setValue(user);

    }

}
