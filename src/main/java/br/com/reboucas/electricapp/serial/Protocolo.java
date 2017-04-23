package br.com.reboucas.electricapp.serial;

import java.math.BigDecimal;

import br.com.reboucas.electricapp.config.ApplicationContextHolder;
import br.com.reboucas.electricapp.domain.Consumo;
import br.com.reboucas.electricapp.services.ConsumoService;

public class Protocolo {
	
	private Consumo consumo;
	private String leituraComando;
	private BigDecimal consumoTotal;
	
	private ConsumoService consumoService;

	public Protocolo() {
		consumoService = ApplicationContextHolder.getContext().getBean(ConsumoService.class);
	}

	private void interpretaComando() {
		// Separa a string de comando em substrings delimitadas por um caractere específico
		String[] aux = leituraComando.split(",");
		System.out.println("Chegou: " + aux[0]);
		
		consumo = new Consumo();
		consumo.setValor(new BigDecimal(aux[0])); // consumo em watts
		consumoService.salvar(consumo); // salva consumo no banco em watts
		consumoTotal = consumoService.consumoTotal(); //consumo total em W
	}

	public BigDecimal getConsumoTotal() {
		return consumoTotal;
	}

	public String getLeituraComando() {
		return leituraComando;
	}

	public void setLeituraComando(String leituraComando) {
		this.leituraComando = leituraComando; // temos a string de dados
		this.interpretaComando(); // interpreta a string
	}



}
