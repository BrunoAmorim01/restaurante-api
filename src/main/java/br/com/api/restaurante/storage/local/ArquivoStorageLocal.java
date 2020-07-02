package br.com.api.restaurante.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.api.restaurante.storage.ArquivoStorage;

//@Profile("development")
@Component
public class ArquivoStorageLocal implements ArquivoStorage {
	private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoStorageLocal.class);

	@Value("${foto-storage-local.local}")
	private Path local;

	@Value("${foto-storage-local.url-base}")
	private String url;

	@PostConstruct
	private void criarpastas() {
		try {
			Files.createDirectories(local);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pastas criadas para salvar fotos");
				LOGGER.debug("Pasta default :" + this.local.toAbsolutePath());
			}
		} catch (IOException e) {

			throw new RuntimeException("Erro ao criar pasta para salvar imagem", e);
		}
	}

	@Override
	public String salvar(MultipartFile[] files) {
		String novoNome = null;
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(local.toAbsolutePath().toString() 
						+ getDefault().getSeparator()+novoNome));
			} 
			 catch (IOException e) {
			
				 throw new RuntimeException("Erro ao salvar a foto", e);
			}

		}
		return novoNome;
	}

	@Override
	public byte[] recuperar(String foto) {
		try {			
			return 	Files.readAllBytes(local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto", e);
		}
		
	}

	@Override
	public void excluir(String foto) {
		try {
			Files.deleteIfExists(local.resolve(foto));
		} catch (IOException e) {
			LOGGER.warn(String.format("Erro apagando foto '%s. Mensagem: %s ", foto, e.getMessage()));
		}

	}

	@Override
	public String getUrl(String foto) {
		
		return url + foto;
	}

}
