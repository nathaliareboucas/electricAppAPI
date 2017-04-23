package br.com.reboucas.electricapp.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.reboucas.electricapp.domain.Consumo;
import br.com.reboucas.electricapp.repository.ConsumoRepository;

@Service
public class ConsumoService {

	@Autowired
	private ConsumoRepository consumoRepository;

	public BigDecimal consumoTotal() {
		return consumoRepository.consumoTotal();
	}

	public Consumo salvar(Consumo consumo) {
		consumo.setId(null);
		return consumoRepository.save(consumo);
	}

	public BigDecimal consumoPorMes(Date ultimaLeitura, Date proximaLeitura) {
		List<Consumo> consumos = consumoRepository.findByDataBetween(ultimaLeitura, 
				proximaLeitura);
		BigDecimal consumoMes = new BigDecimal("0.00");

		for (Consumo c : consumos) {
			consumoMes = consumoMes.add(c.getValor());
		}
		return consumoMes;
	}

	public Consumo getConsumo(Long id) {
		return consumoRepository.findOne(id);
	}

}
