package br.com.bemypet.bemypet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.controller.ManagerPreferences;

public class Login extends AppCompatActivity {

    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ManagerPreferences.getBoolean(this, Constants.LOGADO_NO_SISTEMA)){

            String nome = ManagerPreferences.getString(this, Constants.USUARIO_NOME);
            String email = ManagerPreferences.getString(this, Constants.USUARIO_EMAIL);
            CreateBundleLogado(nome, email);
        }

        //Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        // Facebook Login
        callbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
   //     if(accessToken == null){

            final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
            List<String> permissions = new ArrayList<>();
            permissions.add("public_profile");
            permissions.add("email");
            permissions.add("user_birthday");

            loginButton.setReadPermissions(permissions);

            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback(){
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.i("BeMyPet", "ToString: "+ response.toString());
                            // get facebook data from login
                            Bundle bFacebookData = getFacebookData(object);
                            Log.i("log", bFacebookData.toString());
                            String nome = bFacebookData.getString("first_name") + " " + bFacebookData.getString("last_name");
                            String email = bFacebookData.getString("email");
                            cadastroUsuario(nome, email);
                            //{"id":"1138061596265568","first_name":"Kassiane","last_name":"Mentz","email":"kassimentz@gmail.com","gender":"female","birthday":"01\/08\/1987"}
                            //redirecionar aqui passando o bundle para a proxima activity
                        }
                    });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id, first_name, last_name, email"); // Parametros que quero
                    request.setParameters(parameters);

                    request.executeAsync();

                    //inicial();
                }


                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });

       // }else{
            //jogar para a tela de login com mensagem de erro.
        //}

    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);

            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));

            return bundle;

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.i("BeMyPet", "displayname: " + acct.getDisplayName());
            Log.i("BeMyPet", "displayname: " + acct.getEmail());

            cadastroUsuario(acct.getDisplayName(), acct.getEmail());
            //inicial();
        } else {
            Log.d("CURSO", result.getStatus().toString());
            Log.d("CURSO", "sign out");
        }
    }

    //montar o bundle com nome e email e passar para a tela de cadastro de usuario, dizendo para finalizar o cadastro

    public void inicial(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    public void cadastroUsuario(String nome, String email){

        ManagerPreferences.saveBoolean(this, Constants.LOGADO_NO_SISTEMA, true);
        ManagerPreferences.saveString(this, Constants.USUARIO_NOME, nome);
        ManagerPreferences.saveString(this, Constants.USUARIO_EMAIL, email);

        CreateBundleLogado(nome, email);
    }

    private void CreateBundleLogado(String nome, String email) {
        Bundle bundle = new Bundle();
        bundle.putString("nome", nome);
        bundle.putString("email", email);
        Intent intent = new Intent(getApplicationContext(), CadastroUsuario.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


}
