package br.com.api.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.restaurante.model.Cidade;
import br.com.api.restaurante.model.Estado;
import br.com.api.restaurante.repository.CidadeRepository;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<Cidade> listarPorIdEstado(@RequestParam Long estado) {
		return cidadeRepository.findByEstadoId(estado);
	}

	@GetMapping("/por-nome")
	@PreAuthorize("isAuthenticated()")
	public Cidade listarPorIdEstado(@RequestParam Long estado, @RequestParam String cidade) {
		var cidadeRetornada = cidadeRepository.findOptionalByEstadoIdAndNomeContaining(estado, cidade);		
		if (cidadeRetornada.isEmpty()) {
			Cidade c = new Cidade();
			c.setEstado(new Estado());
			c.getEstado().setId(estado);
			c.setNome(cidade);
			return cidadeRepository.save(c);
		}
		return cidadeRetornada.get();
	}
}
