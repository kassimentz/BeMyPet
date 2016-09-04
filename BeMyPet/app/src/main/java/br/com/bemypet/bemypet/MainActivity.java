package br.com.bemypet.bemypet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.controller.ManagerPreferences;
import br.com.bemypet.bemypet.model.Pet;
import br.com.bemypet.bemypet.model.Usuario;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @BindView(R.id.imgBird) public ImageView imgBird;
    @BindView(R.id.imgHamster) public ImageView imgHamster;
    @BindView(R.id.imgCat) public ImageView imgCat;
    @BindView(R.id.imgDog) public ImageView imgDog;

    private Unbinder unbinder;
    final HashMap<String, Pet> pets = new HashMap<>();
    final HashMap<String, Pet> cats = new HashMap<>();
    final HashMap<String, Pet> dogs = new HashMap<>();
    final HashMap<String, Pet> birds = new HashMap<>();
    final HashMap<String, Pet> hamsters = new HashMap<>();
    List<Usuario> usuarioList = new ArrayList<>();

    Usuario user = new Usuario();

    String message, tipoNotificacao, cpf, erroAdotante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();
        cpf = ManagerPreferences.getString(this, Constants.USUARIO_CPF);
        if(!StringUtils.isNullOrEmpty(cpf)) {
            getUser(cpf);
        }

        getBundle();
        getPets();


    }

    private void getBundle() {

        if(getIntent() != null && getIntent().getExtras() != null){
            if (getIntent().getExtras().getString("tipoNotificacao") != null) {
                tipoNotificacao = getIntent().getExtras().getString("tipoNotificacao");
            }

            if (getIntent().getExtras().getString("message") != null) {
                message = getIntent().getExtras().getString("message");
            }

            if (getIntent().getExtras().getString("erroAdotante") != null) {
                erroAdotante = getIntent().getExtras().getString("erroAdotante");
            }
        }



        if(!StringUtils.isNullOrEmpty(tipoNotificacao) && (!StringUtils.isNullOrEmpty(message))){

            new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert).setTitle("Adoção Reprovada")
                    .setMessage(message).setPositiveButton("OK", null).show();
        }

        if(!StringUtils.isNullOrEmpty(erroAdotante)){

            if(erroAdotante.equalsIgnoreCase("erroAdotante")) {
                new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert).setTitle("Erro - Notificação")
                        .setMessage("Um erro ao salvar a notificao aconteceu e não será possivel exibi-la").setPositiveButton("OK", null).show();
            }
        }

    }

    private void getUser(String cpf) {

        final String cpfUser = cpf;
        CadastroUsuario.dbRef.child("usuario").child(cpfUser).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        usuarioList.add(dataSnapshot.getValue(Usuario.class));
                        getAllPets(dataSnapshot.getValue(Usuario.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("onCancelled", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    /**
     * Buscar no banco de dados
     * Para os pets que nao tiver registro no banco,
     * Desatuvar o botao correspondente
     */
    private void getPets() {

        DatabaseReference myRef = CadastroUsuario.dbRef.child("pet").getRef();
        Query query = myRef.orderByChild("id");

        query.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()){

                    Pet pet = snap.getValue(Pet.class);

                    if(pet.getAdotante() == null && (!pet.getDono().getCpf().equalsIgnoreCase(cpf))) {

                        switch (pet.getEspecie()) {
                            case "Cachorro":
                                if (!dogs.containsKey(pet.getId()))
                                    dogs.put(pet.getId(), pet);
                                break;
                            case "Gato":
                                if (!cats.containsKey(pet.getId()))
                                    cats.put(pet.getId(), pet);
                                break;
                            case "Hamster":
                                if (!hamsters.containsKey(pet.getId()))
                                    hamsters.put(pet.getId(), pet);
                                break;
                            case "Pássaro":
                                if (!birds.containsKey(pet.getId()))
                                    birds.put(pet.getId(), pet);
                                break;


                        }
                    }

                }

                if(!dogs.isEmpty()){
                    ButterKnife.apply(imgDog, ENABLE, false);
                    imgDog.setColorFilter(Color.argb(0, 200, 200, 200));
                }else{
                    ButterKnife.apply(imgDog, DISABLE);
                    imgDog.setColorFilter(Color.argb(200, 200, 200, 200));
                }

                if(!cats.isEmpty()){
                    ButterKnife.apply(imgCat, ENABLE, false);
                    imgCat.setColorFilter(Color.argb(0, 200, 200, 200));
                }else{
                    ButterKnife.apply(imgCat, DISABLE);
                    imgCat.setColorFilter(Color.argb(200, 200, 200, 200));
                }

                if(!birds.isEmpty()){
                    ButterKnife.apply(imgBird, ENABLE, false);
                    imgBird.setColorFilter(Color.argb(0, 200, 200, 200));
                }else{
                    ButterKnife.apply(imgBird, DISABLE);
                    imgBird.setColorFilter(Color.argb(200, 200, 200, 200));
                }

                if(!hamsters.isEmpty()){
                    ButterKnife.apply(imgHamster, ENABLE, false);
                    imgHamster.setColorFilter(Color.argb(0, 200, 200, 200));
                }else{
                    ButterKnife.apply(imgHamster, DISABLE);
                    imgHamster.setColorFilter(Color.argb(200, 200, 200, 200));
                }

            }
            public void onCancelled(DatabaseError databaseError) { }
        });


    }

    public void getAllPets(Usuario usuario){

        user = usuario;
        DatabaseReference myRef = CadastroUsuario.dbRef.child("pet").getRef();
        Query query = myRef.orderByChild("id");
        query.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()){

                    Pet pet = snap.getValue(Pet.class);
                    if(pet.getDono().getCpf().equalsIgnoreCase(usuarioList.get(0).getCpf())){
                        user.addPet(pet);
                    }
                }
            }
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.menu_adocoes_aprovar:
                        //Toast.makeText(getApplicationContext(),"Lista de adocoes para aprovar / notificacoes ",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), ListaNotificacoes.class);
                        startActivity(i);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_quero_doar:
                        cadastroPet();
                        break;

                    case R.id.menu_meus_pets:
                        listaPetsUsuario();
                        break;

                    case R.id.menu_meu_perfil:
                        visualizarPerfil();
                        break;

                    case R.id.menu_politica_adocao:
                        visualizarPolitica();
                        break;

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void visualizarPolitica() {
        Intent intent = new Intent(MainActivity.this, VisualizarPoliticaAdocao.class);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    private void visualizarPerfil(){
        Bundle bundle = new Bundle();
        if(!usuarioList.isEmpty()){
            bundle.putSerializable("usuario", usuarioList.get(0));
        }
        Intent intent = new Intent(getApplicationContext(), VisualizarPerfilUsuarioLogado.class);
        intent.putExtras(bundle);
        startActivity(intent);
        drawerLayout.closeDrawers();

    }

    private void listaPetsUsuario() {

        Bundle bundle = new Bundle();
        for (Pet p : user.getPets()) {
            if (!pets.containsKey(p.getId()))
                pets.put(p.getId(), p);
        }

        if(!pets.isEmpty()){
            bundle.putSerializable("allPets", new ArrayList<Pet>(pets.values()));
        }

        Intent intent = new Intent(MainActivity.this, ListaPetsUsuario.class);
        intent.putExtras(bundle);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>(){
        @Override public void apply(View view, int index){
            view.setEnabled(false);
        }
    };

    static final ButterKnife.Setter<View, Boolean> ENABLE = new ButterKnife.Setter<View, Boolean>(){
        @Override public void set(View view, Boolean value, int index){
            view.setEnabled(true);
        }
    };

    public void cadastroPet(){

        Bundle bundle = new Bundle();
        if(!usuarioList.isEmpty()){
            bundle.putSerializable("doador", usuarioList.get(0));
        }
        Intent intent = new Intent(MainActivity.this, CadastroPet.class);
        intent.putExtras(bundle);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //ação de clique parar voltar para a mainActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menuMoreOptions:
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public void viewDogs(View v) {

        if(!dogs.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("dogs", new ArrayList<Pet>(dogs.values()));
            callActivity(bundle);
        }

    }

    public void viewCats(View v){

        if(!cats.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("cats", new ArrayList<Pet>(cats.values()));
            callActivity(bundle);
        }

    }

    public void viewHamsters(View v){

        if(!hamsters.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("hamsters", new ArrayList<Pet>(hamsters.values()));
            callActivity(bundle);
        }
    }

    public void viewBirds(View v){

        if(!birds.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("birds", new ArrayList<Pet>(birds.values()));
            callActivity(bundle);
        }

    }

    private void callActivity(Bundle b){
        Intent intent = new Intent(getApplicationContext(), PetsEncontrados.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
