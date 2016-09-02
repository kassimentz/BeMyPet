package br.com.bemypet.bemypet.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.bemypet.bemypet.R;

/**
 * Created by objectedge on 8/31/16.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView txtAdotante;
    TextView txtDoador;
    ImageView imageView;

    ViewHolder(View itemView){
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        txtAdotante = (TextView) itemView.findViewById(R.id.title);
        txtDoador = (TextView) itemView.findViewById(R.id.description);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
