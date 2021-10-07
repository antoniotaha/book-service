package br.com.taha.bookservice.proxy;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.taha.bookservice.response.Cambio;

@FeignClient(name="cambio-service")
public interface CambioProxy {

	@GetMapping("/cambio-service/{amount}/{from}/{to}")
	public Cambio getCambio(@PathVariable(value = "amount") Double amount, @PathVariable("from") String from, @PathVariable("to") String to);

}
