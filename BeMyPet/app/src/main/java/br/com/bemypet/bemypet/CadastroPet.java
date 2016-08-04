package br.com.bemypet.bemypet;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import br.com.bemypet.bemypet.enums.Especie;

public class CadastroPet extends AppCompatActivity {

    Spinner spinEspecie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pet);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.cadastroPetToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);



        spinEspecie = (Spinner) findViewById(R.id.spinEspecie);
        spinEspecie.setAdapter(new ArrayAdapter<Especie>(this, android.R.layout.select_dialog_singlechoice, Especie.values()));

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
            case R.id.menuSave:
                //salvar pet

        }
        return super.onOptionsItemSelected(item);
    }



}
