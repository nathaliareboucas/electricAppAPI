package br.com.reboucas.electricapp.resources;

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
import br.com.reboucas.electricapp.domain.Usuario;
import br.com.reboucas.electricapp.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioResources {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody Usuario usuario) {
		usuario = usuarioService.salvar(usuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(usuario.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Usuario usuario = usuarioService.buscar(id);
		return ResponseEntity.status(HttpStatus.OK).body(usuario);
	}
	
	@RequestMapping(value = "/autenticar", method = RequestMethod.POST)
	public ResponseEntity<String> autenticar(@Valid @RequestBody Usuario usu) {
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.autenticar(usu));
	}
	
/*	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<String> alterarSenha(@Valid @RequestBody String senha) {
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.alterarSenha(senha));
	}*/

}
