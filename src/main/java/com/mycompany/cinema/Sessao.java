package com.mycompany.cinema;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Sessao {
    private String filme;
    private String sala;
    private List<Assento> assentosDisponiveis;

    public Sessao(String filme, String sala) {
        this.filme = filme;
        this.sala = sala;
        this.assentosDisponiveis = new ArrayList<>();
        
        // Criação de assentos disponíveis para a sessão
        assentosDisponiveis = Collections.synchronizedList(new ArrayList<>()); // lista sincronizada de assentos disponíveis
        for (char fileira = 'A'; fileira <= 'H'; fileira++) {
            for (int numeroAssento = 1; numeroAssento <= 9; numeroAssento++) {
                String codigoAssento = String.format("%c%d", fileira, numeroAssento);
                assentosDisponiveis.add(new Assento(codigoAssento, 23.0)); // Preço fictício de 23.0 para cada assento.
            }
        }
    }

    public List<Assento> listarAssentosDisponiveis() {
        return assentosDisponiveis;
    }

    public synchronized Reserva reservarAssento(Cliente cliente, String codigoAssento) {
        Assento assento = null;
        for (Assento a : assentosDisponiveis) {
            if (a.getCodigo().equals(codigoAssento)) {
                assento = a;
                break;
            }
        }
        if (assento != null) {
            assentosDisponiveis.remove(assento);
            return new Reserva(this, cliente, assento);
        } else {
            return null; // Assento não disponível ou inexistente.
        }
    }
    
    public synchronized String obterCodigoAssentoDisponivel() {
        if (!assentosDisponiveis.isEmpty()) {
            return assentosDisponiveis.remove(0).getCodigo();
        }
        return null;
    }


	public String getFilme() {
		return filme;
	}

	public String getSala() {
		return sala;
	}
}
