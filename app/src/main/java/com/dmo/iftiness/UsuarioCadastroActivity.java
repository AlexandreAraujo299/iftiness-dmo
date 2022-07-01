package com.dmo.iftiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dmo.iftiness.model.Usuario;
import com.dmo.iftiness.viewmodel.UsuarioViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class UsuarioCadastroActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private TextInputEditText txtNome;
    private TextInputEditText txtEmail;
    private TextInputEditText txtSenha;
    private Button btnCadastrar;

    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_cadastro);

        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText(R.string.usuario_cadastro_titulo);
        txtNome = findViewById(R.id.txt_edit_nome);
        txtEmail = findViewById(R.id.txt_edit_email);
        txtSenha = findViewById(R.id.txt_edit_senha);

        btnCadastrar = findViewById(R.id.btn_usuario_cadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    Usuario usuario = new Usuario(
                            txtNome.getText().toString(),
                            "",
                            "",
                            "",
                            txtEmail.getText().toString(),
                            txtSenha.getText().toString(),
                            0
                    );
                    if(usuario.getSenha().length() >= 6){
                        // insere um novo usuário no BD
                        usuarioViewModel.createUsuario(usuario);
                        // efetua o login do novo usuário
                        usuarioViewModel.login(usuario.getEmail(), usuario.getSenha())
                                .observe(UsuarioCadastroActivity.this, new Observer<Usuario>() {
                                    @Override
                                    public void onChanged(Usuario usuario) {
                                        finish();
                                    }
                                });
                    }else {
                        Toast.makeText(UsuarioCadastroActivity.this,
                                "erro",
                                Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    private boolean validate() {
        boolean isValid = true;
        if (txtNome.getText().toString().trim().isEmpty()) {
            txtNome.setError("Preencha o campo nome");
            isValid = false;
        } else {
            txtNome.setError(null);
        }
        if (txtSenha.getText().toString().trim().isEmpty()) {
            txtSenha.setError("Preencha o campo senha");
            isValid = false;
        } else {
            txtSenha.setError(null);
        }
        if (txtEmail.getText().toString().trim().isEmpty()) {
            txtEmail.setError("Preencha o campo e-mail");
            isValid = false;
        } else {
            txtEmail.setError(null);
        }
        return isValid;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}