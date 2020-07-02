package br.com.api.restaurante.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.restaurante.model.Categoria;
import br.com.api.restaurante.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepo;	
	
	//@PreAuthorize("hasAuthority('admin')")
	@PreAuthorize("hasRole('PESQUISAR_CATEGORIA')")
	@GetMapping
	public List<Categoria> listar(){		
		return categoriaRepo.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> novo(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
		Categoria categoriaSalva =  categoriaRepo.save(categoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
		
	}

}
