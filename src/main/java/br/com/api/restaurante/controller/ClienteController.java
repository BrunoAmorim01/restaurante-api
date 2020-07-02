package br.com.api.restaurante.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.restaurante.event.RecursoCriadoEvent;
import br.com.api.restaurante.model.Cliente;
import br.com.api.restaurante.repository.ClienteRepository;
import br.com.api.restaurante.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PreAuthorize("hasRole('CADASTRAR_CLIENTE')")
	@PostMapping
	public ResponseEntity<Cliente> novo(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
		Cliente clienteSalvo = clienteService.salvar(cliente);
		publisher.publishEvent(new RecursoCriadoEvent(clienteSalvo, response, clienteSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}

	@PreAuthorize("hasRole('CADASTRAR_CLIENTE')")
	@PutMapping("/{codigo}")
	public ResponseEntity<Cliente> atualizar(@Valid @RequestBody Cliente cliente) {
		Cliente clienteSalvo = clienteService.atualizar(cliente);
		return ResponseEntity.ok(clienteSalvo);
	}

	@PreAuthorize("hasRole('PESQUISAR_CLIENTE')")
	@GetMapping("/{codigo}")
	public ResponseEntity<Cliente> porCodigo(@PathVariable Long codigo) {
		Optional<Cliente> cliente = clienteRepository.findById(codigo);
		return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
	}

	@PreAuthorize("hasRole('PESQUISAR_CLIENTE')")
	@GetMapping
	public Page<Cliente> listar(@RequestParam(required = false, defaultValue = "%") String nome, Pageable pageable) {
		return clienteRepository.findByNomeContaining(nome, pageable);
	}
	
	@PreAuthorize("hasRole('PESQUISAR_CLIENTE')")
	@GetMapping("por-nome")
	public List<Cliente> porNome(String nome){
		return clienteRepository.findByNomeIgnoreCaseContaining(nome);
	}

}
