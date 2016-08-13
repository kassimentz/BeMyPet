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

import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.controller.ManagerPreferences;
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

        if(ManagerPreferences.getBoolean(this, Constants.CADASTRADO_NO_SISTEMA)){
            inicial();
        }

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


        }
        return super.onOptionsItemSelected(item);
    }

    public void cadastrarUsuario(){

        Boolean erro;

        Usuario user = new Usuario();
        if(StringUtils.isNullOrEmpty(txtEmailUsuario.getText().toString())){
            txtEmailUsuario.setError(getString(R.string.required_email_message));
            erro = true;
        }else {
            user.setEmail(txtEmailUsuario.getText().toString());
            erro = false;
        }

        if(StringUtils.isNullOrEmpty(txtNomeUsuario.getText().toString())){
            txtNomeUsuario.setError(getString(R.string.required_nome_message));
            erro = true;
        }else {
            user.setNome(txtNomeUsuario.getText().toString());
            erro = false;
        }

        if(StringUtils.isNullOrEmpty(txtCpfUsuario.getText().toString())){
            txtCpfUsuario.setError(getString(R.string.required_cpf_message));
            erro = true;
        }else{
            user.setCpf(txtCpfUsuario.getText().toString());
            erro = false;
        }

        if(StringUtils.isNullOrEmpty((txtTelefoneUsuario.getText().toString()))){
            txtTelefoneUsuario.setError(getString(R.string.required_telefone_message));
            erro = true;
        }else {
            user.setTelefone(txtTelefoneUsuario.getText().toString());
            erro = false;
        }

        Endereco endereco = new Endereco();
        if(StringUtils.isNullOrEmpty(txtCepUsuario.getText().toString())){
            txtCepUsuario.setError(getString(R.string.required_cep_message));
            erro = true;
        }else{
            endereco.setCep(Integer.valueOf(txtCepUsuario.getText().toString()));
            erro = false;
        }

        endereco.setComplemento(txtLogradouroComplementoUsuario.getText().toString());
        if(StringUtils.isNullOrEmpty(txtLogradouroUsuario.getText().toString())){
            txtLogradouroUsuario.setError(getString(R.string.required_logradouro_message));
            erro = true;
        }else {
            endereco.setLogradouro(txtLogradouroUsuario.getText().toString());
            erro = false;
        }
        if(StringUtils.isNullOrEmpty(txtLogradouroNroUsuario.getText().toString())){
            txtLogradouroNroUsuario.setError(getString(R.string.required_numero_message));
            erro = true;
        }else {
            endereco.setNumero(Integer.valueOf(txtLogradouroNroUsuario.getText().toString()));
            erro = false;
        }

        user.setEndereco(endereco);

        if(!erro) {
            ManagerPreferences.saveBoolean(this, Constants.CADASTRADO_NO_SISTEMA, true);
            salvarUsuario(user);
            Toast.makeText(getApplicationContext(), "Usuário Cadastrado Com Sucesso", Toast.LENGTH_LONG);
            inicial();
        }

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
