package br.com.api.restaurante.repository.Pedido;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.api.restaurante.dto.RankingProduto;
import br.com.api.restaurante.model.Pedido;

public interface PedidoRepositoryQuery {
	
	public Page<Pedido> filtrar(PedidoFilter filter, Pageable pageable);
	public BigDecimal vendaMediaMesAtual();
	public List<RankingProduto> rankingProdutosMaisVendidosMesAtual();
	
}
