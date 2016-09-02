package br.com.bemypet.bemypet;

import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import br.com.bemypet.bemypet.adapter.ImageAdapter;
import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.model.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VisualizarPerfilUsuarioLogado extends AppCompatActivity {

    @BindView(R.id.ivUsuarioItem1) public ImageView ivUsuarioItem1;
    @BindView(R.id.ivUsuarioItem2) public ImageView ivUsuarioItem2;
    @BindView(R.id.ivUsuarioItem3) public ImageView ivUsuarioItem3;
    @BindView(R.id.ivUsuarioItem4) public ImageView ivUsuarioItem4;
    @BindView(R.id.ivUsuarioItem5) public ImageView ivUsuarioItem5;
    @BindView(R.id.ivUsuarioItem6) public ImageView ivUsuarioItem6;
    @BindView(R.id.ivUsuarioItem7) public ImageView ivUsuarioItem7;
    @BindView(R.id.txtNomeUsuarioVisualizacao) public TextView txtNomeUsuarioVisualizacao;
    @BindView(R.id.txtCpfUsuarioVisualizacao) public TextView txtCpfUsuarioVisualizacao;
    @BindView(R.id.txtEnderecoUsuarioVisualizacao) public TextView txtEnderecoUsuarioVisualizacao;
    @BindView(R.id.txtTelefoneUsuarioVisualizacao) public TextView txtTelefoneUsuarioVisualizacao;
    @BindView(R.id.txtEmailUsuarioVisualizacao) public TextView txtEmailUsuarioVisualizacao;
    @BindView(R.id.txtJaPossuiPetsUsuarioVisualizacao) public TextView txtJaPossuiPetsUsuarioVisualizacao;
    @BindView(R.id.txtTelasNasJanelasUsuarioVisualizacao) public TextView txtTelasNasJanelasUsuarioVisualizacao;
    @BindView(R.id.chkState1) public ToggleButton chkState1;
    @BindView(R.id.chkState2) public ToggleButton chkState2;
    @BindView(R.id.chkState3) public ToggleButton chkState3;
    @BindView(R.id.chkState4) public ToggleButton chkState4;
    @BindView(R.id.chkState5) public ToggleButton chkState5;
    @BindView(R.id.chkState6) public ToggleButton chkState6;
    @BindView(R.id.chkState7) public ToggleButton chkState7;

    Usuario usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_perfil_usuario_logado);

        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.visualizarPerfilUsuarioLogadoToolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        getBundle();

        verificaValoresNulos(usuarioLogado);

    }

    private void getBundle() {

        if (getIntent().getSerializableExtra("usuario") != null){
            usuarioLogado = (Usuario) getIntent().getSerializableExtra("usuario");
        }
    }

    private void verificaValoresNulos(Usuario usuario) {

        if(!usuario.getImagens().isEmpty()) {
            ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
            ImageAdapter adapter = new ImageAdapter(this, usuario.getImagens());
            viewPager.setAdapter(adapter);
        }

        if(usuario.getNome() == null || StringUtils.isNullOrEmpty(usuario.getNome())){
            txtNomeUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtNomeUsuarioVisualizacao.setText(usuario.getNome());
        }

        if(usuario.getCpf() == null || StringUtils.isNullOrEmpty(usuario.getCpf())){
            txtCpfUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtCpfUsuarioVisualizacao.setText(usuario.getCpf());
        }

        if(usuario.getEndereco() == null || StringUtils.isNullOrEmpty(usuario.getEndereco().toString())){
            txtEnderecoUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtEnderecoUsuarioVisualizacao.setText(usuario.getEndereco().toString());
        }

        if(usuario.getTelefone() == null || StringUtils.isNullOrEmpty(usuario.getTelefone())){
            txtTelefoneUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtTelefoneUsuarioVisualizacao.setText(usuario.getTelefone());
        }

        if(usuario.getEmail() == null || StringUtils.isNullOrEmpty(usuario.getEmail())){
            txtEmailUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtEmailUsuarioVisualizacao.setText(usuario.getEmail());
        }

        if(usuario.getPossuiTelaNasJanelas() == null || StringUtils.isNullOrEmpty(usuario.getPossuiTelaNasJanelas().toString())){
            txtTelasNasJanelasUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            if(usuario.getPossuiTelaNasJanelas()) {
                txtTelasNasJanelasUsuarioVisualizacao.setText("Sim");
            }else{
                txtTelasNasJanelasUsuarioVisualizacao.setText("Não");
            }
        }

        if(usuario.getJaTevePet() == null || StringUtils.isNullOrEmpty(usuario.getJaTevePet())){
            txtJaPossuiPetsUsuarioVisualizacao.setText(Constants.DADO_NAO_INFORMADO);
        }else{
            txtJaPossuiPetsUsuarioVisualizacao.setText(usuario.getJaTevePet());
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
}
