package br.com.bemypet.bemypet.api;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Cassio on 21/08/16.
 */
public class CheckFirebaseConnection {
    public static boolean firebaseConnection(DatabaseReference firebaseRef) {
        final boolean[] result = {false};
        firebaseRef.onDisconnect().removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference firebase) {
                if (error != null) {
                    //System.out.println("could not establish onDisconnect event:" + error.getMessage());
                    result[0] = false;
                } else {
                    result[0] = true;
                }
            }
        });
        return result[0];
    }
}
