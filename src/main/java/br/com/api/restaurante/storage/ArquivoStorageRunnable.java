package br.com.api.restaurante.storage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.api.restaurante.dto.ArquivoDTO;

public class ArquivoStorageRunnable implements Runnable{
	
	private MultipartFile[] files;
	private DeferredResult<ArquivoDTO> result;
	private ArquivoStorage arquivoStorage;
	
	public ArquivoStorageRunnable(MultipartFile[] files, DeferredResult<ArquivoDTO> result,
			ArquivoStorage arquivoStorage) {
		
		this.files = files;
		this.result = result;
		this.arquivoStorage = arquivoStorage;
		
		result.onError((Throwable t) ->{
			result.setErrorResult(
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(t)
					);
		});
		
		result.onTimeout(()->{
			result.setErrorResult(
			ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build()
			);
			System.out.println("Timeout");
		});
		
		result.onCompletion(()->{
			System.out.println("Completo");
			
		});
	}



	@Override
	public void run() {		
		
		String nomeFoto = arquivoStorage.salvar(files);
		String contentType = files[0].getContentType();
		result.setResult(new ArquivoDTO(nomeFoto, contentType,arquivoStorage.getUrl(nomeFoto)));
		
	}

}
