package com.dmo.iftiness;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import com.dmo.iftiness.adapter.AtividadeAdapter;
import com.dmo.iftiness.adapter.EstatisticaAdapter;
import com.dmo.iftiness.model.Atividade;
import com.dmo.iftiness.model.Estatistica;
import com.dmo.iftiness.model.Usuario;
import com.dmo.iftiness.viewmodel.AtividadeViewModel;
import com.dmo.iftiness.viewmodel.UsuarioViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class EstatisticasActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView txtTitulo;
    private UsuarioViewModel usuarioViewModel;
    private AtividadeViewModel atividadeViewModel;
    private RecyclerView rvEstatistica;
    private EstatisticaAdapter estatisticaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);
        atividadeViewModel = new ViewModelProvider(this)
                .get(AtividadeViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Estatísticas");

        rvEstatistica = findViewById(R.id.rvEstatistica);
        estatisticaAdapter = new EstatisticaAdapter(EstatisticasActivity.this);

        rvEstatistica.setAdapter(estatisticaAdapter);
        rvEstatistica.setLayoutManager(new LinearLayoutManager(EstatisticasActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onResume() {
        super.onResume();
        usuarioViewModel.isLogged().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null) {
                    atividadeViewModel.loadAll(usuario.getId()).observe(EstatisticasActivity.this, new Observer<List<Atividade>>() {
                        @Override
                        public void onChanged(List<Atividade> atividades) {
                            List<Estatistica> estatisticas = new ArrayList<>();
                            Estatistica corrida = new Estatistica();
                            Estatistica natacao = new Estatistica();
                            Estatistica ciclismo = new Estatistica();
                            Estatistica caminhada = new Estatistica();
                            for(Atividade atv : atividades){
                                switch (atv.getCategoria()){
                                    case "Corrida":
                                        corrida.setAtividade("Corrida");
                                        corrida.setDistanciaTotal(corrida.getDistanciaTotal() + atv.getDistancia());
                                        corrida.setDuracaoTotal(corrida.getDuracaoTotal() + atv.getDuracao());
                                        corrida.setTotalCalorias(corrida.getTotalCalorias() + (atv.getDuracao()));
                                        corrida.setPontuacao(corrida.getPontuacao() + (int) Math.floor(atv.getDistancia()));
                                        break;
                                    case "Natação":
                                        natacao.setAtividade("Natação");
                                        natacao.setDistanciaTotal(natacao.getDistanciaTotal() + atv.getDistancia());
                                        natacao.setDuracaoTotal(natacao.getDuracaoTotal() + atv.getDuracao());
                                        natacao.setTotalCalorias(natacao.getTotalCalorias() + (atv.getDuracao()));
                                        natacao.setPontuacao(natacao.getPontuacao() + (int) Math.floor(atv.getDistancia()));
                                        break;
                                    case "Ciclismo":
                                        ciclismo.setAtividade("Ciclismo");
                                        ciclismo.setDistanciaTotal(ciclismo.getDistanciaTotal() + atv.getDistancia());
                                        ciclismo.setDuracaoTotal(ciclismo.getDuracaoTotal() + atv.getDuracao());
                                        ciclismo.setTotalCalorias(ciclismo.getTotalCalorias() + (atv.getDuracao()));
                                        ciclismo.setPontuacao(ciclismo.getPontuacao() + (int) Math.floor(atv.getDistancia()));
                                        break;
                                    case "Caminhada":
                                        caminhada.setAtividade("Caminhada");
                                        caminhada.setDistanciaTotal(caminhada.getDistanciaTotal() + atv.getDistancia());
                                        caminhada.setDuracaoTotal(caminhada.getDuracaoTotal() + atv.getDuracao());
                                        caminhada.setTotalCalorias(caminhada.getTotalCalorias() + (atv.getDuracao()));
                                        caminhada.setPontuacao(caminhada.getPontuacao() + (int) Math.floor(atv.getDistancia()));
                                        break;
                                }
                            }
                            if (corrida.getAtividade() != null){
                                estatisticas.add(corrida);
                            }
                            if (natacao.getAtividade() != null){
                                estatisticas.add(natacao);
                            }
                            if (ciclismo.getAtividade() != null){
                                estatisticas.add(ciclismo);
                            }
                            if (caminhada.getAtividade() != null){
                                estatisticas.add(caminhada);
                            }

                            estatisticaAdapter.setEstatisticas(estatisticas);
                            estatisticaAdapter.notifyDataSetChanged();
                            rvEstatistica.setAdapter(estatisticaAdapter);
                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}