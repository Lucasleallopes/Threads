package com.mycompany.cinema;

public class Reserva {
    private Sessao sessao;
    private Cliente cliente;
    private Assento assento;
    
    public Reserva(Sessao sessao, Cliente cliente, Assento assento) {
        this.sessao = sessao;
        this.cliente = cliente;
        this.assento = assento;
    }

	public Sessao getSessao() {
		return sessao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Assento getAssento() {
		return assento;
	}
}

