
package br.com.reboucas.electricapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.reboucas.electricapp.domain.Lampada;
import br.com.reboucas.electricapp.repository.LampadaRepository;
import br.com.reboucas.electricapp.serial.SerialRxTx;

@Service
public class LampadaService {
	
	@Autowired
	private LampadaRepository lampadaRepository;
	
	public Lampada acionaLampada(Lampada lamp) {
		SerialRxTx.sendData(lamp.getId().toString());
		return buscar(lamp.getId());
	}
	
	public Lampada salvar(Lampada lampada) {
		lampada.setId(null);
		return lampadaRepository.save(lampada);
	}
	
	public List<Lampada> listar() {
		List<Lampada> lampadas = lampadaRepository.findAll();
		if (lampadas.isEmpty()) {
			for(int i = 1; i <= 6; i++) {
				Lampada lamp = new Lampada();
				lamp.setValor(String.valueOf(i) + "desligada");
				lampadas.add(salvar(lamp));
			}		
		}
		return lampadas;
	}
	
	public void atualizar(Lampada lamp) {
		lampadaRepository.save(lamp);
	}
	
	public Lampada buscar(Long id){
		return lampadaRepository.findOne(id);
	}
}
