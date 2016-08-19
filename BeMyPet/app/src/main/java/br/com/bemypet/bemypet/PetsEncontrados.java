package br.com.bemypet.bemypet;

import android.content.Intent;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
                createImage(linearLayout, cat);
            }

        }
        if(!dogs.isEmpty()){
            for (Pet dog : dogs) {
                createImage(linearLayout, dog);
            }
        }

        if(!hamsters.isEmpty()){
            for (Pet hamster : hamsters) {
                createImage(linearLayout, hamster);
            }
        }

        if(!birds.isEmpty()){
            for (Pet bird : birds) {
                createImage(linearLayout, bird);
            }
        }

    }

    private void createImage(final LinearLayout linearLayout, final Pet pet) {

        if(pet.getImagens() != null) {
            for (Uri img : pet.getImagens()) {

                ((BeMyPetApplication)getApplication()).stRef.child("images/"+img).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Use the bytes to display the image

                        //get the item_pet_encontrado layout
                        View inflatedLayout = getLayoutInflater().inflate(R.layout.item_pet_encontrado, null, false);

                        ImageView mThumbnailPreview = (ImageView) findViewById(R.id.imgPetEncontrado);
                        mThumbnailPreview.setImageBitmap(
                                BitmapFactory.decodeByteArray(bytes, 0, bytes.length)
                        );
                        mThumbnailPreview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));

                        TextView txtNome = (TextView) findViewById(R.id.txtNomePetEncontrado);
                        txtNome.setText(pet.getNome());

                        TextView txtIdadeAproximada = (TextView) findViewById(R.id.txtIdadeAproximada);
                        txtIdadeAproximada.setText(pet.getIdadeAproximade().toString());

                        //add item_pet_encontrado to linear layout
                        linearLayout.addView(inflatedLayout);
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

    public void visualizarPet(View v){

        Intent intent = new Intent(getApplicationContext(), VisualizarPet.class);
        //TODO verificar o pet clicado e enviar no put extra
        //intent.putExtras(b);
        startActivity(intent);
        finish();
    }
    
}
