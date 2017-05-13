package br.com.reboucas.electricapp.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.reboucas.electricapp.domain.Consumo;
import br.com.reboucas.electricapp.domain.Leitura;
import br.com.reboucas.electricapp.repository.ConsumoRepository;

@Service
public class ConsumoService {

	@Autowired
	private ConsumoRepository consumoRepository;
	@Autowired
	private LeituraService leituraService;

	public BigDecimal consumoTotal() {
		return consumoRepository.consumoTotal();
	}

	public Consumo salvar(Consumo consumo) {
		consumo.setId(null);
		return consumoRepository.save(consumo);
	}

	public BigDecimal consumoPorMes(Date ultimaLeitura, Date proximaLeitura) {
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
		TimeZone.setDefault(tz);
		Calendar ca = GregorianCalendar.getInstance(tz);
		ca.setTime(proximaLeitura);
		ca.add(Calendar.DATE, -1);
		proximaLeitura = ca.getTime();
		
		List<Consumo> consumos = consumoRepository.findByDataBetween(ultimaLeitura, 
				proximaLeitura);
		BigDecimal consumoMes = new BigDecimal("0.00");

		for (Consumo c : consumos) {
			consumoMes = consumoMes.add(c.getValor());
		}
		return consumoMes;
	}
	
	public List<Leitura> leituraConsumoMes() {
		List<Leitura> leituras = leituraService.listar();		
		List<Leitura> leiturasEnviar = new ArrayList<>();
		BigDecimal consumoMes;
		
		for (int i = leituras.size()-1; i > 0; i--) {
			consumoMes = leituras.get(i).getValorUltimaLeitura()
					.subtract(leituras.get(i-1).getValorUltimaLeitura());
			
			leituras.get(i).setValorUltimaLeitura(consumoMes);
			leiturasEnviar.add(leituras.get(i));
		}
		
		leituras.get(0).setValorUltimaLeitura(consumoPorMes(leituras.get(0).getUltimaLeitura(), 
				leituras.get(0).getProximaLeitura()));
		
		leiturasEnviar.add(leituras.get(0));
		return leiturasEnviar;
	}

	public Consumo getConsumo(Long id) {
		return consumoRepository.findOne(id);
	}
	
	public void atualizar(Consumo consumo) {
		consumoRepository.save(consumo);
	} 

}
