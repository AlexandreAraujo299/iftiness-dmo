package com.dmo.iftiness.model;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Atividade implements Serializable {
    private String id;
    private Usuario usuario;
    private String categoria;
    private double distancia;
    private int duracao;
    private String data_atividade;

    public Atividade(Usuario usuario, String categoria, double distancia, int duracao, String data_atividade) {
        this.id = UUID.randomUUID().toString();
        this.usuario = usuario;
        this.categoria = categoria;
        this.distancia = distancia;
        this.duracao = duracao;
        this.data_atividade = data_atividade;
    }

    @Ignore
    public Atividade() {
        this( null, "", 0.0, 0, "");
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getData_atividade() {
        return data_atividade;
    }

    public void setData_atividade(String data_atividade) {
        this.data_atividade = data_atividade;
    }


    @NonNull


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atividade atividade = (Atividade) o;
        return id.equals(atividade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
