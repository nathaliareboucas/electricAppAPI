package br.com.reboucas.electricapp.services;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

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
		BigDecimal consumoMes;
		
		while (hoje.after(ultimaLeitura.getProximaLeitura())) {
			if (ultimaLeitura.getId() != 1) {				
				Leitura leituraAnterior = buscar(ultimaLeitura.getId()-1);
				consumoMes = consumoService.consumoPorMes(leituraAnterior.getUltimaLeitura(), leituraAnterior.getProximaLeitura());
				ultimaLeitura.setValorUltimaLeitura(leituraAnterior.getValorUltimaLeitura()
						.add(consumoMes));
				
				atualizar(ultimaLeitura);
			}
			
			Leitura leitura = new Leitura();
			TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
			TimeZone.setDefault(tz);
			Calendar ca = GregorianCalendar.getInstance(tz);
			ca.setTime(ultimaLeitura.getProximaLeitura());
			leitura.setUltimaLeitura(ca.getTime());
			
			consumoMes = consumoService.consumoPorMes(ultimaLeitura.getUltimaLeitura(), ultimaLeitura.getProximaLeitura());
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
