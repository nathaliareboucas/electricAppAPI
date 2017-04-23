package br.com.reboucas.electricapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.reboucas.electricapp.domain.Leitura;
import br.com.reboucas.electricapp.repository.LeituraRepository;

@Service
public class LeituraService {
	
	@Autowired
	private LeituraRepository leituraRepository;
	
	public Leitura salvar(Leitura leitura) {
		leitura.setId(null);
		return leituraRepository.save(leitura);
	}
	
	public Leitura utimaLeitura(){		
		return leituraRepository.ultimaLeitura();
	}

}
