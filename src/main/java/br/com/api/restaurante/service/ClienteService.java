package br.com.api.restaurante.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.api.restaurante.model.Cliente;
import br.com.api.restaurante.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Cliente atualizar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Optional<Cliente> buscarClientePorCodigo(Long codigo) {
		Optional<Cliente> clienteSalvo = clienteRepository.findById(codigo);
		if (!clienteSalvo.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}

		return clienteSalvo;
	}
}
