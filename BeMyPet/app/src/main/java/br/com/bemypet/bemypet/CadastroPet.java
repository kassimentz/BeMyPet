package br.com.bemypet.bemypet;

import android.support.v4.app.NavUtils;
import android.support.v4.util.TimeUtils;
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
import android.widget.TextView;


import java.util.Timer;

import br.com.bemypet.bemypet.model.Pet;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroPet extends AppCompatActivity {

    //tenho que ter um bundle que passe o usuario que esta cadastrando o pet

    Spinner spinEspecie;

    @BindView(R.id.txtNomePet) public EditText txtNomePet;
    @BindView(R.id.txtIdadeAproximada) public EditText txtIdadeAproximada;
    @BindView(R.id.txtPesoAproximado) public EditText txtPesoAproximado;
    @BindView(R.id.rgOpcoesSexoPet) public RadioGroup rgOpcoesSexoPet;
    @BindView(R.id.txtSaude) public TextView txtSaude;
    @BindView(R.id.rgOpcoesCastrado) public RadioGroup rgOpcoesCastrado;
    @BindView(R.id.txtTemperamento) public TextView txtTemperamento;
    @BindView(R.id.txtSociavel) public TextView txtSociavel;
    @BindView(R.id.txtHistorico) public TextView txtHistorico;
    @BindView(R.id.txtRaca) public TextView txtRaca;
    @BindView(R.id.rgOpcoesCadastroPetAtivo) public RadioGroup rgOpcoesCadastroPetAtivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pet);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.cadastroPetToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        spinEspecie = (Spinner) findViewById(R.id.spinEspecie);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.especie_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinEspecie.setAdapter(adapter);


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
                cadastrarPet();

        }
        return super.onOptionsItemSelected(item);
    }

    private void cadastrarPet() {

        Pet pet = new Pet();
        pet.setNome(txtNomePet.getText().toString());
        final Boolean cadastroAtivo = ((RadioButton)findViewById(rgOpcoesCadastroPetAtivo.getCheckedRadioButtonId() )).getText().toString().equalsIgnoreCase("Sim");
        pet.setCadastroAtivo(cadastroAtivo);
        final Boolean castrado = ((RadioButton)findViewById(rgOpcoesCastrado.getCheckedRadioButtonId() )).getText().toString().equalsIgnoreCase("Sim");
        pet.setCastrado(castrado);
        pet.setEspecie(spinEspecie.getSelectedItem().toString());
        pet.setHistorico(txtHistorico.getText().toString());
        pet.setIdadeAproximade(Integer.valueOf(txtIdadeAproximada.getText().toString()));
        pet.setPesoAproximado(Double.valueOf(txtPesoAproximado.getText().toString()));
        pet.setRaca(txtRaca.getText().toString());
        pet.setSaude(txtSaude.getText().toString());
        final String sexo = ((RadioButton)findViewById(rgOpcoesSexoPet.getCheckedRadioButtonId() )).getText().toString();
        pet.setSexo(sexo);
        pet.setSociavelCom(txtSociavel.getText().toString());
        pet.setTemperamento(txtTemperamento.getText().toString());
        pet.setId(String.valueOf(System.currentTimeMillis()));
        salvarPet(pet);
        finish();

    }

    private void salvarPet(Pet pet) {
        ((BeMyPetApplication)getApplication()).dbRef.child("pet").child(pet.getId()).setValue(pet);
    }


}
