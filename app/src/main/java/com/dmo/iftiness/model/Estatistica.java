package com.dmo.iftiness.model;

import java.io.Serializable;

public class Estatistica implements Serializable {
    private String atividade;
    private double distanciaTotal;
    private int duracaoTotal;
    private int totalCalorias;
    private int pontuacao;

    public  Estatistica (){

    }

    public Estatistica(String atividade, double distanciaTotal, int duracaoTotal, int totalCalorias, int pontuacao) {
        this.atividade = atividade;
        this.distanciaTotal = distanciaTotal;
        this.duracaoTotal = duracaoTotal;
        this.totalCalorias = totalCalorias;
        this.pontuacao = pontuacao;
    }

    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setDistanciaTotal(double distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public int getDuracaoTotal() {
        return duracaoTotal;
    }

    public void setDuracaoTotal(int duracaoTotal) {
        this.duracaoTotal = duracaoTotal;
    }

    public int getTotalCalorias() {
        return totalCalorias;
    }

    public void setTotalCalorias(int totalCalorias) {
        this.totalCalorias = totalCalorias;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }
}
