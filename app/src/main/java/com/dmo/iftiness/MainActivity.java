package com.dmo.iftiness;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dmo.iftiness.adapter.AtividadeAdapter;
import com.dmo.iftiness.model.Atividade;
import com.dmo.iftiness.model.Usuario;
import com.dmo.iftiness.viewmodel.AtividadeViewModel;
import com.dmo.iftiness.viewmodel.UsuarioViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView txtTitulo, txtLogin;
    private UsuarioViewModel usuarioViewModel;
    private AtividadeViewModel atividadeViewModel;

    private RecyclerView rvProduto;
    private AtividadeAdapter atividadeAdapter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);
        atividadeViewModel = new ViewModelProvider(this)
                .get(AtividadeViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText(getString(R.string.app_name));

        drawerLayout = findViewById(R.id.nav_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.toogle_open, R.string.toogle_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_account:
                        intent = new Intent(MainActivity.this, UsuarioPerfilActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_register_atividade:
                        intent = new Intent(MainActivity.this, AtividadeFisicaCadastroActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_sair:
                        usuarioViewModel.logout();
                        finish();
                        startActivity(getIntent());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        txtLogin = navigationView.getHeaderView(0).findViewById(R.id.header_profile_name);
        txtLogin.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UsuarioLoginActivity.class);
            startActivity(intent);
        });



        rvProduto = findViewById(R.id.rvAtividade);
        atividadeAdapter = new AtividadeAdapter(MainActivity.this);

        rvProduto.setAdapter(atividadeAdapter);
        rvProduto.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onResume() {
        super.onResume();
        usuarioViewModel.isLogged().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null) {
                    txtLogin.setText(usuario.getNome());
                    atividadeViewModel.loadAll(usuario.getId()).observe(MainActivity.this, new Observer<List<Atividade>>() {
                        @Override
                        public void onChanged(List<Atividade> atividades) {
                            atividadeAdapter.setProdutos(atividades);
                            atividadeAdapter.notifyDataSetChanged();
                            rvProduto.setAdapter(atividadeAdapter);
                        }
                    });
                } else {
                    startActivity(new Intent(MainActivity.this,
                            UsuarioLoginActivity.class));
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed () {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}