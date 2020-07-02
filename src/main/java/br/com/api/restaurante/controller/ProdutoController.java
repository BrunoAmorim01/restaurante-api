package br.com.api.restaurante.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.restaurante.model.Produto;
import br.com.api.restaurante.repository.ProdutoRepository;
import br.com.api.restaurante.repository.produto.ProdutoFilter;
import br.com.api.restaurante.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoService produtoService;

	// @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PRODUTO') and
	// #oauth2.hasScope('read')")
	@PreAuthorize("hasRole('PESQUISAR_PRODUTO')")
	@GetMapping
	public Page<Produto> listar(ProdutoFilter filter, Pageable pageable) {

		return produtoRepository.filtrar(filter, pageable);
	}

	// @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and
	// #oauth2.hasScope('write')")
	@PreAuthorize("hasRole('CADASTRAR_PRODUTO')")
	@PostMapping
	public ResponseEntity<Produto> novo(@Valid @RequestBody Produto produto, HttpServletResponse response) {
		Produto produtoSalvo = produtoService.salvar(produto);

		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
	}

	// @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PRODUTO') and
	// #oauth2.hasScope('read')")
	@PreAuthorize("hasRole('PESQUISAR_PRODUTO')")
	@GetMapping("/{codigo}")
	public ResponseEntity<Produto> porCodigo(@PathVariable Long codigo) {
		Optional<Produto> produto = produtoService.buscarProdutoPorCodigo(codigo);

		return produto.isPresent() ? ResponseEntity.ok(produto.get()) : ResponseEntity.notFound().build();
	}

	// @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PRODUTO') and
	// #oauth2.hasScope('write')")
	@PreAuthorize("hasRole('CADASTRAR_PRODUTO')")
	@PutMapping("/{codigo}")
	public ResponseEntity<Produto> atualizar(@Valid @RequestBody Produto produto) {
		Produto produtoSalvo = produtoService.salvar(produto);
		return ResponseEntity.ok(produtoSalvo);
	}

	@PreAuthorize("hasRole('REMOVER_PRODUTO')")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		produtoRepository.deleteById(codigo);
	}

	@PreAuthorize("hasRole('PESQUISAR_PRODUTO')")
	@GetMapping("/por-nome")
	public List<Produto> porNome(String nome) {
		return produtoRepository.findByNomeIgnoreCaseContaining(nome);
	}

}
