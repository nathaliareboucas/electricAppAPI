package br.com.reboucas.electricapp.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.reboucas.electricapp.domain.Leitura;
import br.com.reboucas.electricapp.services.LeituraService;

@RestController
@RequestMapping("/leitura")
public class LeituraResources {
	
	@Autowired
	private LeituraService leituraService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody Leitura leitura) {
		leitura = leituraService.salvar(leitura);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(leitura.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/ultima", method = RequestMethod.GET)
	public ResponseEntity<Leitura> ultimaLeitura() {
		return ResponseEntity.status(HttpStatus.OK).body(leituraService.ultimaLeitura());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Leitura>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(leituraService.listar());
	}
	
	@RequestMapping(value = "/medicao", method = RequestMethod.GET)
	public ResponseEntity<Leitura> getMedicao() {
		return ResponseEntity.status(HttpStatus.OK).body(leituraService.getMedicao());
	}

}
