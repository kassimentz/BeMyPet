package br.com.bemypet.bemypet;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;
import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.model.Pet;
import br.com.bemypet.bemypet.model.Usuario;
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
    @BindView(R.id.btnAddImage) public Button btnAddImage;

    Pet pet;
    Usuario doador;

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
        pet = new Pet();

        spinEspecie = (Spinner) findViewById(R.id.spinEspecie);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.especie_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinEspecie.setAdapter(adapter);

        getBundle();


    }

    private void getBundle() {

        if (getIntent().getSerializableExtra("doador") != null){
            doador = (Usuario) getIntent().getSerializableExtra("doador");
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
            case R.id.menuSave:
                cadastrarPet();

        }
        return super.onOptionsItemSelected(item);
    }


    private void cadastrarPet() {

        Boolean erro, erroImagem;

        if(StringUtils.isNullOrEmpty(txtNomePet.getText().toString())){
            txtNomePet.setError(getString(R.string.required_nome_pet_message));
            erro = true;
        }else{
            pet.setNome(txtNomePet.getText().toString());
            erro = false;
        }

        final Boolean cadastroAtivo = ((RadioButton)findViewById(rgOpcoesCadastroPetAtivo.getCheckedRadioButtonId())).getText().toString().equalsIgnoreCase("Sim");
        pet.setCadastroAtivo(cadastroAtivo);

        final String castrado = ((RadioButton) findViewById(rgOpcoesCastrado.getCheckedRadioButtonId())).getText().toString();
        pet.setCastrado(castrado);

        pet.setEspecie(spinEspecie.getSelectedItem().toString());
        pet.setHistorico(txtHistorico.getText().toString());

        if(StringUtils.isNullOrEmpty(txtIdadeAproximada.getText().toString())){
            txtIdadeAproximada.setError(getString(R.string.required_idade_pet_message));
            erro = true;
        }else{
            pet.setIdadeAproximade(Integer.valueOf(txtIdadeAproximada.getText().toString()));
            erro = false;
        }

        if(StringUtils.isNullOrEmpty(txtPesoAproximado.getText().toString())){
            txtPesoAproximado.setError(getString(R.string.required_peso_pet_message));
            erro = true;
        }else{
            pet.setPesoAproximado(Double.valueOf(txtPesoAproximado.getText().toString()));
            erro = false;
        }

        if(StringUtils.isNullOrEmpty(txtRaca.getText().toString())){
            txtRaca.setError(getString(R.string.required_raca_pet_message));
            erro = true;
        }else{
            pet.setRaca(txtRaca.getText().toString());
            erro = false;
        }

        if(pet.getImagens().isEmpty()){
            //deixar o botao de salvar ativo só quando tiver as imagens carregadas
            btnAddImage.setError(getString(R.string.required_imagens_pet));
            erroImagem = true;

        }else{
            erroImagem = false;
        }


        pet.setSaude(txtSaude.getText().toString());
        final String sexo = ((RadioButton)findViewById(rgOpcoesSexoPet.getCheckedRadioButtonId() )).getText().toString();
        pet.setSexo(sexo);

        pet.setSociavelCom(txtSociavel.getText().toString());
        pet.setTemperamento(txtTemperamento.getText().toString());
        pet.setId(String.valueOf(System.currentTimeMillis()));

        pet.setStatusAdocao(br.com.bemypet.bemypet.controller.Constants.DISPONIVEL);
        pet.setDono(doador);

        pet.setDoador(doador);

        if(!erro && !erroImagem){
            salvarPet(pet);
            this.finish();
        }

    }



    private void salvarPet(Pet pet) {

        final Pet p = pet;

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    CadastroUsuario.dbRef.child("pet").child(p.getId()).setValue(p);
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao salvar", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.i("Cancel", "Listener was cancelled");
            }
        });
    }


    public void escolherImagens(View v){
        // For multiple images
        int numberOfImagesToSelect = 10;
        Intent intent = new Intent(this, AlbumSelectActivity.class);
        //set limit on number of images that can be selected, default is 10
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, numberOfImagesToSelect);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            List<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            for (Image img : images) {
                Log.i("images", img.name);

                storeImageToFirebase(img);
            }
        }
    }


   private void storeImageToFirebase(Image img) {

       Uri file = Uri.fromFile(new File(img.path));
       StorageReference imgRef = CadastroUsuario.stRef.child("images/"+String.valueOf(System.currentTimeMillis()+file.getLastPathSegment()));
       UploadTask uploadTask = imgRef.putFile(file);

       // Register observers to listen for when the download is done or if it fails
       uploadTask.addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception exception) {
               Toast.makeText(getApplicationContext(), "Falha ao fazer upload de imagem", Toast.LENGTH_LONG);
           }
       }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
               Uri downloadUrl = taskSnapshot.getDownloadUrl();
               Log.i("url", downloadUrl.toString());
               pet.addImage(downloadUrl.toString());
           }
       });


   }

}
