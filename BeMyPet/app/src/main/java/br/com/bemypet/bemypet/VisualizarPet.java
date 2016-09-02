package br.com.bemypet.bemypet;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

import br.com.bemypet.bemypet.adapter.ImageAdapter;
import br.com.bemypet.bemypet.model.Pet;

public class VisualizarPet extends AppCompatActivity {

    Pet pet;

    @BindView(R.id.ivPetItem1) public ImageView ivPetItem1;
    @BindView(R.id.ivPetItem2) public ImageView ivPetItem2;
    @BindView(R.id.ivPetItem3) public ImageView ivPetItem3;
    @BindView(R.id.ivPetItem4) public ImageView ivPetItem4;
    @BindView(R.id.ivPetItem5) public ImageView ivPetItem5;
    @BindView(R.id.ivPetItem6) public ImageView ivPetItem6;
    @BindView(R.id.ivPetItem7) public ImageView ivPetItem7;
    @BindView(R.id.ivPetItem8) public ImageView ivPetItem8;
    @BindView(R.id.ivPetItem9) public ImageView ivPetItem9;
    @BindView(R.id.ivPetItem10) public ImageView ivPetItem10;
    @BindView(R.id.ivPetItem11) public ImageView ivPetItem11;
    @BindView(R.id.ivPetItem12) public ImageView ivPetItem12;
    @BindView(R.id.txtNomePetVisualizacao) public TextView txtNomePetVisualizacao;
    @BindView(R.id.txtEspeciePetVisualizacao) public TextView txtEspeciePetVisualizacao;
    @BindView(R.id.txtIdadeAproximadaPetVisualizacao) public TextView txtIdadeAproximadaPetVisualizacao;
    @BindView(R.id.txtPesoAproximadoPetVisualizacao) public TextView txtPesoAproximadoPetVisualizacao;
    @BindView(R.id.txtRacaPetVisualizacao) public TextView txtRacaPetVisualizacao;
    @BindView(R.id.txtSexoPetVisualizacao) public TextView txtSexoPetVisualizacao;
    @BindView(R.id.txtSaudePetVisualizacao) public TextView txtSaudePetVisualizacao;
    @BindView(R.id.txtCastradoPetVisualizacao) public TextView txtCastradoPetVisualizacao;
    @BindView(R.id.txtTemperamentoPetVisualizacao) public TextView txtTemperamentoPetVisualizacao;
    @BindView(R.id.txtSociavelPetVisualizacao) public TextView txtSociavelPetVisualizacao;
    @BindView(R.id.txtHistoricoPetVisualizacao) public TextView txtHistoricoPetVisualizacao;
    @BindView(R.id.txtCadastroPetVisualizacao) public TextView txtCadastroPetVisualizacao;
    @BindView(R.id.chkState1) public ToggleButton chkState1;
    @BindView(R.id.chkState2) public ToggleButton chkState2;
    @BindView(R.id.chkState3) public ToggleButton chkState3;
    @BindView(R.id.chkState4) public ToggleButton chkState4;
    @BindView(R.id.chkState5) public ToggleButton chkState5;
    @BindView(R.id.chkState6) public ToggleButton chkState6;
    @BindView(R.id.chkState7) public ToggleButton chkState7;
    @BindView(R.id.chkState8) public ToggleButton chkState8;
    @BindView(R.id.chkState9) public ToggleButton chkState9;
    @BindView(R.id.chkState10) public ToggleButton chkState10;
    @BindView(R.id.chkState11) public ToggleButton chkState11;
    @BindView(R.id.chkState12) public ToggleButton chkState12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_pet);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.visualizarPetToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        getBundle();
        setPet();

    }

    private void setPet() {
        if(!pet.getImagens().isEmpty()) {
            ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
            ImageAdapter adapter = new ImageAdapter(this, pet.getImagens());
            viewPager.setAdapter(adapter);
        }

        if(pet.getNome() == null || StringUtils.isNullOrEmpty(pet.getNome())){
            txtNomePetVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtNomePetVisualizacao.setText(pet.getNome());
        }

        if(StringUtils.isNullOrEmpty(pet.getEspecie())){
            txtEspeciePetVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtEspeciePetVisualizacao.setText(pet.getEspecie());
        }

        if(pet.getIdadeAproximade() == null || StringUtils.isNullOrEmpty(pet.getIdadeAproximade().toString())){
            txtIdadeAproximadaPetVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtIdadeAproximadaPetVisualizacao.setText(pet.getIdadeAproximade().toString());
        }

        if(pet.getPesoAproximado() == null || StringUtils.isNullOrEmpty(pet.getPesoAproximado().toString())){
            txtPesoAproximadoPetVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else {
            txtPesoAproximadoPetVisualizacao.setText(pet.getPesoAproximado().toString());
        }


        if(pet.getRaca() == null || StringUtils.isNullOrEmpty(pet.getRaca())){
            txtRacaPetVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtRacaPetVisualizacao.setText(pet.getRaca());
        }

        if(pet.getSexo() == null || StringUtils.isNullOrEmpty(pet.getSexo())){
            txtSexoPetVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else {
            txtSexoPetVisualizacao.setText(pet.getSexo());
        }

        if(pet.getSaude() == null || StringUtils.isNullOrEmpty(pet.getSaude())){
            txtSaudePetVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtSaudePetVisualizacao.setText(pet.getSaude());
        }

        if(pet.getCadastroAtivo() != null || StringUtils.isNullOrEmpty(pet.getCadastroAtivo().toString())){
            txtCadastroPetVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtCadastroPetVisualizacao.setText(StringUtils.booleanToString(pet.getCadastroAtivo().toString()));
        }

        if(pet.getCastrado() != null || StringUtils.isNullOrEmpty(pet.getCastrado().toString())){
            txtCastradoPetVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtCastradoPetVisualizacao.setText(StringUtils.booleanToString(pet.getCastrado().toString()));
        }

        if(pet.getTemperamento() != null || StringUtils.isNullOrEmpty(pet.getTemperamento())){
            txtTemperamentoPetVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else {
            txtTemperamentoPetVisualizacao.setText(pet.getTemperamento());
        }

        if(pet.getSociavelCom() != null || StringUtils.isNullOrEmpty(pet.getSociavelCom())){
            txtSociavelPetVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else {
            txtSociavelPetVisualizacao.setText(pet.getSociavelCom());
        }

        if(pet.getHistorico() != null || StringUtils.isNullOrEmpty(pet.getHistorico())){
            txtHistoricoPetVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtHistoricoPetVisualizacao.setText(pet.getHistorico());
        }

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void queroAdotar(View v){
        Bundle bundle = new Bundle();
        bundle.putSerializable("pet", pet);
        Intent intent = new Intent(VisualizarPet.this, VerificacaoAdocao.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}
