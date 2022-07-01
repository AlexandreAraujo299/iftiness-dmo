package com.dmo.iftiness.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.dmo.iftiness.model.Usuario;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuario WHERE email = :email AND senha = :senha")
    Usuario login(String email, String senha);

    @Transaction
    @Query("SELECT * FROM usuario WHERE id = :usuarioId")
    LiveData<Usuario> loadUsuarioComEndereco(String usuarioId);

    @Insert
    void insert(Usuario usuario);

    /*@Insert
    void insert(Endereco endereco);*/

    @Update
    void update(Usuario usuario);

    /*@Update
    void update(Endereco endereco);*/
}
