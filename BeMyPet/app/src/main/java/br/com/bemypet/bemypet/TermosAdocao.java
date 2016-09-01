package br.com.bemypet.bemypet;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

public class TermosAdocao extends AppCompatActivity {

    @BindView(R.id.rgTermosAdocao) public RadioGroup rgTermosAdocao;

    Usuario adotante;
    Pet pet;
    JSONArray jsonArray = new JSONArray();
    OkHttpClient mClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos_adocao);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.termosAdocaoToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        getBundle();
    }


    private void getBundle() {

        if (getIntent().getSerializableExtra("adotante") != null){
            adotante = (Usuario) getIntent().getSerializableExtra("adotante");
        }

        if(getIntent().getSerializableExtra("pet") != null){
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
                //adicionar o adotante no bundle somente se ele clicar em aceito
                //se o aceito tiver clicado, envia uma solicitacao de adocao para o dono do pet
                // envia notificacao firebase xmpp

                if(((RadioButton)findViewById(rgTermosAdocao.getCheckedRadioButtonId() )).getText().toString().equalsIgnoreCase("Aceito")){

                    String to = pet.getDoador().getTokenFCM(); // the notification key
                    String title = "Be My Pet";
                    String body = pet.getNome()+ " diz: Alguém quer me adotar";

                    int icon = R.drawable.ic_pets_black_24px;
                    String message = "O usuario " + adotante.getNome() + " quer adotar o pet "+ pet.getNome();
                    jsonArray.put(to);
                    //to, title, body, icon, message, adotante, doador, pet, tipoNotificacao
                    sendMessage(jsonArray,title,body,icon,message, adotante.getCpf(), pet.getDoador().getCpf(), pet.getId(), Constants.QUERO_ADOTAR);

                }else{
                    Toast.makeText(TermosAdocao.this, "Você só poderá prosseguir após aceitar os termos de adoção! ", Toast.LENGTH_LONG).show();
                }


        }
        return super.onOptionsItemSelected(item);
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
                    data.put("origem", "");
                    data.put("destino", "");
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
                    Toast.makeText(TermosAdocao.this, "Solicitação enviada com sucesso. Aguarde a análise do dono do pet.", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(TermosAdocao.this, "Falha no envio da solicitação. Um erro ocorreu. Tente novamente.", Toast.LENGTH_LONG).show();
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
