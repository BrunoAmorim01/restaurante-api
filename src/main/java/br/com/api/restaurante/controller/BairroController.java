package br.com.api.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.restaurante.model.Bairro;
import br.com.api.restaurante.model.Cidade;
import br.com.api.restaurante.repository.BairroRepository;

@RestController
@RequestMapping("/bairros")
public class BairroController {

	@Autowired
	private BairroRepository bairroRepository;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<Bairro> listarPorIdCidade(@RequestParam Long cidade) {
		return bairroRepository.findByCidadeId(cidade);

	}

	@GetMapping("/por-nome")
	@PreAuthorize("isAuthenticated()")
	public Bairro listarPorIdCidade(@RequestParam Long cidade, @RequestParam String bairro) {
		var b = bairroRepository.findOptionalByCidadeIdAndNomeContaining(cidade, bairro);
		if (b.isEmpty()) {
			var novoBairro = new Bairro();
			novoBairro.setCidade(new Cidade());
			novoBairro.getCidade().setId(cidade);
			novoBairro.setNome(bairro);
			return bairroRepository.save(novoBairro);
		}
		return b.get();

	}

}
