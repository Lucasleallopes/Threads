package com.mycompany.cinema;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private String nome;
    private String endereco;
    private List<Sessao> sessoes;

    public Cinema(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.sessoes = new ArrayList<>();
    }

    public void adicionarSessao(Sessao sessao) {
        sessoes.add(sessao);
    }

    public List<Sessao> listarSessoes() {
        return sessoes;
    }

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}
}

