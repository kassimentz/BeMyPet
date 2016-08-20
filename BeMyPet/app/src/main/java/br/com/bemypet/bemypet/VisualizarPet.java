package br.com.bemypet.bemypet;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import br.com.bemypet.bemypet.adapter.ImageAdapter;
import br.com.bemypet.bemypet.model.Pet;

public class VisualizarPet extends AppCompatActivity {

    Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_pet);

        getBundle();

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(this, pet.getImagens());
        viewPager.setAdapter(adapter);
    }

    private void getBundle() {

        if (getIntent().getSerializableExtra("Cachorro") != null){
            pet = (Pet) getIntent().getSerializableExtra("Cachorro");
        }

        if (getIntent().getSerializableExtra("Gato") != null){
            pet = (Pet) getIntent().getSerializableExtra("Gato");
        }

        if (getIntent().getSerializableExtra("Hamster") != null){
            pet = (Pet) getIntent().getSerializableExtra("Hamster");
        }

        if (getIntent().getSerializableExtra("Pássaro") != null){
            pet = (Pet) getIntent().getSerializableExtra("Pássaro");
        }
    }

}
