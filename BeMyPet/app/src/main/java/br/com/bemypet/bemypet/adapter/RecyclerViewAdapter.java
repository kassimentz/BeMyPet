package br.com.bemypet.bemypet.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.bemypet.bemypet.MainActivity;
import br.com.bemypet.bemypet.R;
import br.com.bemypet.bemypet.VisualizarNotificacaoPadrao;
import br.com.bemypet.bemypet.VisualizarRotaPetActivity;
import br.com.bemypet.bemypet.VisualizarUsuario;
import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.controller.ManagerPreferences;
import br.com.bemypet.bemypet.model.Notificacao;
import br.com.bemypet.bemypet.model.Usuario;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by objectedge on 8/31/16.
 */

public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<Notificacao, NotificacaoViewHolder>  {

    Context context;
    String cpfLogado;
    List<Usuario> usuarioList = new ArrayList<>();


    public RecyclerViewAdapter(
            Class<Notificacao> modelClass,
            int modelLayout,
            Class<NotificacaoViewHolder> viewHolderClass,
            Query ref, String cpf ){
        super( modelClass, modelLayout, viewHolderClass, ref );
        this.cpfLogado = cpf;

    }

    @Override
    protected void populateViewHolder(NotificacaoViewHolder notificacaoViewHolder, Notificacao notificacao, int i) {

        notificacaoViewHolder.txtTipoNotificacao.setText(notificacao.getStatusNotificacao());
        notificacaoViewHolder.txtDataNotificacao.setText(new SimpleDateFormat("dd/MM/yyyy").format(notificacao.getData()));

    }




    @Override
    public void onBindViewHolder(NotificacaoViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);

        final Notificacao notificacao = getItem(position);

        viewHolder.txtTipoNotificacao.setText(notificacao.getStatusNotificacao());
        viewHolder.txtDataNotificacao.setText(new SimpleDateFormat("dd/MM/yyyy").format(notificacao.getData()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                verificaNotificacao(view, notificacao);
            }
        });

    }



    private void verificaNotificacao(View v, Notificacao notificacao) {

        if(cpfLogado.equalsIgnoreCase(notificacao.getCpfAdotante())){
            //quem está logado é adotante
            if(notificacao.getStatusNotificacao().equalsIgnoreCase(Constants.QUERO_ADOTAR)){
                //é adotante e notificacao é "quero adotar", entao ve a notificacao padrao
                goToVisualizarNotificacaoPadrao(v, notificacao);
            }else if(notificacao.getStatusNotificacao().equalsIgnoreCase(Constants.ADOCAO_APROVADA)){
                //é adotante e notificacao é "adocao aprovada", entao visualiza rota para buscar pet
                goToVisualizarRotaPet(v, notificacao);
            }else if(notificacao.getStatusNotificacao().equalsIgnoreCase(Constants.ADOCAO_REPROVADA)){
                //é adotante e notificacao é "adocao reprovada"
                goToVisualizarNotificacaoPadrao(v, notificacao);
            }
        }else{
            //quem está logado é doador
            if(notificacao.getStatusNotificacao().equalsIgnoreCase(Constants.QUERO_ADOTAR)){
                //é doador e notificacao é "quero adotar", entao ve o perfil do adotante
                goToVisualizarUsuario(v, notificacao);
            }else if(notificacao.getStatusNotificacao().equalsIgnoreCase(Constants.ADOCAO_APROVADA)){
                //é doador e notificacao é "adocao aprovada", vai para visualizacao padrao
                goToVisualizarNotificacaoPadrao(v, notificacao);
            }else if(notificacao.getStatusNotificacao().equalsIgnoreCase(Constants.ADOCAO_REPROVADA)){
                //é doador e notificacao é "adocao reprovada"
                goToVisualizarNotificacaoPadrao(v, notificacao);
            }
        }


    }

    private void goToVisualizarUsuario(View v, Notificacao notificacao) {
        String cpfAdotante = null;
        if(notificacao.getCpfAdotante() != null){
            cpfAdotante = notificacao.getCpfAdotante();
            Bundle bundle = new Bundle();
            Intent resultIntent = new Intent(v.getContext(), VisualizarUsuario.class);
            bundle.putString("cpfAdotante", cpfAdotante);
            bundle.putString("idPet", notificacao.getIdPet());
            resultIntent.putExtras(bundle);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(resultIntent);
        }else{

            Intent resultIntent = new Intent(v.getContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("erroAdotante", "erroAdotante");
            resultIntent.putExtras(bundle);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(resultIntent);

        }

    }

    private void goToVisualizarRotaPet(View v, Notificacao notificacao) {
        Bundle bundle = new Bundle();
        Intent resultIntent = new Intent(v.getContext(), VisualizarRotaPetActivity.class);
        bundle.putString("origem", notificacao.getEnderecoAdotante());
        bundle.putString("destino", notificacao.getEnderecoDoador());
        resultIntent.putExtras(bundle);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(resultIntent);
    }

    private void goToVisualizarNotificacaoPadrao(View v, Notificacao notificacao) {
        Bundle bundle = new Bundle();
        Intent resultIntent = new Intent(v.getContext(), VisualizarNotificacaoPadrao.class);
        bundle.putLong("data", notificacao.getData());
        bundle.putString("adotante", notificacao.getNomeAdotante());
        bundle.putString("doador", notificacao.getNomeDoador());
        bundle.putString("pet", notificacao.getNomePet());
        bundle.putString("statusNotificacao", notificacao.getStatusNotificacao());
        bundle.putString("petImg", notificacao.getImage());
        resultIntent.putExtras(bundle);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(resultIntent);
    }



}