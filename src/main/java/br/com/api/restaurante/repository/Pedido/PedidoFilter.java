package br.com.api.restaurante.repository.Pedido;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.api.restaurante.model.StatusPedido;

public class PedidoFilter {
	
	
	@DateTimeFormat(pattern =  "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataInicio;
	
	@DateTimeFormat(pattern =  "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataFim;
	
	private List<StatusPedido> statusPedido;

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDateTime getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}

	public List<StatusPedido> getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(List<StatusPedido> statusPedido) {
		this.statusPedido = statusPedido;
	}

	

	

	

}
