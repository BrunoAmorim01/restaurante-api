package br.com.api.restaurante.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.restaurante.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	public Page<Cliente> findByNomeContaining(String nome, Pageable pageable);
	public List<Cliente>  findByNomeIgnoreCaseContaining(String nome);

}
