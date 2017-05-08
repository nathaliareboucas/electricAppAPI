package br.com.reboucas.electricapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import br.com.reboucas.electricapp.serial.SerialRxTx;

@SpringBootApplication
public class ElectricappapiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {		
		SpringApplication.run(ElectricappapiApplication.class, args);	
		SerialRxTx serial = new SerialRxTx();
		serial.iniciaSerial();
	}
}
