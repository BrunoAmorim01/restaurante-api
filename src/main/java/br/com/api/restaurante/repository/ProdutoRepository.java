package br.com.api.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.restaurante.model.Produto;
import br.com.api.restaurante.repository.produto.ProdutoRepositoryQuery;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery{
	
	public List<Produto> findByNomeIgnoreCaseContaining(String nome);
	
	
}
