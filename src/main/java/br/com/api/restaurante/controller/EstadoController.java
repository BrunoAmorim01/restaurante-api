package br.com.api.restaurante.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.restaurante.model.Estado;
import br.com.api.restaurante.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@GetMapping
	public List<Estado> listEstados() {
		return estadoRepository.findAll();
	}
	@GetMapping("/por-sigla")
	public ResponseEntity<Estado> porNome(@RequestParam String sigla){
		System.out.println("sigla " + sigla);
		Optional<Estado> estado = estadoRepository.findBySigla(sigla.toUpperCase());
		return estado.isPresent() ? ResponseEntity.ok(estado.get()) : ResponseEntity.notFound().build();
	}
	

}
