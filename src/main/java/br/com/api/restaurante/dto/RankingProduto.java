package br.com.api.restaurante.dto;

public class RankingProduto {

	private String nome;
	private Long vezes;

	public RankingProduto(String nome, Long vezes) {
		this.nome = nome;
		this.vezes = vezes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getVezes() {
		return vezes;
	}
	
	public void setVezes(Long vezes) {
		this.vezes = vezes;
	}

}
