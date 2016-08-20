package br.com.bemypet.bemypet;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
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
        

        linearLayout = (LinearLayout) findViewById(R.id.llpets);

        getBundle();
        createThumbs();

    }

    private void createThumbs() {

        if(!cats.isEmpty()){
            for (int i = 0; i < cats.size(); i+=2){
                createImage(linearLayout, cats.get(i), i + 1 == cats.size() ? null : cats.get(i + 1));
            }
        }

        if(!dogs.isEmpty()){
            for (int i = 0; i < dogs.size(); i+=2){
                createImage(linearLayout, dogs.get(i), i + 1 == dogs.size() ? null : dogs.get(i + 1));
            }

        }

        if(!hamsters.isEmpty()){
            for (int i = 0; i < hamsters.size(); i+=2){
                createImage(linearLayout, hamsters.get(i), i + 1 == hamsters.size() ? null : hamsters.get(i + 1));
            }
        }

        if(!birds.isEmpty()){
            for (int i = 0; i < birds.size(); i+=2){
                createImage(linearLayout, birds.get(i), i + 1 == birds.size() ? null : birds.get(i + 1));
            }
        }

    }

    private void createImage(final LinearLayout linearLayout, final Pet petLeft, final Pet petRight) {
        View inflatedLayout = getLayoutInflater().inflate(R.layout.item_pet_encontrado, null, false);

        if(petLeft.getImagens() != null) {

            String img = petLeft.getImagens().get(0);

            ImageView mThumbnailPreview = (ImageView) inflatedLayout.findViewById(R.id.imgPetEncontrado1);
            Picasso.with(this).load(img).into(mThumbnailPreview);
        }

        TextView txtNome = (TextView) inflatedLayout.findViewById(R.id.txtNomePetEncontrado1);
        txtNome.setText(petLeft.getNome());

        TextView txtIdadeAproximada = (TextView) inflatedLayout.findViewById(R.id.txtIdadePetEncontrado1);
        txtIdadeAproximada.setText(petLeft.getIdadeAproximade().toString());

        LinearLayout llPetEncontrado = (LinearLayout) inflatedLayout.findViewById(R.id.llPetEncontradoLeft);
        llPetEncontrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), VisualizarPet.class);

                String bundleType = null;
                switch (petLeft.getEspecie()){
                    case "Cachorro":
                        bundleType = petLeft.getEspecie();
                        break;
                    case "Gato":
                        bundleType = petLeft.getEspecie();
                        break;
                    case "Hamster":
                        bundleType = petLeft.getEspecie();
                        break;
                    case "Pássaro":
                        bundleType = petLeft.getEspecie();
                        break;
                }

                Bundle bundle = new Bundle();
                bundle.putSerializable(bundleType, petLeft);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });


        LinearLayout llPetEncontradoRight = (LinearLayout) inflatedLayout.findViewById(R.id.llPetEncontradoRight);
        if(petRight != null) {

            if(petRight.getImagens() != null) {
                String img = petRight.getImagens().get(0);

                ImageView mThumbnailPreview2 = (ImageView) inflatedLayout.findViewById(R.id.imgPetEncontrado2);
                Picasso.with(this).load(img).into(mThumbnailPreview2);
            }

            TextView txtNome2 = (TextView) inflatedLayout.findViewById(R.id.txtNomePetEncontrado2);
            txtNome2.setText(petRight.getNome());

            TextView txtIdadeAproximada2 = (TextView) inflatedLayout.findViewById(R.id.txtIdadePetEncontrado2);
            txtIdadeAproximada2.setText(petRight.getIdadeAproximade().toString());



            llPetEncontradoRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), VisualizarPet.class);

                    String bundleType = null;
                    switch (petRight.getEspecie()){
                        case "Cachorro":
                            bundleType = petRight.getEspecie();
                            break;
                        case "Gato":
                            bundleType = petRight.getEspecie();
                            break;
                        case "Hamster":
                            bundleType = petRight.getEspecie();
                            break;
                        case "Pássaro":
                            bundleType = petRight.getEspecie();
                            break;
                    }

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(bundleType, petRight);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            });
        }else{
            llPetEncontradoRight.setVisibility(View.GONE);
        }

        //add item_pet_encontrado to linear layout
        linearLayout.addView(inflatedLayout);

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
