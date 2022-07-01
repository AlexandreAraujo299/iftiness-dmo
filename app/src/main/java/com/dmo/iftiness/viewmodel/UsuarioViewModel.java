package com.dmo.iftiness.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dmo.iftiness.model.Usuario;
import com.dmo.iftiness.repository.UsuariosRepository;

import java.util.Optional;

public class UsuarioViewModel extends AndroidViewModel{
    public static final String USUARIO_ID = "USUARIO_ID";

    private UsuariosRepository usuariosRepository;


    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        usuariosRepository = new UsuariosRepository(application);
    }

    public void createUsuario(Usuario usuario){
        usuariosRepository.createUsuario(usuario);
    }

    public void update(Usuario usuario){
        usuariosRepository.update(usuario);
    }

    public LiveData<Usuario> login(String email, String senha) {
        return usuariosRepository.login(email, senha);
    }

    public void logout(){
        PreferenceManager.getDefaultSharedPreferences(getApplication())
                .edit().remove(USUARIO_ID)
                .apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<Usuario> isLogged(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        Optional<String> id = Optional.ofNullable(sharedPreferences.getString(USUARIO_ID, null));
        if(!id.isPresent()){
            return new MutableLiveData<>(null);
        }
        return usuariosRepository.load(id.get());
    }

    public void resetPassword(String email) {
        usuariosRepository.resetPassword(email);
    }
}
