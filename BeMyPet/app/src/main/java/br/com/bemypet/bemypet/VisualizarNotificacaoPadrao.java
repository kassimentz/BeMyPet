package br.com.bemypet.bemypet;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import br.com.bemypet.bemypet.model.Pet;
import br.com.bemypet.bemypet.model.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VisualizarNotificacaoPadrao extends AppCompatActivity {

    String data, adotante, doador, pet, statusNotificacao, petImg;

    @BindView(R.id.txtDataNotificacao) public TextView txtDataNotificacao;
    @BindView(R.id.txtStatusNotificacao) public TextView txtStatusNotificacao;
    @BindView(R.id.txtDoador) public TextView txtDoador;
    @BindView(R.id.txtAdotante) public TextView txtAdotante;
    @BindView(R.id.txtPet) public TextView txtPet;
    @BindView(R.id.imgPet) public ImageView imgPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_notificacao_padrao);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.notificacoesPadraoToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        getBundle();
        setNotificacoes();
    }

    private void setNotificacoes() {
        txtDataNotificacao.setText(getData());
        txtStatusNotificacao.setText(getStatusNotificacao());
        txtAdotante.setText(getAdotante());
        txtDoador.setText(getDoador());
        txtPet.setText(getPet());
        Picasso.with(this).load(getPetImg()).into(imgPet);
    }

    public void getBundle(){
        if(getIntent() != null && getIntent().getExtras() != null) {

            Bundle bundle = getIntent().getExtras();

            if (getIntent().getExtras().getLong("data") != 0 ) {
                setData(new SimpleDateFormat("dd/MM/yyyy").format(getIntent().getExtras().getLong("data")));
            }

            if (getIntent().getExtras().getString("adotante") != null) {
                setAdotante(getIntent().getExtras().getString("adotante"));
            }

            if (getIntent().getExtras().getString("doador") != null) {
                setDoador(getIntent().getExtras().getString("doador"));
            }

            if (getIntent().getExtras().getString("pet") != null) {
                setPet(getIntent().getExtras().getString("pet"));
            }

            if (getIntent().getExtras().getString("statusNotificacao") != null) {
                setStatusNotificacao(getIntent().getExtras().getString("statusNotificacao"));
            }

            if (getIntent().getExtras().getString("petImg") != null) {
                setPetImg(getIntent().getExtras().getString("petImg"));
            }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAdotante() {
        return adotante;
    }

    public void setAdotante(String adotante) {
        this.adotante = adotante;
    }

    public String getDoador() {
        return doador;
    }

    public void setDoador(String doador) {
        this.doador = doador;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getStatusNotificacao() {
        return statusNotificacao;
    }

    public void setStatusNotificacao(String statusNotificacao) {
        this.statusNotificacao = statusNotificacao;
    }

    public String getPetImg() {
        return petImg;
    }

    public void setPetImg(String petImg) {
        this.petImg = petImg;
    }
}
