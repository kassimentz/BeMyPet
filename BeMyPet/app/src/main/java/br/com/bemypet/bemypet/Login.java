package br.com.bemypet.bemypet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class Login extends AppCompatActivity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken == null){

            LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.e("BeMyPet", "ToString: "+ loginResult.toString());
                    Log.e("BeMyPet", "accessToken: "+ loginResult.getAccessToken());
                    Log.e("BeMyPet", "token: "+ loginResult.getAccessToken().getToken());
                    Log.e("BeMyPet", "granted: "+ loginResult.getRecentlyGrantedPermissions());
                    Log.e("BeMyPet", "denied: "+ loginResult.getRecentlyGrantedPermissions());
                    telaPrincipal();
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });

        }else{
            telaPrincipal();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void telaPrincipal(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
