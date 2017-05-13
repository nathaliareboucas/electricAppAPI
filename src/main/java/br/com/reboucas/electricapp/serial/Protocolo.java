package br.com.reboucas.electricapp.serial;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.reboucas.electricapp.config.ApplicationContextHolder;
import br.com.reboucas.electricapp.domain.Consumo;
import br.com.reboucas.electricapp.domain.Lampada;
import br.com.reboucas.electricapp.services.ConsumoService;
import br.com.reboucas.electricapp.services.LampadaService;

@Service
public class Protocolo {
	
	private Consumo consumo;
	private Lampada lampada;
	private String leituraComando;
	private BigDecimal consumoTotal;
	private ConsumoService consumoService;	
	private LampadaService lampadaService;

	public Protocolo() {
		consumoService = ApplicationContextHolder.getContext().getBean(ConsumoService.class);
		lampadaService = ApplicationContextHolder.getContext().getBean(LampadaService.class);
	}

	private void interpretaComando() {
		// Separa a string de comando em substrings delimitadas por um caractere específico
		String[] aux = leituraComando.split(",");		
		
		lampada = new Lampada();	
		if (aux[0].equals("1ligada") || aux[0].equals("1desligada")) {
			lampada.setId(new Long(1));
			lampada.setValor(aux[0]);
			lampadaService.atualizar(lampada);
			System.out.println(lampada.getValor());
		}
		else if (aux[0].equals("2ligada") || aux[0].equals("2desligada")) {
			lampada.setId(new Long(2));
			lampada.setValor(aux[0]);
			lampadaService.atualizar(lampada);
			System.out.println(lampada.getValor());
		}
		else if (aux[0].equals("3ligada") || aux[0].equals("3desligada")) {
			lampada.setId(new Long(3));
			lampada.setValor(aux[0]);
			lampadaService.atualizar(lampada);
			System.out.println(lampada.getValor());
		}
		else if (aux[0].equals("4ligada") || aux[0].equals("4desligada")) {
			lampada.setId(new Long(4));
			lampada.setValor(aux[0]);
			lampadaService.atualizar(lampada);
			System.out.println(lampada.getValor());
		}
		else if (	aux[0].equals("5ligada") || aux[0].equals("5desligada")) {
			lampada.setId(new Long(5));
			lampada.setValor(aux[0]);
			lampadaService.atualizar(lampada);
			System.out.println(lampada.getValor());
		}
		else if (aux[0].equals("6ligada") || aux[0].equals("6desligada")) {
			lampada.setId(new Long(6));
			lampada.setValor(aux[0]);
			lampadaService.atualizar(lampada);
			System.out.println(lampada.getValor());
		}			
		else {
			System.out.println("Chegou: " + aux[0]);
			
			consumo = new Consumo();
			consumo.setValor(new BigDecimal(aux[0])); // consumo em watts
			consumoService.salvar(consumo); // salva consumo no banco em watts
			//consumoTotal = consumoService.consumoTotal(); //consumo total em W
			
		}
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
