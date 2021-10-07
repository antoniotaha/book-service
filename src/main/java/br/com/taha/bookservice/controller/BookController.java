package br.com.taha.bookservice.controller;

import java.util.HashMap;

import lombok.AllArgsConstructor;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.taha.bookservice.model.Book;
import br.com.taha.bookservice.proxy.CambioProxy;
import br.com.taha.bookservice.repository.BookRepository;
import br.com.taha.bookservice.response.Cambio;

@RestController
@RequestMapping("book-service")
@AllArgsConstructor
public class BookController {

	private final Environment environment;

	private final BookRepository bookRepository;

	private final CambioProxy cambioProxy;

	@GetMapping("{bookId}/{currency}")
	public Book show(@PathVariable("bookId") Long bookId, @PathVariable("currency") String currency) {
		var book = bookRepository.getById(bookId);
		if(book == null) {
			throw new RuntimeException("Entity not found");
		}

		var response = this.cambioProxy.getCambio(book.getPrice(), "USD", currency);
		var port = environment.getProperty("local.server.port");
		book.setEnvironment("Book port:" + port + " Cambio port: " + response.getEnvironment());
		book.setPrice(response.getConvertedValue());
		return book;
	}

// Request com RestTemplate para microservice Cambio
//	@GetMapping("{bookId}/{currency}")
//	public Book show(@PathVariable("bookId") Long bookId, @PathVariable("currency") String currency) {
//		var book = bookRepository.getById(bookId);
//		if(book == null) {
//			throw new RuntimeException("Entity not found");
//		}
//
//		HashMap<String, String> params = new HashMap<>();
//		params.put("amount", book.getPrice().toString());
//		params.put("from", "USD");
//		params.put("to", currency);
//
//		var response = new RestTemplate()
//				.getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params);
//
//		var port = environment.getProperty("local.server.port");
//		book.setEnvironment(port);
//		book.setPrice(response.getBody().getConvertedValue());
//		return book;
//	}

}
