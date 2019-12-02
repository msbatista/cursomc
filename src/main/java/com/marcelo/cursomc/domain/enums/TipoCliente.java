package com.marcelo.cursomc.domain.enums;

public enum TipoCliente {

	PESSOA_FISICA(1, "Pessoa Física"), 
	PESSOA_JURIDICA(2, "Pessoa Jurídica");

	private final int cod;
	private final String descricao;

	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCliente toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoCliente tipoCliente : TipoCliente.values()) {
			if (cod.equals(tipoCliente.getCod())) {
				return tipoCliente;
			}
		}

		throw new IllegalArgumentException("Id invalido: " + cod);

	}

}
