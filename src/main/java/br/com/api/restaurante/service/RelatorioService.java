package br.com.api.restaurante.service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class RelatorioService {
	@Autowired
	private DataSource dataSource;
	
	public byte[] listarPedido(LocalDateTime inicio, LocalDateTime fim) throws JRException, SQLException {
		Map<String, Object> parametros = new HashMap<>();		
		parametros.put("DATA_INICIO", Timestamp.valueOf(inicio));
		parametros.put("DATA_FIM", Timestamp.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/listar-pedidos.jasper");
		
		Connection con = this.dataSource.getConnection();		
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,con);		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
}
