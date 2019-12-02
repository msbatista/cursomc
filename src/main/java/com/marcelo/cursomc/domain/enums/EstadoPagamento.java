package com.marcelo.cursomc.domain.enums;

public enum EstadoPagamento {
	PENDENTE(1, "Pagamento Quitado"),
	QUITADO(2, "Pagamento Quitado"),
	CANCELADO(3, "Pagamento Cancelado");
	
	private final int codigo;
	private final String descricao;
	
	private EstadoPagamento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoPagamento toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (EstadoPagamento estadoPagamento : EstadoPagamento.values()) {
			if (codigo.equals(estadoPagamento.codigo)) {
				return estadoPagamento;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + codigo);
		
	}
}
