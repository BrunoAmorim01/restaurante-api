package br.com.api.restaurante.storage;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface ArquivoStorage {
	public String salvar(MultipartFile [] files);	
	public byte[] recuperar(String imagem);
	public void excluir(String imagem);
	public String getUrl(String imagem);
	
	default String renomearArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;		
		
	}
}
