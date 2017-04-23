package br.com.reboucas.electricapp.resources;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.reboucas.electricapp.domain.Lampada;
import br.com.reboucas.electricapp.services.LampadaService;

@RestController
@RequestMapping("/lampada")
public class LampadaResources {
	
	@Autowired
	private LampadaService lampadaService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Lampada>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(lampadaService.listar());
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Lampada> acionaLampada(@Valid @RequestBody Lampada lamp, 
			@PathVariable("id") Long id) {
		
		lamp.setId(id);		
		return ResponseEntity.status(HttpStatus.OK).body(lampadaService.acionaLampada(lamp));
	}
	

}
