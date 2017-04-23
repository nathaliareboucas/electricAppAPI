package br.com.reboucas.electricapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.reboucas.electricapp.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
