package br.com.bemypet.bemypet;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.bemypet.bemypet.model.Usuario;

public class TermosAdocao extends AppCompatActivity {

    Usuario adotante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos_adocao);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.termosAdocaoToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        getBundle();
    }


    private void getBundle() {

        if (getIntent().getSerializableExtra("adotante") != null){
            adotante = (Usuario) getIntent().getSerializableExtra("doador");
        }
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_prosseguir, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menuForward:
                //adicionar o adotante no bundle somente se ele clicar em aceito
                //se o aceito tiver clicado, envia uma solicitacao de adocao para o dono do pet
                // envia notificacao firebase xmpp

        }
        return super.onOptionsItemSelected(item);
    }
}
