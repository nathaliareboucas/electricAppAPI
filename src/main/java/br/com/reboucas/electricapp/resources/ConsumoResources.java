package br.com.reboucas.electricapp.resources;

import java.math.BigDecimal;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.reboucas.electricapp.domain.Consumo;
import br.com.reboucas.electricapp.domain.Leitura;
import br.com.reboucas.electricapp.services.ConsumoService;

@RestController
@RequestMapping("/consumo")
public class ConsumoResources {

	@Autowired
	private ConsumoService consumoService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody Consumo consumo) {
		consumo = consumoService.salvar(consumo);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(consumo.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
		
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<BigDecimal> consumoTotal() {
		return ResponseEntity.status(HttpStatus.OK).body(consumoService.consumoTotal());
	}
	
	@RequestMapping(value = "/mensal", method = RequestMethod.POST)
	public ResponseEntity<BigDecimal> consumoPorMes(@Valid @RequestBody Leitura leitura) {		
		return ResponseEntity.status(HttpStatus.OK).
				body(consumoService.consumoPorMes(leitura.getUltimaLeitura(),
						leitura.getProximaLeitura()));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Consumo> getConsumo(@PathVariable("id") Long id ) {
		return ResponseEntity.status(HttpStatus.OK).body(consumoService.getConsumo(id));
	}
}
