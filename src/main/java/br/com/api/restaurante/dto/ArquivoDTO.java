package br.com.api.restaurante.dto;

public class ArquivoDTO {
	private String nome;
	private String contentType;
	private String url;
	private byte[] imagem;

	public ArquivoDTO(String nome, String contentType, String url) {		
		this.nome = nome;
		this.contentType = contentType;
		this.url = url;
	}
	public ArquivoDTO() {
	
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
}
