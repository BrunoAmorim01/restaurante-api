package br.com.api.restaurante.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.restaurante.dto.RankingProduto;
import br.com.api.restaurante.repository.PedidoRepository;

@RestController
@RequestMapping("/dashboard")
@PreAuthorize("hasRole('VISUALIZAR_DASHBOARD')")	
public class DashBoardController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping("/venda-media-mes-atual")
	public BigDecimal vendaMediaMesAtual() {		
		return pedidoRepository.vendaMediaMesAtual();
	}
	
	@GetMapping("/ranking-produtos-mais-vendidos-mes-atual")
	public List<RankingProduto> rankingProdutosMaisVendidosMesAtual() {		
		return pedidoRepository.rankingProdutosMaisVendidosMesAtual();
	}
	
	@GetMapping("/venda-total-mes-atual")
	public BigDecimal vendaTotalMesAtual() {		
		return pedidoRepository.totalVendasMesAtual(LocalDate.now());
	}

}
