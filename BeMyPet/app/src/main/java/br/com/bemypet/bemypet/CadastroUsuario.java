package br.com.bemypet.bemypet;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.controller.ManagerPreferences;
import br.com.bemypet.bemypet.model.Endereco;
import br.com.bemypet.bemypet.model.Usuario;
import br.com.bemypet.bemypet.service.GPSTracker;
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

    public static FirebaseDatabase database;
    public static FirebaseStorage storage;
    public static DatabaseReference dbRef;
    public static StorageReference stRef;

    // GPSTracker class
    GPSTracker gps;
    Double latitude;
    Double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database =  FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        dbRef.keepSynced(true);

        storage = FirebaseStorage.getInstance();
        stRef = storage.getReference();

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

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CadastroUsuario.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            //Toast.makeText(getApplicationContext(), "Você precisa permitir o acesso", Toast.LENGTH_SHORT).show();
            gps = new GPSTracker(getApplicationContext(), CadastroUsuario.this);
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
            } else {
                gps.showSettingsAlert();
            }
        }
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

        endereco.setLatitude(latitude);
        endereco.setLongitude(longitude);

        user.setEndereco(endereco);

        if(!erro) {
            ManagerPreferences.saveBoolean(this, Constants.CADASTRADO_NO_SISTEMA, true);
            salvarUsuario(user);
            Toast.makeText(getApplicationContext(), "Usuário Cadastrado Com Sucesso", Toast.LENGTH_LONG);
            inicial();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the

                    // contacts-related task you need to do.

                    gps = new GPSTracker(getApplicationContext(), CadastroUsuario.this);

                    // Check if GPS enabled
                    if (gps.canGetLocation()) {

                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();

                        // \n is for new line
                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    } else {
                        // Can't get location.
                        // GPS or network is not enabled.
                        // Ask user to enable GPS/network in settings.
                        gps.showSettingsAlert();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(getApplicationContext(), "Você precisa permitir o acesso", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }

    public void inicial(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    private void salvarUsuario(Usuario user) {

        final Usuario u = user;

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FirebaseInstanceId", "Refreshed token: " + refreshedToken);

        u.setTokenFCM(refreshedToken);
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    dbRef.child("usuario").child(u.getCpf()).setValue(u);
                    ManagerPreferences.saveString(getApplicationContext(), Constants.USUARIO_CPF, u.getCpf());
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

}
