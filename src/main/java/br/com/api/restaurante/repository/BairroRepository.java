package br.com.api.restaurante.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.restaurante.model.Bairro;

public interface BairroRepository extends JpaRepository<Bairro, Long> {

	List<Bairro> findByCidadeId(Long cidadeId);

	Optional<Bairro> findOptionalByCidadeIdAndNomeContaining(Long cidade, String nome);

}
