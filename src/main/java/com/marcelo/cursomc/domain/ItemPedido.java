package com.marcelo.cursomc.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();

	private double desconto;
	private Integer quantidade;
	private double preco;

	public ItemPedido() {
	}

	public ItemPedido(
			Pedido pedido,
			Produto produto,
			double desconto,
			Integer quantidade,
			double preco) {
		super();
		this.id.setPedido(pedido);
		this.id.setProduto(produto);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Double getSubTotal() { return (preco - desconto) * quantidade; }

	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}

	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}

	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}

	public Produto getProduto() {
		return id.getProduto();
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
		if (o instanceof ItemPedido) {
			ItemPedido item = (ItemPedido) o;
			return this.id.equals(item.id);
		}
		return false;
	}
}
