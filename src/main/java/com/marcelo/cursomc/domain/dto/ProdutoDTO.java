package com.marcelo.cursomc.domain.dto;

import com.marcelo.cursomc.domain.Produto;

import java.io.Serializable;

public class ProdutoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String nome;
    private double price;

    public ProdutoDTO() {
    }


    public ProdutoDTO(Produto produto) {
        id = produto.getId();
        nome = produto.getNome();
        price = produto.getPrice();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
