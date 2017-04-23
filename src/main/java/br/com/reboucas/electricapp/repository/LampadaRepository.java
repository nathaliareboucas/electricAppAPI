package br.com.reboucas.electricapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.reboucas.electricapp.domain.Lampada;

public interface LampadaRepository extends JpaRepository<Lampada, Long>{

}
