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
import com.dmo.iftiness.model.Estatistica;

import java.util.ArrayList;
import java.util.List;

public class EstatisticaAdapter extends RecyclerView.Adapter<EstatisticaAdapter.ViewHolder> {
    private List<Estatistica> estatisticas;
    private Context context;

    public EstatisticaAdapter(Context context) {
        this.estatisticas = new ArrayList<>();
        this.context = context;
    }

    public void setEstatisticas(List<Estatistica> estatisticas) {
        this.estatisticas = estatisticas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.estatistica_atividade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Estatistica estatistica = estatisticas.get(position);

        if (estatistica.getAtividade().equals("Corrida")){
            holder.icon.setImageResource(R.mipmap.ic_corrida_round);
        }
        if (estatistica.getAtividade().equals("Caminhada")){
            holder.icon.setImageResource(R.mipmap.ic_caminhada_round);
        }
        if (estatistica.getAtividade().equals("Natação")){
            holder.icon.setImageResource(R.mipmap.ic_natacao_round);
        }
        if (estatistica.getAtividade().equals("Ciclismo")){
            holder.icon.setImageResource(R.mipmap.ic_ciclismo_round);
        }

        holder.titulo.setText(estatistica.getAtividade());
        holder.txtDistanciaTotal.setText("Distância total: " + String.valueOf(estatistica.getDistanciaTotal()) + " Km");
        holder.txtTempoTotal.setText("Tempo total: " + String.valueOf(estatistica.getDuracaoTotal()) + " minutos");
        holder.txtCalorias.setText("Calorias: " +  String.valueOf(estatistica.getTotalCalorias()) +" calorias");
        holder.txtPontuacao.setText("Pontuação: " +  String.valueOf(estatistica.getPontuacao()));
    }

    @Override
    public int getItemCount() {
        return estatisticas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView titulo;
        private TextView txtDistanciaTotal;
        private TextView txtCalorias;
        private TextView txtTempoTotal;
        private TextView txtPontuacao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imgAtividade);
            titulo = itemView.findViewById(R.id.txtTituloAtividade);
            txtDistanciaTotal = itemView.findViewById(R.id.txtDistanciaTotal);
            txtCalorias = itemView.findViewById(R.id.txtCalorias);
            txtTempoTotal = itemView.findViewById(R.id.txtTempoTotal);
            txtPontuacao = itemView.findViewById(R.id.txtPontuacao);
        }
    }
}
