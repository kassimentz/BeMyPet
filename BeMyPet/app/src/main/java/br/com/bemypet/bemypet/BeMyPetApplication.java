package br.com.bemypet.bemypet;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by kassi on 24/07/16.
 */
public class BeMyPetApplication extends Application{

    public FirebaseDatabase database;
    public FirebaseStorage storage;
    public DatabaseReference dbRef;
    public StorageReference stRef;
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());

        database =  FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        storage = FirebaseStorage.getInstance();
        stRef = storage.getReference();
    }
}
