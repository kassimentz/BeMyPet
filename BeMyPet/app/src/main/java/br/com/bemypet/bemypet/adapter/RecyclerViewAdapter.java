package br.com.bemypet.bemypet.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.bemypet.bemypet.CadastroUsuario;
import br.com.bemypet.bemypet.MainActivity;
import br.com.bemypet.bemypet.R;
import br.com.bemypet.bemypet.VisualizarPoliticaAdocao;
import br.com.bemypet.bemypet.VisualizarUsuario;
import br.com.bemypet.bemypet.api.StringUtils;
import br.com.bemypet.bemypet.controller.Constants;
import br.com.bemypet.bemypet.controller.ManagerPreferences;
import br.com.bemypet.bemypet.model.Notificacao;
import br.com.bemypet.bemypet.model.Usuario;

/**
 * Created by objectedge on 8/31/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<Notificacao> list = Collections.emptyList();
    Context context;
    String cpfLogado;
    List<Usuario> usuarioList = new ArrayList<>();

    public RecyclerViewAdapter(List<Notificacao> list, Context context){
        this.list = list;
        this.context = context;

        cpfLogado = ManagerPreferences.getString(context, Constants.USUARIO_CPF);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.txtTipoNotificacao.setText(list.get(position).getStatusNotificacao());
        holder.txtDataNotificacao.setText(new SimpleDateFormat("dd/MM/yyyy").format(list.get(position).getData()));

        //list.get(position).getId();
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                verificaNotificacao(list.get(position));
            }
        });

    }


    private void verificaNotificacao(Notificacao notificacao) {
        Bundle bundle = new Bundle();
        Intent resultIntent = null;
        List<String> cpfs = new ArrayList<>();
        Usuario adotante = new Usuario();
        Usuario doador = new Usuario();

        cpfs.add(notificacao.getCpfAdotante());
        cpfs.add(notificacao.getCpfDoador());

        getUsers(cpfs);

        if(notificacao.getStatusNotificacao().equalsIgnoreCase("queroAdotar")){

            if(cpfLogado.equalsIgnoreCase(notificacao.getCpfAdotante())){
                //ao clicar mostrar Data de aprovação, nome do usuario que adotou, nome e foto do pet.
            }else{

                //clicar mostrar o perfil do Usuário adotante (mesmos passos para aprovar adoção)
                //(verificar o que precisa receber de bundle) - cpfAdotante, idPet
                bundle.putString("cpfAdotante", notificacao.getCpfAdotante());
                bundle.putString("idPet", notificacao.getIdPet());
                resultIntent = new Intent(context, VisualizarUsuario.class);
                resultIntent.putExtras(bundle);
            }
        }else if(notificacao.getStatusNotificacao().equalsIgnoreCase("adocaoAprovada")){
            //mostrando a rota para buscar o pet - verificar o que precisa passar de bundle
            if(cpfLogado.equalsIgnoreCase(notificacao.getCpfAdotante())){

                if(usuarioList.get(0) != null){adotante = usuarioList.get(0);}
                if(usuarioList.get(1) != null){ doador = usuarioList.get(1);}
                if(adotante != null && doador != null){
                    bundle.putString("origem", adotante.getEndereco().toString());
                    bundle.putString("destino", doador.getEndereco().toString());
                }

            }else{
                //ao clicar mostrar Data de aprovação, nome do usuario que adotou, nome e foto do pet.
            }
        }else if (notificacao.getStatusNotificacao().equalsIgnoreCase("adocaoReprovada")){
            //ao clicar mostrar Data de aprovação, nome do usuario que adotou, nome e foto do pet.
        }

        context.startActivity(resultIntent);

    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        if(list.size() > 0){
            return list.size();
        }else{
            return 0;
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        CardView cv;
        TextView txtTipoNotificacao;
        TextView txtDataNotificacao;
        private ItemClickListener clickListener;


        ViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            txtTipoNotificacao = (TextView) itemView.findViewById(R.id.tipoNotificacao);
            txtDataNotificacao = (TextView) itemView.findViewById(R.id.data);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Notificacao data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Notificacao data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    private void getUsers(List<String> cpfs) {

        for (String cpf : cpfs) {
            final String cpfUser = cpf;
            CadastroUsuario.dbRef.child("usuario").child(cpfUser).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            usuarioList.add(dataSnapshot.getValue(Usuario.class));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.i("onCancelled", "getUser:onCancelled", databaseError.toException());
                        }
                    });
        }

    }
}
