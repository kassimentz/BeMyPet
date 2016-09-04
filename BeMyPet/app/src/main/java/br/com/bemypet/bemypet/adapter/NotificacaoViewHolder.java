package br.com.bemypet.bemypet.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.bemypet.bemypet.R;

/**
 * Created by kassi on 03/09/16.
 */
public class NotificacaoViewHolder extends RecyclerView.ViewHolder{

    CardView cv;
    TextView txtTipoNotificacao;
    TextView txtDataNotificacao;

    public NotificacaoViewHolder(View itemView) {
        super(itemView);

        cv = (CardView) itemView.findViewById(R.id.cardView);
        txtTipoNotificacao = (TextView) itemView.findViewById(R.id.tipoNotificacao);
        txtDataNotificacao = (TextView) itemView.findViewById(R.id.data);
    }
}
