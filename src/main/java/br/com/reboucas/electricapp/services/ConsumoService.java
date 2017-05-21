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
		
		for (int i = leituras.size()-2; i >= 0; i--) {
			consumoMes = consumoPorMes(leituras.get(i).getUltimaLeitura(), leituras.get(i).getProximaLeitura());
			leituras.get(i).setValorUltimaLeitura(consumoMes);
			leiturasEnviar.add(leituras.get(i));
		}
		return leiturasEnviar;
	}
	
	public List<Consumo> listaConsumoPorMes(Date dataInicio, Date dataFim) {
		return consumoRepository.findByDataBetween(dataInicio, dataFim);
	}
	
	public Consumo ultimoConsumo() {
		return consumoRepository.ultimoConsumo();
	}

	public Consumo getConsumo(Long id) {
		return consumoRepository.findOne(id);
	}
	
	public void atualizar(Consumo consumo) {
		consumoRepository.save(consumo);
	} 

}
