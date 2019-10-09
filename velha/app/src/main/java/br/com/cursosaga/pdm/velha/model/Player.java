package br.com.cursosaga.pdm.velha.model;

public class Player {
    private String nome;
    private int escolha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nomePlayer) {
        this.nome = nomePlayer;
    }

    public int getEscolha() {
        return escolha;
    }

    public void setEscolha(int escolha) {
        this.escolha = escolha;
    }
}
