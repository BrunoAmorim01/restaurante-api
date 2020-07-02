package br.com.api.restaurante.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.api.restaurante.model.ItemPedido;
import br.com.api.restaurante.model.Pedido;
import br.com.api.restaurante.model.Produto;
import br.com.api.restaurante.storage.ArquivoStorage;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;
	

	@Autowired
	private ArquivoStorage arquivoStorage;

	public void enviarPedidoCliente(Pedido pedido) {
		Context context = new Context(new Locale("pt", "BR"));
		context.setVariable("pedido", pedido);
		context.setVariable("logo", "logo");

		String email = pedido.getCliente().getEmail();

		Map<String, String> imagens = new HashMap<>();

		for (ItemPedido itemPedido : pedido.getItens()) {
			Produto produto = itemPedido.getProduto();
			if (produto.temImagem()) {
				String cid = "imagem-" + produto.getId();
				context.setVariable(cid, cid);
				imagens.put(cid, produto.getNomeArquivo() + "|" + "image/png");
			}

		}
		String mensagem = templateEngine.process("mail/pedido", context);

		enviarEmailPedido("naoresponda@restaurante.com", email, "Pedido restaurante", mensagem, imagens);
	}

	private void enviarEmailPedido(String remetente, String destinatario, String assunto, String mensagem, Map<String, String> imagens) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setFrom(remetente);
			helper.setTo(destinatario);
			helper.setSubject(assunto);
			helper.setText(mensagem, true);
			helper.addInline("logo", new ClassPathResource("static/images/logo.png"), "image/png");
			
			for (String cid : imagens.keySet()) {
				String[] imgContentType = imagens.get(cid).split("\\|");
				String img = imgContentType[0];
				String contentType = imgContentType[1];
				
				byte[] arrayImg = arquivoStorage.recuperar(img);
				helper.addInline(cid, new ByteArrayResource(arrayImg),contentType);

			}

			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			throw new RuntimeException("Problemas com o envio de e-mail!", e);
		}
	}

	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String template,
			Map<String, Object> variaveis) {

		Context context = new Context(new Locale("pt", "BR"));
		context.setVariable("logo", "logo");
		variaveis.entrySet().forEach(e -> context.setVariable(e.getKey(), e.getValue()));

		String mensagem = templateEngine.process(template, context);
		this.enviarEmail(remetente, destinatarios, assunto, mensagem);

	}

	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mensagem, true);
			helper.addInline("logo", new ClassPathResource("static/images/logo.png"), "image/png");

			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			throw new RuntimeException("Problemas com o envio de e-mail!", e);
		}

	}

}
