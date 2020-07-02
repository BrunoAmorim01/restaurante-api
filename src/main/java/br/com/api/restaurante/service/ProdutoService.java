package br.com.api.restaurante.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.api.restaurante.model.Produto;
import br.com.api.restaurante.repository.ProdutoRepository;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Produto salvar(Produto produto){		

		return produtoRepository.save(produto);
	}
	
	/*
	public Produto atualizar(Long codigo, Produto produto) {
		Produto produtoSalvo = buscarProdutoPorCodigo(codigo);
		return null;
	}*/

	public Optional<Produto> buscarProdutoPorCodigo(Long codigo) {
		Optional<Produto> produtoSalvo = produtoRepository.findById(codigo);
		
		if(!produtoSalvo.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return produtoSalvo;
	}
	
	
}
