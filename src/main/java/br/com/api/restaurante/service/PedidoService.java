package br.com.api.restaurante.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.api.restaurante.model.Pedido;
import br.com.api.restaurante.model.StatusPedido;
import br.com.api.restaurante.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	public Pedido salvar(Pedido pedido) {
		pedido.getItens().forEach(i -> i.setPedido(pedido));
		pedido.setDataCriacao(LocalDateTime.now());
		return pedidoRepository.save(pedido);
	}

	public Pedido atualizar(Pedido pedido) {

		Pedido pedidoSalvo = buscarPedidoPorCodigo(pedido.getId()).get();
		pedidoSalvo.getItens().clear();
		pedidoSalvo.getItens().addAll(pedido.getItens());
		pedidoSalvo.getItens().forEach(i -> i.setPedido(pedidoSalvo));
		
		pedidoSalvo.setDesconto(pedido.getDesconto());
		pedidoSalvo.setObservacao(pedido.getObservacao());
		pedidoSalvo.setTotal(pedido.getTotal());
		
		BeanUtils.copyProperties(pedido, pedidoSalvo, "id", "itens");
		return pedidoRepository.save(pedidoSalvo);
		// pedido.getItens().forEach( i -> i.setPedido(pedido));
		// return pedidoRepository.save(pedido);
	}

	public Optional<Pedido> buscarPedidoPorCodigo(Long codigo) {
		Optional<Pedido> pedidoSalvo = pedidoRepository.findById(codigo);

		if (!pedidoSalvo.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return pedidoSalvo;
	}

	public void concluir(Long codigo) {
		Pedido pedidoSalvo = buscarPedidoPorCodigo(codigo).get();
		pedidoSalvo.setStatusPedido(StatusPedido.CONCLUIDO);
		pedidoRepository.save(pedidoSalvo);
	}
	
	public void cancelar(Long codigo) {
		Pedido pedidoSalvo = buscarPedidoPorCodigo(codigo).get();
		pedidoSalvo.setStatusPedido(StatusPedido.CANCELADO);
		pedidoRepository.save(pedidoSalvo);
	}

}
