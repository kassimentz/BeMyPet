package br.com.bemypet.bemypet;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.bemypet.bemypet.adapter.ImageAdapter;
import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.model.Pet;
import br.com.bemypet.bemypet.model.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VisualizarUsuario extends AppCompatActivity {

    String cpfAdotante, idPet;
    Usuario adotante = new Usuario();
    Pet pet = new Pet();
    JSONArray jsonArray = new JSONArray();
    OkHttpClient mClient = new OkHttpClient();

    @BindView(R.id.ivUsuarioItem1) public ImageView ivUsuarioItem1;
    @BindView(R.id.ivUsuarioItem2) public ImageView ivUsuarioItem2;
    @BindView(R.id.ivUsuarioItem3) public ImageView ivUsuarioItem3;
    @BindView(R.id.ivUsuarioItem4) public ImageView ivUsuarioItem4;
    @BindView(R.id.ivUsuarioItem5) public ImageView ivUsuarioItem5;
    @BindView(R.id.ivUsuarioItem6) public ImageView ivUsuarioItem6;
    @BindView(R.id.ivUsuarioItem7) public ImageView ivUsuarioItem7;
    @BindView(R.id.txtNomeUsuarioVisualizacao) public TextView txtNomeUsuarioVisualizacao;
    @BindView(R.id.txtCpfUsuarioVisualizacao) public TextView txtCpfUsuarioVisualizacao;
    @BindView(R.id.txtEnderecoUsuarioVisualizacao) public TextView txtEnderecoUsuarioVisualizacao;
    @BindView(R.id.txtTelefoneUsuarioVisualizacao) public TextView txtTelefoneUsuarioVisualizacao;
    @BindView(R.id.txtEmailUsuarioVisualizacao) public TextView txtEmailUsuarioVisualizacao;
    @BindView(R.id.txtJaPossuiPetsUsuarioVisualizacao) public TextView txtJaPossuiPetsUsuarioVisualizacao;
    @BindView(R.id.txtTelasNasJanelasUsuarioVisualizacao) public TextView txtTelasNasJanelasUsuarioVisualizacao;
    @BindView(R.id.chkState1) public ToggleButton chkState1;
    @BindView(R.id.chkState2) public ToggleButton chkState2;
    @BindView(R.id.chkState3) public ToggleButton chkState3;
    @BindView(R.id.chkState4) public ToggleButton chkState4;
    @BindView(R.id.chkState5) public ToggleButton chkState5;
    @BindView(R.id.chkState6) public ToggleButton chkState6;
    @BindView(R.id.chkState7) public ToggleButton chkState7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_usuario);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.visualizarUsuarioToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        getBundle();
        if(!StringUtils.isNullOrEmpty(cpfAdotante)){
            getUser(cpfAdotante);
        }

        if(!StringUtils.isNullOrEmpty(idPet)){
            getPet(idPet);
        }

    }



    private void getBundle() {

        if (getIntent().getExtras().getString("cpfAdotante") != null) {
            cpfAdotante = getIntent().getExtras().getString("cpfAdotante");
        }

        if (getIntent().getExtras().getString("idPet") != null) {
            idPet = getIntent().getExtras().getString("idPet");
        }

    }

    private void getUser(String cpf) {

        final String cpfUser = cpf;
        CadastroUsuario.dbRef.child("usuario").child(cpfUser).addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    verificaValoresNulos(dataSnapshot.getValue(Usuario.class));
                    adotante = dataSnapshot.getValue(Usuario.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("onCancelled", "getUser:onCancelled", databaseError.toException());
                }
            }
        );
    }

    private void getPet(String idPet) {

        final String id = idPet;
        CadastroUsuario.dbRef.child("pet").child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        pet = dataSnapshot.getValue(Pet.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("onCancelled", "getUser:onCancelled", databaseError.toException());
                    }
                }
        );
    }

    private void verificaValoresNulos(Usuario usuario) {

        if(!usuario.getImagens().isEmpty()) {
            ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
            ImageAdapter adapter = new ImageAdapter(this, usuario.getImagens());
            viewPager.setAdapter(adapter);
        }

        if(usuario.getNome() == null || StringUtils.isNullOrEmpty(usuario.getNome())){
            txtNomeUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtNomeUsuarioVisualizacao.setText(usuario.getNome());
        }

        if(usuario.getCpf() == null || StringUtils.isNullOrEmpty(usuario.getCpf())){
            txtCpfUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtCpfUsuarioVisualizacao.setText(usuario.getCpf());
        }

        if(usuario.getEndereco() == null || StringUtils.isNullOrEmpty(usuario.getEndereco().toString())){
            txtEnderecoUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtEnderecoUsuarioVisualizacao.setText(usuario.getEndereco().toString());
        }

        if(usuario.getTelefone() == null || StringUtils.isNullOrEmpty(usuario.getTelefone())){
            txtTelefoneUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtTelefoneUsuarioVisualizacao.setText(usuario.getTelefone());
        }

        if(usuario.getEmail() == null || StringUtils.isNullOrEmpty(usuario.getEmail())){
            txtEmailUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtEmailUsuarioVisualizacao.setText(usuario.getEmail());
        }

        if(usuario.getPossuiTelaNasJanelas() == null || StringUtils.isNullOrEmpty(usuario.getPossuiTelaNasJanelas().toString())){
            txtTelasNasJanelasUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            if(usuario.getPossuiTelaNasJanelas()) {
                txtTelasNasJanelasUsuarioVisualizacao.setText("Sim");
            }else{
                txtTelasNasJanelasUsuarioVisualizacao.setText("Não");
            }
        }

        if(usuario.getJaTevePet() == null || StringUtils.isNullOrEmpty(usuario.getJaTevePet())){
            txtJaPossuiPetsUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtJaPossuiPetsUsuarioVisualizacao.setText(usuario.getJaTevePet());
        }
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

    public void aprovarAdocao(View v){
        //setar adotante
        //setar dono
        //update pet
        pet.setDono(adotante);
        pet.setAdotante(adotante);
        pet.setStatusAdocao(Constants.ADOTADO);
        updatePet(pet);

    }

    public void reprovarAdocao(View v){

        String body = pet.getNome()+ " diz: Status Adoção Reprovada";
        String message = "O usuario " + pet.getDono().getNome() + " não autorizou a adoção do pet "+ pet.getNome();
        //to, title, body, icon, message, adotante, doador, pet, tipoNotificacao
        sendNotification(adotante.getTokenFCM(), body, message, adotante.getCpf(), pet.getDoador().getCpf(), pet.getId(), Constants.ADOCAO_REPROVADA);

    }

    private void updatePet(Pet pPet) {

        String key = CadastroUsuario.dbRef.child("pet").child(pPet.getId()).getKey();
        Map<String, Object> userValues = pPet.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/pet/" + key, userValues);
        CadastroUsuario.dbRef.updateChildren(childUpdates);

        String body = pet.getNome()+ " diz: Status Adoção Aprovada";
        String message = "O usuario " + pet.getDono().getNome() + " autorizou a adoção do pet "+ pet.getNome();
        sendNotification(adotante.getTokenFCM(), body, message, adotante.getCpf(), pet.getDoador().getCpf(), pet.getId(), Constants.ADOCAO_APROVADA);
    }

    private void sendNotification(String to, String body, String message, String adotante, String doador, String pet, String tipoNotificacao) {
        String title = "Be My Pet";
        int icon = R.drawable.ic_pets_black_24px;

        jsonArray.put(to);
        //to, title, body, icon, message, adotante, doador, pet, tipoNotificacao
        sendMessage(jsonArray,title,body,icon,message, adotante, doador, pet, tipoNotificacao);
    }

    public void sendMessage(final JSONArray recipients, final String title, final String body,
                            final int icon, final String message, final String cpfAdotante,
                            final String cpfDoador, final String idPet, final String tipoNotificacao) {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", body);
                    notification.put("title", title);
                    notification.put("icon", icon);

                    JSONObject data = new JSONObject();
                    data.put("message", message);
                    data.put("cpfAdotante", cpfAdotante);
                    data.put("cpfDoador", cpfDoador);
                    data.put("idPet", idPet);
                    data.put("tipoNotificacao", tipoNotificacao);
                    root.put("notification", notification);
                    root.put("data", data);
                    root.put("registration_ids", recipients);

                    String result = postToFCM(root.toString());
                    Log.d("TermosAdocao", "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                    Toast.makeText(getApplicationContext(), "Solicitação enviada com sucesso. Aguarde a análise do dono do pet.", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Falha no envio da solicitação. Um erro ocorreu. Tente novamente.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException {

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(Constants.FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key="+ Constants.NOTIFICATION_KEY)
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
