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
        cpfLogado = ManagerPreferences.getString(context, Constants.USUARIO_CPF);
    }

    @Override
    protected void populateViewHolder(NotificacaoViewHolder notificacaoViewHolder, Notificacao notificacao, int i) {

        notificacaoViewHolder.txtTipoNotificacao.setText(notificacao.getStatusNotificacao());
        notificacaoViewHolder.txtDataNotificacao.setText(new SimpleDateFormat("dd/MM/yyyy").format(notificacao.getData()));

    }



    @Override
    public void onBindViewHolder(NotificacaoViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);

        Notificacao notificacao = getItem(position);

        viewHolder.txtTipoNotificacao.setText(notificacao.getStatusNotificacao());
        viewHolder.txtDataNotificacao.setText(new SimpleDateFormat("dd/MM/yyyy").format(notificacao.getData()));

    }

    //    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        //Inflate the layout, initialize the View Holder
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
//        ViewHolder holder = new ViewHolder(v);
//        return holder;
//    }
//
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//
//        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
//        holder.txtTipoNotificacao.setText(list.get(position).getStatusNotificacao());
//        holder.txtDataNotificacao.setText(new SimpleDateFormat("dd/MM/yyyy").format(list.get(position).getData()));
//
//        //list.get(position).getId();
//        holder.setClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                verificaNotificacao(list.get(position));
//            }
//        });
//
//    }


    private void verificaNotificacao(Notificacao notificacao) {

        Log.i("cpfLogado", cpfLogado);
        Log.i("notificacao", notificacao.toString());
        Log.i("getCpfAdotante()", "notificacao: "+ notificacao.getCpfAdotante());
        Log.i("getStatusNotificacao()", "notificacao: "+notificacao.getStatusNotificacao());

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