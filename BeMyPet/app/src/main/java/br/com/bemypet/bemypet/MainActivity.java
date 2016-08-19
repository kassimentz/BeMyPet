package br.com.bemypet.bemypet;

import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.bemypet.bemypet.model.Pet;
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
    final List<Pet> cats = new ArrayList<>();
    final List<Pet> dogs = new ArrayList<>();
    final List<Pet> birds = new ArrayList<>();
    final List<Pet> hamsters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();

        ButterKnife.apply(imgDog, DISABLE);
        ButterKnife.apply(imgCat, DISABLE);
        ButterKnife.apply(imgBird, DISABLE);
        ButterKnife.apply(imgHamster, DISABLE);
        
        getPets();

    }

    /**
     * Buscar no banco de dados
     * Para os pets que nao tiver registro no banco,
     * Desatuvar o botao correspondente
     */
    private void getPets() {

        DatabaseReference myRef = ((BeMyPetApplication)getApplication()).dbRef.child("pet").getRef();
        Query query = myRef.orderByChild("id");
        final List<Pet> pets = new ArrayList<>();
        query.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()){

                    Pet pet = snap.getValue(Pet.class);
                    pets.add(pet);

                    switch (pet.getEspecie()){
                        case "Cachorro":
                            dogs.add(pet);
                            break;
                        case "Gato":
                            cats.add(pet);
                            break;
                        case "Hamster":
                            hamsters.add(pet);
                            break;
                        case "Pássaro":
                            birds.add(pet);
                            break;

                    }

                }


            }
            public void onCancelled(DatabaseError databaseError) { }
        });

        if(!dogs.isEmpty()){
            ButterKnife.apply(imgDog, ENABLE, false);
        }else{
            ButterKnife.apply(imgDog, DISABLE);
        }

        if(!cats.isEmpty()){
            ButterKnife.apply(imgCat, ENABLE, false);
        }else{
            ButterKnife.apply(imgCat, DISABLE);
        }

        if(!birds.isEmpty()){
            ButterKnife.apply(imgBird, ENABLE, false);
        }else{
            ButterKnife.apply(imgBird, DISABLE);
        }

        if(!hamsters.isEmpty()){
            ButterKnife.apply(imgHamster, ENABLE, false);
        }else{
            ButterKnife.apply(imgHamster, DISABLE);
        }
    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.menu_quero_adotar:
                        Toast.makeText(getApplicationContext(),"Quero Adotar",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_quero_doar:

                        Intent iNewDoador = new Intent(MainActivity.this, CadastroPet.class);
                        startActivity(iNewDoador);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_meus_pets:

                        Toast.makeText(getApplicationContext(),"Lista de Pets do Usuario",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_meu_perfil:
                        Toast.makeText(getApplicationContext(),"Meu Perfil",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_politica_adocao:
                        Toast.makeText(getApplicationContext(),"Política de Adoção",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
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

    public void cadastroPet(View v){
        Intent intent = new Intent(MainActivity.this, CadastroPet.class);
        startActivity(intent);
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
                //chamar tela opcoes
            case R.id.menuConfigSearch:
                // chamar tela de configuracao de busca
        }
        return super.onOptionsItemSelected(item);
    }

    public void viewDogs(View v) {

        if(!dogs.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("dogs", (Serializable) dogs);
            callActivity(bundle);
        }

    }

    public void viewCats(View v){

        if(!cats.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("cats", (Serializable) cats);
            callActivity(bundle);
        }

    }

    public void viewHamsters(View v){

        if(!hamsters.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("hamsters", (Serializable) hamsters);
            callActivity(bundle);
        }
    }

    public void viewBirds(View v){

        if(!birds.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("birds", (Serializable) birds);
            callActivity(bundle);
        }

    }

    private void callActivity(Bundle b){
        //trocar a classe a ser chamada
        Intent intent = new Intent(getApplicationContext(), CadastroUsuario.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}
