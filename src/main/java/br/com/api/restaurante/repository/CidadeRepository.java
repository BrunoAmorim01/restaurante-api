package br.com.api.restaurante.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.restaurante.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	List<Cidade> findByEstadoId(Long estadoId);

	Optional<Cidade> findOptionalByEstadoIdAndNomeContaining(Long estadoId, String nome);

}
