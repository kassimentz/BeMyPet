package br.com.bemypet.bemypet;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kassi on 24/07/16.
 */
public class BeMyPetApplication extends Application{

    public FirebaseDatabase database;
    public DatabaseReference dbRef;
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());

        database =  FirebaseDatabase.getInstance();
        dbRef = database.getReference();
    }
}
