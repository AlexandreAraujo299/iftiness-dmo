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

import com.dmo.iftiness.model.Atividade;
import com.dmo.iftiness.model.Usuario;
import com.dmo.iftiness.repository.AtividadeRepository;
import com.dmo.iftiness.repository.UsuariosRepository;

import java.util.List;
import java.util.Optional;

public class AtividadeViewModel extends AndroidViewModel {

    private AtividadeRepository atividadeRepository;


    public AtividadeViewModel(@NonNull Application application) {
        super(application);
        atividadeRepository = new AtividadeRepository(application);
    }

    public void create(Atividade atividade){
        atividadeRepository.inserir(atividade);
    }

    public void update(Atividade atividade){
        atividadeRepository.update(atividade);
    }

    public void delete(Atividade atividade){
        atividadeRepository.delete(atividade);
    }

    public LiveData<List<Atividade>> loadAll(String userId){
        return atividadeRepository.loadAll(userId);
    }

}
