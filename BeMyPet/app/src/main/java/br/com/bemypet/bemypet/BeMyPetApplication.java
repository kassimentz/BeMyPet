package br.com.bemypet.bemypet;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by kassi on 24/07/16.
 */
public class BeMyPetApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
