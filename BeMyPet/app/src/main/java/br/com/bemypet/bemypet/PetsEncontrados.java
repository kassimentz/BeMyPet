package br.com.bemypet.bemypet;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import br.com.bemypet.bemypet.model.Pet;

public class PetsEncontrados extends AppCompatActivity {

    List<Pet> cats = new ArrayList<>();
    List<Pet> dogs = new ArrayList<>();
    List<Pet> birds = new ArrayList<>();
    List<Pet> hamsters = new ArrayList<>();
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_encontrados);
        
        Toolbar myToolbar = (Toolbar) findViewById(R.id.petsEncontradosToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        
        getBundle();
        createThumbs();

    }

    private void createThumbs() {

        if(!cats.isEmpty()){
            for (Pet cat : cats) {
                createImage(linearLayout, cat.getImagens());
            }

        }
        if(!dogs.isEmpty()){
            for (Pet dog : dogs) {
                createImage(linearLayout, dog.getImagens());
            }
        }

        if(!hamsters.isEmpty()){
            for (Pet hamster : hamsters) {
                createImage(linearLayout, hamster.getImagens());
            }
        }

        if(!birds.isEmpty()){
            for (Pet bird : birds) {
                createImage(linearLayout, bird.getImagens());
            }
        }

    }

    private void createImage(final LinearLayout linearLayout, List<Uri> images) {

        if(images != null) {
            for (Uri img : images) {

                ((BeMyPetApplication)getApplication()).stRef.child("images/"+img).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Use the bytes to display the image
                        ImageView mThumbnailPreview = new ImageView(getApplicationContext());
                        mThumbnailPreview.setImageBitmap(
                                BitmapFactory.decodeByteArray(bytes, 0, bytes.length)
                        );
                        mThumbnailPreview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        linearLayout.addView(mThumbnailPreview);
                        setContentView(linearLayout);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        }

    }

    private void getBundle() {

        if (getIntent().getSerializableExtra("dogs") != null){
            dogs = (List<Pet>) getIntent().getSerializableExtra("dogs");
        }

        if (getIntent().getSerializableExtra("cats") != null){
            cats = (List<Pet>) getIntent().getSerializableExtra("cats");
        }

        if (getIntent().getSerializableExtra("birds") != null){
            birds = (List<Pet>) getIntent().getSerializableExtra("birds");
        }

        if (getIntent().getSerializableExtra("hamsters") != null){
            hamsters = (List<Pet>) getIntent().getSerializableExtra("hamsters");
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

        }
        return super.onOptionsItemSelected(item);
    }
    
}
