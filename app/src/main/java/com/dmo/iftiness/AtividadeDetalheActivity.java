package com.dmo.iftiness;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dmo.iftiness.model.Atividade;
import com.dmo.iftiness.model.Usuario;
import com.dmo.iftiness.viewmodel.AtividadeViewModel;
import com.dmo.iftiness.viewmodel.UsuarioViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AtividadeDetalheActivity extends AppCompatActivity {

    private Atividade atividade;
    private Toolbar toolbar;
    private TextView txtTitulo;
    private TextInputEditText txtDistancia;
    private TextInputEditText txtDuracao;
    private TextInputEditText txtDataAtividade;
    private Spinner spCategoria;
    private Spinner spinner;
    private Button btnSalvar;
    private Button btnDeletar;

    private AtividadeViewModel atividadeViewModel;
    private UsuarioViewModel usuarioViewModel;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_detalhe);

        atividade = (Atividade) getIntent().getSerializableExtra("ATIVIDADE");

        spinner = (Spinner) findViewById(R.id.categoria_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorias_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Atividade");


        txtDistancia = findViewById(R.id.txt_edit_distancia);
        txtDistancia.setText(String.valueOf(atividade.getDistancia()));

        txtDuracao = findViewById(R.id.txt_edit_duracao);
        txtDuracao.setText(String.valueOf(atividade.getDuracao()));

        txtDataAtividade = findViewById(R.id.input_data_atividade);
        txtDataAtividade.setText(atividade.getData_atividade());

        spCategoria = findViewById(R.id.categoria_spinner);

        String[] categorias = getResources().getStringArray(R.array.categorias_array);
        for (int i = 0; i < categorias.length; i++){
            if(categorias[i].equals(atividade.getCategoria())){
                spCategoria.setSelection(i);
            }
        }

        atividadeViewModel = new ViewModelProvider(this)
                .get(AtividadeViewModel.class);
        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);



        btnDeletar = findViewById(R.id.btn_excluir_atividade);
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atividadeViewModel.delete(atividade);
                finish();
            }
        });

        btnSalvar = findViewById(R.id.btn_salvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }

    public void update(){
        if(!validate()){
            return;
        }

        atividade.setData_atividade(txtDataAtividade.getText().toString());
        atividade.setCategoria(spCategoria.getSelectedItem().toString());
        atividade.setDistancia(Double.parseDouble(txtDistancia.getText().toString()));
        atividade.setDuracao(Integer.parseInt(txtDuracao.getText().toString()));
        atividade.setUsuario(user);

        atividadeViewModel.update(atividade);
        Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show();

    }

    private boolean validate(){
        boolean isValid = true;
        if(txtDataAtividade.getText().toString().trim().isEmpty()){
            txtDataAtividade.setError("Preencha o campo Data de Atividade");
            isValid = false;
        }else{
            txtDataAtividade.setError(null);
        }
        if(txtDuracao.getText().toString().trim().isEmpty()){
            txtDuracao.setError("Preencha o campo Duração");
            isValid = false;
        }else{
            txtDuracao.setError(null);
        }
        if(txtDistancia.getText().toString().trim().isEmpty()){
            txtDistancia.setError("Preencha o campo Distância");
            isValid = false;
        }else{
            txtDistancia.setError(null);
        }

        return isValid;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onResume() {
        super.onResume();
        usuarioViewModel.isLogged().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null) {
                    user = usuario;
                } else {
                    startActivity(new Intent(AtividadeDetalheActivity.this,
                            UsuarioLoginActivity.class));
                    finish();
                }
            }
        });

    }
}