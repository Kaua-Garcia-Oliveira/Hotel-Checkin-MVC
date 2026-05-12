package com.hotel.model;

import java.math.BigDecimal;

public class Quarto {
    private int id;
    private String numero;
    private String tipo; // SIMPLES, DUPLO, SUITE
    private BigDecimal precoDiaria;
    private boolean disponivel;
    private String descricao;

    public Quarto() {}

    public Quarto(int id, String numero, String tipo, BigDecimal precoDiaria, boolean disponivel, String descricao) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.precoDiaria = precoDiaria;
        this.disponivel = disponivel;
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public BigDecimal getPrecoDiaria() { return precoDiaria; }
    public void setPrecoDiaria(BigDecimal precoDiaria) { this.precoDiaria = precoDiaria; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return "Quarto{id=" + id + ", numero='" + numero + "', tipo='" + tipo + "'}";
    }
}
