package br.com.api.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.api.restaurante.dto.ArquivoDTO;
import br.com.api.restaurante.storage.ArquivoStorage;
import br.com.api.restaurante.storage.ArquivoStorageRunnable;

@RestController
@RequestMapping("/arquivos")
public class ArquivoController {

	@Autowired
	private ArquivoStorage arquivoStorage;
	// private String nomeFoto;
	// private String contentType;

	@PostMapping
	public DeferredResult<ArquivoDTO> uploadImagem(@RequestParam("file") MultipartFile[] files) {

		DeferredResult<ArquivoDTO> result = new DeferredResult<>(15000l);

		Thread thread = new Thread(new ArquivoStorageRunnable(files, result, arquivoStorage));

		thread.start();

		return result;

	}

	// @GetMapping("/{nome}")
	@GetMapping(value = "/{nome}")
	public ResponseEntity<byte[]> visualizarImagem(@PathVariable("nome") String nomeImagem) {
		System.out.println(nomeImagem);
		byte[] arquivo = arquivoStorage.recuperar(nomeImagem);
		/*
		 * ArquivoDTO arquivoDTO =new ArquivoDTO(); Resource r = new
		 * ByteArrayResource(arquivo); byte[] arquivo2=
		 * Base64.getEncoder().encode(arquivo); arquivoDTO.setNome(nomeImagem);
		 * arquivoDTO.setImagem(arquivo);
		 * 
		 * //return arquivoDTO;
		 */
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(arquivo);
	}

	@DeleteMapping("/{nome}")
	@ResponseStatus(code = HttpStatus.OK)
	public void excluirImagem(@PathVariable("nome") String nomeImagem) {

		arquivoStorage.excluir(nomeImagem);

	}

}
