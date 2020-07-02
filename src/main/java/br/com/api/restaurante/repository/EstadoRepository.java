package br.com.api.restaurante.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.restaurante.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
	
	public Optional<Estado> findBySigla(String sigla);
	
	
	
}
