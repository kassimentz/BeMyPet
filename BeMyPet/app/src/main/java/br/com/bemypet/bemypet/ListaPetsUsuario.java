package br.com.bemypet.bemypet;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.controller.ManagerPreferences;
import br.com.bemypet.bemypet.model.Pet;
import br.com.bemypet.bemypet.model.Usuario;
import butterknife.ButterKnife;

public class ListaPetsUsuario extends AppCompatActivity {

    List<Pet> pets = new ArrayList<>();
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pets_usuario);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.listaPetsUsuarioToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutPetsUsuario);

        getBundle();
        createThumbs();
        Log.i("array pets", pets.toString());


    }

    private void getBundle() {

        if (getIntent().getSerializableExtra("allPets") != null){
            pets = (List<Pet>) getIntent().getSerializableExtra("allPets");
        }else{
            Log.i("get bundle", "null");
        }

    }

    //transformar todos as listas de pets em umas só
    private void createThumbs() {
        Log.i("createThumbs", pets.toString());
        if(!pets.isEmpty()){
            for (int i = 0; i < pets.size(); i+=2){
                createImage(linearLayout, pets.get(i), i + 1 == pets.size() ? null : pets.get(i + 1));
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
}
