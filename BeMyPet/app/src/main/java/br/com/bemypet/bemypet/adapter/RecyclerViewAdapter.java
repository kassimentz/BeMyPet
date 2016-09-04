package br.com.bemypet.bemypet.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.bemypet.bemypet.R;
import br.com.bemypet.bemypet.VisualizarNotificacaoPadrao;
import br.com.bemypet.bemypet.VisualizarRotaPetActivity;
import br.com.bemypet.bemypet.VisualizarUsuario;
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
            Query ref ){
        super( modelClass, modelLayout, viewHolderClass, ref );

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
                verificaNotificacao(notificacao);
            }
        });

    }



    private void verificaNotificacao(Notificacao notificacao) {
        cpfLogado = ManagerPreferences.getString(context, Constants.USUARIO_CPF);
        Log.i("cpfLogado", "cpfLogado" + cpfLogado);
        Log.i("notificacao", "notificacao" + notificacao.toString());
        Log.i("getCpfAdotante()", "notificacao cpf adotante: "+ notificacao.getCpfAdotante());
        Log.i("getStatusNotificacao()", "notificacao status: "+notificacao.getStatusNotificacao());

        if(cpfLogado.equalsIgnoreCase(notificacao.getCpfAdotante())){
            //quem está logado é adotante
            if(notificacao.getStatusNotificacao().equalsIgnoreCase(Constants.QUERO_ADOTAR)){
                //é adotante e notificacao é "quero adotar", entao ve a notificacao padrao
                goToVisualizarNotificacaoPadrao(notificacao);
            }else if(notificacao.getStatusNotificacao().equalsIgnoreCase(Constants.ADOCAO_APROVADA)){
                //é adotante e notificacao é "adocao aprovada", entao visualiza rota para buscar pet
                goToVisualizarRotaPet(notificacao);
            }else if(notificacao.getStatusNotificacao().equalsIgnoreCase(Constants.ADOCAO_REPROVADA)){
                //é adotante e notificacao é "adocao reprovada"
                goToVisualizarNotificacaoPadrao(notificacao);
            }
        }else{
            //quem está logado é doador
            if(notificacao.getStatusNotificacao().equalsIgnoreCase(Constants.QUERO_ADOTAR)){
                //é doador e notificacao é "quero adotar", entao ve o perfil do adotante
                goToVisualizarUsuario(notificacao);
            }else if(notificacao.getStatusNotificacao().equalsIgnoreCase(Constants.ADOCAO_APROVADA)){
                //é doador e notificacao é "adocao aprovada", vai para visualizacao padrao
                goToVisualizarNotificacaoPadrao(notificacao);
            }else if(notificacao.getStatusNotificacao().equalsIgnoreCase(Constants.ADOCAO_REPROVADA)){
                //é doador e notificacao é "adocao reprovada"
                goToVisualizarNotificacaoPadrao(notificacao);
            }
        }


    }

    private void goToVisualizarUsuario(Notificacao notificacao) {
        Bundle bundle = new Bundle();
        Intent resultIntent = new Intent(context, VisualizarUsuario.class);
        bundle.putString("cpfAdotante", notificacao.getCpfAdotante());
        bundle.putString("idPet", notificacao.getIdPet());
        resultIntent.putExtras(bundle);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(resultIntent);
    }

    private void goToVisualizarRotaPet(Notificacao notificacao) {
        Bundle bundle = new Bundle();
        Intent resultIntent = new Intent(context, VisualizarRotaPetActivity.class);
        bundle.putString("origem", notificacao.getEnderecoAdotante());
        bundle.putString("destino", notificacao.getEnderecoDoador());
        resultIntent.putExtras(bundle);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(resultIntent);
    }

    private void goToVisualizarNotificacaoPadrao(Notificacao notificacao) {
        Bundle bundle = new Bundle();
        Intent resultIntent = new Intent(context, VisualizarNotificacaoPadrao.class);
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