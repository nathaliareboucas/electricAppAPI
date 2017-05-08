package br.com.reboucas.electricapp.services;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.reboucas.electricapp.domain.Leitura;
import br.com.reboucas.electricapp.repository.LeituraRepository;
import br.com.reboucas.electricapp.util.FormataData;

@Service
public class LeituraService {
	
	@Autowired
	private LeituraRepository leituraRepository;
	
	@Autowired
	private ConsumoService consumoService;
	
	public Leitura salvar(Leitura leitura) {
		leitura.setId(null);
		return leituraRepository.save(leitura);
	}
	
	public Leitura ultimaLeitura(){		
		return leituraRepository.ultimaLeitura();
	}
	
	public List<Leitura> listar() {
		return leituraRepository.findAll();
	}
	
	public void atualizar(Leitura leitura) {
		leituraRepository.save(leitura);
	} 
	
	public Leitura getMedicao() {
		Leitura ultimaLeitura = ultimaLeitura();
		Date hoje = new FormataData().getDataAtual();
		//BigDecimal consumoTotal;
		BigDecimal consumoMes;
		
		while (hoje.after(ultimaLeitura.getProximaLeitura())) {
			if (ultimaLeitura.getId() != 1) {
				//consumoTotal = consumoService.consumoTotal();
				consumoMes = consumoService.consumoPorMes(ultimaLeitura.getUltimaLeitura(), ultimaLeitura.getProximaLeitura());
				//ultimaLeitura.setValorUltimaLeitura(consumoTotal.subtract(consumoMes));
				Leitura leituraAnterior = buscar(ultimaLeitura.getId()-1);
				ultimaLeitura.setValorUltimaLeitura(leituraAnterior.getValorUltimaLeitura()
						.add(consumoMes));
				
				atualizar(ultimaLeitura);
			}
			
			Leitura leitura = new Leitura();
			Calendar date = Calendar.getInstance();
			date.setTime(ultimaLeitura.getProximaLeitura());
			date.add(Calendar.DATE, -1);
			leitura.setUltimaLeitura(date.getTime());
			
			//consumoTotal = consumoService.consumoTotal();
			consumoMes = consumoService.consumoPorMes(leitura.getUltimaLeitura(), leitura.getProximaLeitura());
			//leitura.setValorUltimaLeitura(consumoTotal.subtract(consumoMes));
			//leitura.setValorUltimaLeitura(consumoTotal);
			leitura.setValorUltimaLeitura(ultimaLeitura.getValorUltimaLeitura()
					.add(consumoMes));
			
			ultimaLeitura = salvar(leitura);
		}
		
		return ultimaLeitura;
	}
	
	public Leitura buscar(Long id) {
		return leituraRepository.findOne(id);
	}
	
}
