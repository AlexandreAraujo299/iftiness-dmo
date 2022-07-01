package com.dmo.iftiness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dmo.iftiness.model.Usuario;
import com.dmo.iftiness.viewmodel.UsuarioViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class UsuarioLoginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtTitulo;
    private Button btnCadastrar;
    private Button btnEntrar;
    private TextInputEditText txtEmail;
    private TextInputEditText txtSenha;
    private TextView txtEsqueceuSenha;
    private AlertDialog dialogResetPassword;

    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_login);


        usuarioViewModel =
                new ViewModelProvider(this).get(UsuarioViewModel.class);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText(R.string.usuario_login_titulo);
        txtEmail = findViewById(R.id.txt_edit_email);
        txtSenha = findViewById(R.id.txt_edit_login_senha);

        btnCadastrar = findViewById(R.id.btn_login_cadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioLoginActivity.this,
                        UsuarioCadastroActivity.class);
                startActivity(intent);

                finish();

            }
        });

        Intent intent = new Intent(this, MainActivity.class);
        Handler handler = new Handler();
        btnEntrar = findViewById(R.id.btn_usuario_login);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioViewModel.login(txtEmail.getText().toString(),
                        txtSenha.getText().toString())
                        .observe(UsuarioLoginActivity.this, new Observer<Usuario>() {
                            @Override
                            public void onChanged(Usuario usuario) {
                                if (usuario == null) {
                                    Toast.makeText(getApplicationContext(), "Login Erro",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Login else",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    //finish();
                                }
                            }
                        });
            }
        });

       // buildResetPasswordDialog();

        txtEsqueceuSenha = findViewById(R.id.txt_esqueceu_senha);
        txtEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogResetPassword.show();
            }
        });
    }

    /*private void buildResetPasswordDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_reset_password, null);
        TextInputEditText edtEmail = view.findViewById(R.id.txt_edit_email);

        dialogResetPassword = new MaterialAlertDialogBuilder(this)
                .setPositiveButton(R.string.btn_ok_resetar_senha, (dialog, which) -> {
                    usuarioViewModel.resetPassword(edtEmail.getText().toString());
                    Toast.makeText(UsuarioLoginActivity.this, getString(R.string.msg_resetar_email), Toast.LENGTH_LONG).show();
                    edtEmail.getText().clear();
                })
                .setNegativeButton(R.string.btn_cancel_resetar_senha, (dialog, which) -> {
                    edtEmail.getText().clear();
                })
                .setIcon(android.R.drawable.ic_dialog_email)
                .setView(view)
                .setTitle(R.string.title_dialog_resetar_senha)
                .create();
    }*/


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}