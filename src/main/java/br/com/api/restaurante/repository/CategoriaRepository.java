package br.com.api.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.restaurante.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
