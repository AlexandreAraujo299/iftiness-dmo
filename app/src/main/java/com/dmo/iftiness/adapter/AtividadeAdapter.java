package com.dmo.iftiness.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dmo.iftiness.AtividadeDetalheActivity;
import com.dmo.iftiness.R;
import com.dmo.iftiness.model.Atividade;

import java.util.ArrayList;
import java.util.List;

public class AtividadeAdapter extends RecyclerView.Adapter<AtividadeAdapter.ViewHolder> {
    private List<Atividade> atividades;
    private Context context;

    public AtividadeAdapter(Context context) {
        this.atividades = new ArrayList<>();
        this.context = context;
    }

    public void setProdutos(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_atividade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Atividade atividade = atividades.get(position);
        if (atividade.getCategoria().equals("Corrida")){
            holder.icon.setImageResource(R.mipmap.ic_corrida_round);
        }
        if (atividade.getCategoria().equals("Caminhada")){
            holder.icon.setImageResource(R.mipmap.ic_caminhada_round);
        }
        if (atividade.getCategoria().equals("Natação")){
            holder.icon.setImageResource(R.mipmap.ic_natacao_round);
        }
        if (atividade.getCategoria().equals("Ciclismo")){
            holder.icon.setImageResource(R.mipmap.ic_ciclismo_round);
        }

        holder.titulo.setText(atividade.getCategoria());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AtividadeDetalheActivity.class);
                intent.putExtra("ATIVIDADE", atividade);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return atividades.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView titulo;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imgItemProduto);
            titulo = itemView.findViewById(R.id.txtItemProdutoTitulo);
            cardView = itemView.findViewById(R.id.cardItemProduto);
        }
    }
}
