package br.com.bemypet.bemypet;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import br.com.bemypet.bemypet.api.StringUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FiltrarPet extends AppCompatActivity {

    Spinner spinEspecieFiltro;

    @BindView(R.id.txtFiltroRaioEmKm) public EditText txtFiltroRaioEmKm;
    @BindView(R.id.txtFiltroCidade) public EditText txtFiltroCidade;
    @BindView(R.id.txtFiltroEstado) public EditText txtFiltroEstado;
    @BindView(R.id.txtFiltroPais) public EditText txtFiltroPais;
    @BindView(R.id.txtFiltroIdade) public EditText txtFiltroIdade;
    @BindView(R.id.rgOpcoesSexoPetFiltro) public RadioGroup rgOpcoesSexoPetFiltro;
    @BindView(R.id.rgOpcoesCastradoFiltro) public RadioGroup rgOpcoesCastradoFiltro;
    @BindView(R.id.txtFiltroRaca) public EditText txtFiltroRaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_pet);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.filtrarPetToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        spinEspecieFiltro = (Spinner) findViewById(R.id.spinEspecieFiltro);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.especie_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinEspecieFiltro.setAdapter(adapter);
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filtro, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menuFiltro:
                filtrarPet();

        }
        return super.onOptionsItemSelected(item);
    }

    //TODO fazer o filtro do pet conforme os filtros selecionados
    private void filtrarPet() {

        Double filtroRaio = null;
        String filtroCidade = null;
        String filtroEstado = null;
        String filtroPais = null;
        Integer filtroIdade = null;
        String filtroSexo = null;
        String filtroCastrado = null;
        String filtroRaca = null;

        if(!StringUtils.isNullOrEmpty(txtFiltroRaioEmKm.getText().toString())){
            filtroRaio = Double.valueOf(txtFiltroRaioEmKm.getText().toString());
        }

        if(!StringUtils.isNullOrEmpty(txtFiltroCidade.getText().toString())){
            filtroCidade = txtFiltroCidade.getText().toString();
        }

        if(!StringUtils.isNullOrEmpty(txtFiltroEstado.getText().toString())){
            filtroEstado = txtFiltroEstado.getText().toString();
        }

        if(!StringUtils.isNullOrEmpty(txtFiltroPais.getText().toString())){
            filtroPais = txtFiltroPais.getText().toString();
        }

        if(!StringUtils.isNullOrEmpty(txtFiltroIdade.getText().toString())){
            filtroIdade = Integer.getInteger(txtFiltroIdade.getText().toString());
        }


        filtroSexo = ((RadioButton)findViewById(rgOpcoesSexoPetFiltro.getCheckedRadioButtonId() )).getText().toString();

        filtroCastrado = ((RadioButton)findViewById(rgOpcoesCastradoFiltro.getCheckedRadioButtonId())).getText().toString();


        if(!StringUtils.isNullOrEmpty(txtFiltroRaca.getText().toString())){
            filtroRaca = txtFiltroRaca.getText().toString();
        }




    }
}
