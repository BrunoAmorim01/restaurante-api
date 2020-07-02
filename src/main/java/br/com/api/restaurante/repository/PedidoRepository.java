package br.com.api.restaurante.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.api.restaurante.model.Pedido;
import br.com.api.restaurante.repository.Pedido.PedidoRepositoryQuery;

public interface PedidoRepository extends JpaRepository<Pedido, Long>, PedidoRepositoryQuery {	
	
	@Query(value = "SELECT SUM(p.total) FROM Pedido p WHERE EXTRACT (YEAR_MONTH FROM p.dataCriacao ) = EXTRACT (YEAR_MONTH FROM :anoMes)")
	public BigDecimal totalVendasMesAtual(LocalDate anoMes);
}
