package br.com.api.restaurante.repository.produto;

import br.com.api.restaurante.model.Categoria;


public class ProdutoFilter {
	
	private String nome;	
		
	private Categoria categoria;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	
	
}
