package br.com.api.restaurante.model;

public enum StatusPedido {
	ABERTO("Aberto"), CONCLUIDO("Concluido"), CANCELADO("Cancelado");

	private String descricao;

	StatusPedido(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
