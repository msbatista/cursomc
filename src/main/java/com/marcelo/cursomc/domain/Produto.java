package com.marcelo.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private double price;

	@JsonIgnore
	@ManyToMany
	@JoinTable(
		name = "PRODUTO_CATEGORIA",
		joinColumns = @JoinColumn(name = "produto_id"),
		inverseJoinColumns = @JoinColumn(name = "categoria_id")
	)
	private final List<Categoria> categorias = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "id.produto")
	private final Set<ItemPedido> itens = new HashSet<>();

	public Produto() {

	}

	public Produto(Integer id, String nome, double price) {
		this.id = id;
		this.nome = nome;
		this.price = price;
	}

	@JsonIgnore
	public List<Pedido> getPedidos() {
		List<Pedido> pedidos = new ArrayList<Pedido>();
		for (ItemPedido item : this.itens) {
			pedidos.add(item.getPedido());
		}
		return pedidos;
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

	public Set<ItemPedido> getItens() {
		return itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Produto) {
			Produto p = (Produto) o;
			return this.id.equals(p.id);
		}
		return false;
	}
}
