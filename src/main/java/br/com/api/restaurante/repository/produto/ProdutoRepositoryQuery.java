package br.com.api.restaurante.repository.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.api.restaurante.model.Produto;

public interface ProdutoRepositoryQuery {	

	public Page<Produto> filtrar(ProdutoFilter filter, Pageable pageable);
	

}
