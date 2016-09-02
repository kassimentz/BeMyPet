package br.com.bemypet.bemypet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import br.com.bemypet.bemypet.R;
import br.com.bemypet.bemypet.model.Notificacao;

/**
 * Created by objectedge on 8/31/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    List<Notificacao> list = Collections.emptyList();
    Context context;

    public RecyclerViewAdapter(List<Notificacao> list, Context context){
        this.list = list;
        this.context = context;
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
        holder.txtAdotante.setText(list.get(position).getCpfAdotante());
        holder.txtDoador.setText(list.get(position).getCpfDoador());
        Picasso.with(context).load(list.get(position).getImage().charAt(0)).into(holder.imageView);


        //animate(holder);
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

}
