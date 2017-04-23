package br.com.reboucas.electricapp.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.com.reboucas.electricapp.domain.Consumo;

public interface ConsumoRepository extends JpaRepository<Consumo, Long>{
	
	@Query(value = "SELECT SUM(valor) AS consumoTotal FROM consumo", nativeQuery= true)
	public BigDecimal consumoTotal();
	
	public List<Consumo> findByDataBetween(Date inicio, Date fim);

}
