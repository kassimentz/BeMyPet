package br.com.bemypet.bemypet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.bemypet.bemypet.api.StringUtils;
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
    Pet pet;
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

        Boolean erro;


        if(StringUtils.isNullOrEmpty(txtNomePet.getText().toString())){
            txtNomePet.setError(getString(R.string.required_nome_pet_message));
            erro = true;
        }else{
            pet.setNome(txtNomePet.getText().toString());
            erro = false;
        }

        final Boolean cadastroAtivo = ((RadioButton)findViewById(rgOpcoesCadastroPetAtivo.getCheckedRadioButtonId())).getText().toString().equalsIgnoreCase("Sim");
        pet.setCadastroAtivo(cadastroAtivo);
        final Boolean castrado = ((RadioButton)findViewById(rgOpcoesCastrado.getCheckedRadioButtonId() )).getText().toString().equalsIgnoreCase("Sim");
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


        pet.setSaude(txtSaude.getText().toString());
        final String sexo = ((RadioButton)findViewById(rgOpcoesSexoPet.getCheckedRadioButtonId() )).getText().toString();
        pet.setSexo(sexo);

        pet.setSociavelCom(txtSociavel.getText().toString());
        pet.setTemperamento(txtTemperamento.getText().toString());
        pet.setId(String.valueOf(System.currentTimeMillis()));

        if(!erro){
            salvarPet(pet);
            this.finish();
        }

    }

    private void salvarPet(Pet pet) {
        ((BeMyPetApplication)getApplication()).dbRef.child("pet").child(pet.getId()).setValue(pet);
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
            List<Uri> imgs = new ArrayList<>();
            for (Image img : images) {
                Log.i("images", img.name);
                imgs.add(storeImageToFirebase(img));
            }

            pet.setImagens(imgs);

        }
    }

    /*private String storeImageToFirebase(Image img) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(img.path, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

        // we finally have our base64 string version of the image, save it.
        return base64Image;
    }*/
   
   private Uri storeImageToFirebase(Image img) {
       
        String fileName = img.name;
        String storageRef = firebase.storage().ref('/images/' + fileName);
       
        UploadTask uploadTask = storageRef.putBytes(img);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                return downloadUrl;
            }
        });
   }

}
