package br.com.api.restaurante.model;

public enum Origem {

	NACIONAL("Nacional"),
	INTERNACIONAL("Internacional");
	
	private String descricao;
	
	 Origem(String descricao) {
		 this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
