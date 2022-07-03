package com.dmo.iftiness.repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dmo.iftiness.database.AppDatabase;
import com.dmo.iftiness.database.UsuarioDao;
import com.dmo.iftiness.model.Atividade;
import com.dmo.iftiness.model.Usuario;
import com.dmo.iftiness.viewmodel.UsuarioViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class AtividadeRepository {
    private UsuarioDao usuarioDao;

    private FirebaseFirestore firestore;

    private RequestQueue queue;

    private SharedPreferences preference;

    public AtividadeRepository(Application application) {
        usuarioDao = AppDatabase.getInstance(application).usuarioDao();
        firestore = FirebaseFirestore.getInstance();
        queue = Volley.newRequestQueue(application);
        preference = PreferenceManager.getDefaultSharedPreferences(application);
    }

    public void inserir(Atividade atividade){
        DocumentReference usuarioRef = firestore.collection("usuario")
                .document(atividade.getUsuario().getId());
        DocumentReference pedidoRef = firestore.collection("atividade")
                .document(atividade.getId());

        pedidoRef.set(atividade).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                System.out.println("************* ATIVIDADE CRIADA COM SUCESSO *****************");
            }
        });
    }

    public LiveData<Atividade> load(String usuarioId, String atividadeId){
        Atividade atividade = new Atividade();
        MutableLiveData<Atividade> liveData = new MutableLiveData<>();

        DocumentReference userRef = firestore.collection("usuario").document(usuarioId).collection("atividade").document(atividadeId);

        userRef.get().addOnSuccessListener(snapshot -> {
            Atividade atv = snapshot.toObject(Atividade.class);

            atv.setId(atv.getId());

            liveData.setValue(atv);
        });

        return liveData;
    }

    public LiveData<List<Atividade>> loadAll(String usuarioId){
        MutableLiveData<List<Atividade>> liveData = new MutableLiveData<>();

        firestore.collection("usuario").document(usuarioId)
                .collection("atividade")
                .addSnapshotListener((value, error) -> {
                    if(error != null){
                        return;
                    }

                    liveData.setValue(value.toObjects(Atividade.class));
                    System.out.println("aaaaaaaaaaaaaa");
                });

        return liveData;
    }


    public Boolean update(Atividade atividade){
        final Boolean[] atualizado = {false};

        DocumentReference atividadeRef = firestore.collection("usuario").document(atividade.getUsuario().getId()).collection("atividade").document(atividade.getId());

        atividadeRef.set(atividade).addOnSuccessListener(unused -> {
            atualizado[0] = true;
        });


        return atualizado[0];
    }

    public void delete(Atividade atividade){
        firestore.collection("usuario").document(atividade.getUsuario().getId())
                .collection("atividade").document(atividade.getId()).delete()
                .addOnSuccessListener(unused -> {
                    Log.d("db_delete", "Atividade deletada com sucesso");
                });
    }
}
