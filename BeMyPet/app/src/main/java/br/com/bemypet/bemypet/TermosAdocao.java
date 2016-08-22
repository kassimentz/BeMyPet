package br.com.bemypet.bemypet;

import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.bemypet.bemypet.model.Pet;
import br.com.bemypet.bemypet.model.Usuario;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TermosAdocao extends AppCompatActivity {

    Usuario adotante;
    Pet pet;
    JSONArray jsonArray = new JSONArray();
    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    OkHttpClient mClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos_adocao);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.termosAdocaoToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        getBundle();
    }


    private void getBundle() {

        if (getIntent().getSerializableExtra("adotante") != null){
            adotante = (Usuario) getIntent().getSerializableExtra("doador");
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
                /*RemoteMessage.Builder builder = new RemoteMessage.Builder(pet.getDono().getTokenFCM());

                FirebaseMessaging fm = FirebaseMessaging.getInstance();
                fm.send(builder
                        .setMessageId((String.valueOf(System.currentTimeMillis())))
                        .addData("Be My Pet", "O Usuário " + adotante.getNome() + "deseja adotar o Pet " + pet.getNome())
                        .build());*/


                jsonArray.put(pet.getDono().getTokenFCM());
                sendMessage(jsonArray,"Hello","How r u","Http:\\google.com","My Name is Vishal");

        }
        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(final JSONArray recipients, final String title, final String body, final String icon, final String message) {

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
                    Toast.makeText(TermosAdocao.this, "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(TermosAdocao.this, "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException {

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key=" + "your server key")
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
