package com.marcelo.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private double price;

	@JsonBackReference
	@ManyToMany
	@JoinTable(
			name = "PRODUTO_CATEGORIA", 
			joinColumns = @JoinColumn(name = "produto_id"), 
			inverseJoinColumns = @JoinColumn(name = "categoria_id")
	)
	private final List<Categoria> categorias = new ArrayList<Categoria>();

	public Produto() {

	}

	public Produto(Integer id, String nome, double price) {
		this.id = id;
		this.nome = nome;
		this.price = price;
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

	public List<Categoria> getCategorias() {
		return categorias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Produto) {
			Produto p = (Produto) o;
			return this.id == p.id;
		}
		return false;
	}

}
