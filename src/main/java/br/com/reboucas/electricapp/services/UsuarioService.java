package br.com.reboucas.electricapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.reboucas.electricapp.domain.Usuario;
import br.com.reboucas.electricapp.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario salvar(Usuario usuario) {
		usuario.setId(null);
		return usuarioRepository.save(usuario);
	}
	
	public String autenticar(Usuario usu) {
		Usuario usuario = buscar(usu.getId());
		
		if (usu.getSenha().equalsIgnoreCase(usuario.getSenha())) {
			return "Autenticado";
		} else {
			return "Senha inválida";
		}
	}
	
	public Usuario buscar(Long id) {
		return usuarioRepository.findOne(id);
	}
	
/*	public String alterarSenha(String novaSenha) {
		Long id = new Long(1);
		Usuario usu = buscar(id);
		
		usu.setSenha(novaSenha);
		usu = usuarioRepository.save(usu);
		return usu.getSenha();
	}*/
}
