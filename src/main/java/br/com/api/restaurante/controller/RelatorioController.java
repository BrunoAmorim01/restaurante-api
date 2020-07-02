package br.com.api.restaurante.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.restaurante.service.RelatorioService;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

	@Autowired
	private RelatorioService relatorioService;
	
	@PreAuthorize("hasRole('EMITIR_RELATORIO_PEDIDO')")	
	@GetMapping("/listar-pedidos")
	public ResponseEntity<byte[]> listaPedidos(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime inicio,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fim)
			throws JRException, SQLException {

		byte[] relatorio = relatorioService.listarPedido(inicio, fim);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);

	}

}
