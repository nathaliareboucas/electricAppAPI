package br.com.reboucas.electricapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.com.reboucas.electricapp.domain.Leitura;

public interface LeituraRepository extends JpaRepository<Leitura, Long>{
	
	@Query(value="SELECT * FROM leitura ORDER BY id DESC LIMIT 1;", nativeQuery= true)
	public Leitura ultimaLeitura();

}
