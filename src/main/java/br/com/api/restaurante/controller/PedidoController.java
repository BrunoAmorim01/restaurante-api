package br.com.api.restaurante.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.restaurante.event.RecursoCriadoEvent;
import br.com.api.restaurante.mail.Mailer;
import br.com.api.restaurante.model.Pedido;
import br.com.api.restaurante.repository.PedidoRepository;
import br.com.api.restaurante.repository.Pedido.PedidoFilter;
import br.com.api.restaurante.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private Mailer mailer;

	@PreAuthorize("hasRole('CADASTRAR_PEDIDO')")
	@PostMapping
	public ResponseEntity<Pedido> novo(@Valid @RequestBody Pedido pedido, HttpServletResponse response) {
		Pedido pedidoSalvo = pedidoService.salvar(pedido);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pedidoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
	}

	@PreAuthorize("hasRole('CADASTRAR_PEDIDO')")
	@PutMapping("/{codigo}")
	public ResponseEntity<Pedido> atualizar(@Valid @RequestBody Pedido pedido) {
		Pedido pedidoSalvo = pedidoService.atualizar(pedido);
		return ResponseEntity.ok(pedidoSalvo);
	}

	@PreAuthorize("hasRole('PESQUISAR_PEDIDO')")
	// @PreAuthorize("hasAuthority(#oauth2.hasScope('message:read'))")
	@GetMapping("/{codigo}")
	public ResponseEntity<Pedido> porCodigo(@PathVariable Long codigo) {
		Optional<Pedido> pedido = pedidoService.buscarPedidoPorCodigo(codigo);
		return pedido.isPresent() ? ResponseEntity.ok(pedido.get()) : ResponseEntity.notFound().build();
	}

	@PreAuthorize("hasRole('PESQUISAR_PEDIDO')")
	@GetMapping
	public Page<Pedido> listar(PedidoFilter filter, Pageable pageable) {
		return pedidoRepository.filtrar(filter, pageable);
	}

	@PreAuthorize("hasRole('CONCLUIR_PEDIDO')")
	@PutMapping("/{codigo}/concluir")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void concluirPedido(@PathVariable Long codigo) {
		pedidoService.concluir(codigo);
	}

	@PreAuthorize("hasRole('CANCELAR_PEDIDO')")
	@PutMapping("/{codigo}/cancelar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelarPedido(@PathVariable Long codigo) {
		pedidoService.cancelar(codigo);
	}

	@GetMapping(value = "/{codigo}", params = "enviarEmail")
	public void enviarEmail(@PathVariable Long codigo) {

		Optional<Pedido> pedido = pedidoRepository.findById(codigo);
		mailer.enviarPedidoCliente(pedido.get());
	}

}
