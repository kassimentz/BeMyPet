package br.com.bemypet.bemypet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import br.com.bemypet.bemypet.enums.Especie;

public class CadastroPet extends AppCompatActivity {

    Spinner spinEspecie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pet);

        spinEspecie = (Spinner) findViewById(R.id.spinEspecie);
        spinEspecie.setAdapter(new ArrayAdapter<Especie>(this, android.R.layout.select_dialog_singlechoice, Especie.values()));

    }


}
