package com.mycompany.cinema;

public class Assento {
    private String codigo;
    private double preco;

    public Assento(String codigo, double preco) {
        this.codigo = codigo;
        this.preco = preco;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPreco() {
        return preco;
    }
}
