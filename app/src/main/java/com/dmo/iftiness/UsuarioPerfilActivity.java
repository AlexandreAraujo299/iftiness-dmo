package com.dmo.iftiness;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dmo.iftiness.model.Usuario;
import com.dmo.iftiness.viewmodel.UsuarioViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UsuarioPerfilActivity extends AppCompatActivity {

    private  Spinner spinner;
    private final int REQUEST_TAKE_PHOTO = 1;

    private Toolbar toolbar;
    private TextView txtTitulo;
    private TextInputEditText txtNome;
    private TextInputEditText txtEmail;
    private TextInputEditText txtDataNascimento;
    private TextInputEditText txtTelefone;
    private Spinner spSexo;
    private Button btnAtualizar;
    private ImageView imagePerfil;

    private Uri photoURI;

    private UsuarioViewModel usuarioViewModel;

    private Usuario user;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_perfil);

        spinner = (Spinner) findViewById(R.id.sexo_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText(R.string.usuario_perfil_titulo);
        txtNome = findViewById(R.id.txt_edit_nome);
        txtEmail = findViewById(R.id.txt_edit_email);
        txtDataNascimento = findViewById(R.id.input_data_nascimento);
        txtTelefone = findViewById(R.id.txt_edit_telefone);
        spSexo = findViewById(R.id.sexo_spinner);

        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);

        usuarioViewModel.isLogged().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario user) {
                if(user != null){
                    UsuarioPerfilActivity.this.user = user;
                    txtNome.setText(user.getNome());
                    txtDataNascimento.setText(user.getData_nascimento());
                    txtEmail.setText(user.getEmail());
                    txtTelefone.setText(user.getTelefone());


                    String[] sexo = getResources().getStringArray(R.array.sexo_array);
                    for (int i = 0; i < sexo.length; i++){
                        if(sexo[i].equals(user.getSexo())){
                            spSexo.setSelection(i);
                        }
                    }
                }else{
                    startActivity(new Intent(UsuarioPerfilActivity.this,
                            UsuarioLoginActivity.class));
                    finish();
                }
            }
        });

        btnAtualizar = findViewById(R.id.btn_usuario_perfil_salvar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
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

        user.setNome(txtNome.getText().toString());
        user.setEmail(txtEmail.getText().toString());
        user.setData_nascimento(txtDataNascimento.getText().toString());
        user.setSexo(spSexo.getSelectedItem().toString());
        user.setTelefone(txtTelefone.getText().toString());

        usuarioViewModel.update(user);
        Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show();

    }

    private boolean validate(){
        boolean isValid = true;
        if(txtNome.getText().toString().trim().isEmpty()){
            txtNome.setError("Preencha o campo nome");
            isValid = false;
        }else{
            txtNome.setError(null);
        }
        if(txtDataNascimento.getText().toString().trim().isEmpty()){
            txtDataNascimento.setError("Preencha o campo sobrenome");
            isValid = false;
        }else{
            txtDataNascimento.setError(null);
        }
        if(txtEmail.getText().toString().trim().isEmpty()){
            txtEmail.setError("Preencha o campo e-mail");
            isValid = false;
        }else{
            txtEmail.setError(null);
        }
        if(txtTelefone.getText().toString().trim().isEmpty()){
            txtTelefone.setError("Preencha o campo logradouro");
            isValid = false;
        }else{
            txtTelefone.setError(null);
        }

        return isValid;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit().putString(MediaStore.EXTRA_OUTPUT, photoURI.toString())
                .apply();
        imagePerfil.setImageURI(photoURI);
    }
}