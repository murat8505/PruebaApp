package com.vaw.miguel.notas;

/**
 * Created by Miguel on 18/06/2015.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorTitulares
        extends RecyclerView.Adapter<AdaptadorTitulares.TitularesViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Titular> datos;

    public static class TitularesViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private TextView txtCodigo;
        private TextView txtCorte1;
        private TextView txtCorte2;
        private TextView txtCorte3;
        private TextView txtNotaF;

        public TitularesViewHolder(View itemView) {
            super(itemView);

            txtTitulo = (TextView)itemView.findViewById(R.id.LblTitulo);
            txtCodigo = (TextView)itemView.findViewById(R.id.LblCod);
            txtCorte1 = (TextView)itemView.findViewById(R.id.LblCorte1);
            txtCorte2 = (TextView)itemView.findViewById(R.id.LblCorte2);
            txtCorte3 = (TextView)itemView.findViewById(R.id.LblCorte3);
            txtNotaF = (TextView)itemView.findViewById(R.id.LblNotaF);

        }

        public void bindTitular(Titular t) {
            txtTitulo.setText(t.getTitulo());
            txtCodigo.setText(t.getCodigo());
            txtCorte1.setText(t.getCorte1());
            txtCorte2.setText(t.getCorte2());
            txtCorte3.setText(t.getCorte3());
            txtNotaF.setText(t.getNotaF());
        }
    }

    public AdaptadorTitulares(ArrayList<Titular> datos) {
        this.datos = datos;
    }

    @Override
    public TitularesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listitem_titular, viewGroup, false);

        itemView.setOnClickListener(this);
        //android:background="?android:attr/selectableItemBackground"

        TitularesViewHolder tvh = new TitularesViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(TitularesViewHolder viewHolder, int pos) {
        Titular item = datos.get(pos);

        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}