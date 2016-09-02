package br.com.bemypet.bemypet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

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

    Usuario doador, adotante;
    String idPet, data, statusNotificacao;
    Pet pet;

    @BindView(R.id.txtDataNotificacao) public EditText txtDataNotificacao;
    @BindView(R.id.txtStatusNotificacao) public EditText txtStatusNotificacao;
    @BindView(R.id.txtDoador) public EditText txtDoador;
    @BindView(R.id.txtAdotante) public EditText txtAdotante;
    @BindView(R.id.txtPet) public EditText txtPet;
    @BindView(R.id.imgPet) public ImageView imgPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_notificacao_padrao);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        setNotificacoes();
    }

    private void setNotificacoes() {
        txtDataNotificacao.setText(getData());
        txtStatusNotificacao.setText(getStatusNotificacao());
        txtAdotante.setText(getAdotante().getNome());
        txtDoador.setText(getDoador().getNome());
        txtPet.setText(getPet().getNome());
        Picasso.with(this).load(pet.getImagens().get(0)).into(imgPet);
    }

    public void getBundle(){
        if(getIntent() != null && getIntent().getExtras() != null) {

            Bundle bundle = getIntent().getExtras();

            if (getIntent().getSerializableExtra("doador") != null) {
                setDoador((Usuario) getIntent().getSerializableExtra("doador"));
            }

            if (getIntent().getSerializableExtra("adotante") != null) {
                setAdotante((Usuario) getIntent().getSerializableExtra("adotante"));
            }

            if (getIntent().getExtras().getString("idPet") != null) {
                setIdPet(getIntent().getExtras().getString("idPet"));
                getPet(getIdPet());
            }

            if (getIntent().getExtras().getString("statusNotificacao") != null) {
                setStatusNotificacao(getIntent().getExtras().getString("statusNotificacao"));
            }

            if (getIntent().getExtras().getString("idPet") != null) {
                setData(new SimpleDateFormat("dd/MM/yyyy").format(getIntent().getExtras().getLong("idPet")));
            }
        }
    }

    private void getPet(String idPet) {

        final String id = idPet;
        CadastroUsuario.dbRef.child("pet").child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        setPet(dataSnapshot.getValue(Pet.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("onCancelled", "getUser:onCancelled", databaseError.toException());
                    }
                }
        );
    }

    public Usuario getDoador() {
        return doador;
    }

    public void setDoador(Usuario doador) {
        this.doador = doador;
    }

    public Usuario getAdotante() {
        return adotante;
    }

    public void setAdotante(Usuario adotante) {
        this.adotante = adotante;
    }

    public String getIdPet() {
        return idPet;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatusNotificacao() {
        return statusNotificacao;
    }

    public void setStatusNotificacao(String statusNotificacao) {
        this.statusNotificacao = statusNotificacao;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
