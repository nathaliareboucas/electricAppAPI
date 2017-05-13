package br.com.reboucas.electricapp.domain;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.reboucas.electricapp.util.JsonDateSerializer;

@Entity
public class Leitura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@NotNull
	@JsonFormat(pattern="dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date ultimaLeitura;
		
	@JsonFormat(pattern="dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date proximaLeitura;

	private BigDecimal valorUltimaLeitura;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getUltimaLeitura() {		
		return this.ultimaLeitura;
	}

	public void setUltimaLeitura(Date ultimaLeitura) {
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
		TimeZone.setDefault(tz);
		Calendar ca = GregorianCalendar.getInstance(tz);
		ca.setTime(ultimaLeitura);	
//		ca.add(Calendar.DATE, +1);
		this.ultimaLeitura = ca.getTime();
		setProximaLeitura(this.ultimaLeitura);
	}

	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getProximaLeitura() {
		return this.proximaLeitura;
	}

	public void setProximaLeitura(Date ultimaLeitura) {
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
		TimeZone.setDefault(tz);
		Calendar ca = GregorianCalendar.getInstance(tz);
		ca.setTime(ultimaLeitura);
		
		if (verificaFevereiro(ca) && verificaAnoBisexto(ca)) {
			ca.add(Calendar.DATE, +28);
		} else {
			ca.add(Calendar.DATE, +30);
		}
		
		this.proximaLeitura = ca.getTime();
	}
	
	public BigDecimal getValorUltimaLeitura() {
		return valorUltimaLeitura;
	}

	public void setValorUltimaLeitura(BigDecimal valorUltimaLeitura) {
		this.valorUltimaLeitura = valorUltimaLeitura;
	}
	
	public boolean verificaFevereiro(Calendar data) {
		int mes = data.get(Calendar.MONTH); 
		return mes == 2;
	}
	
	public boolean verificaAnoBisexto(Calendar data) {
		int ano = data.get(Calendar.YEAR);
		if(ano % 400 == 0){
            return true;
        // se o ano for menor que 400
        } else if((ano % 4 == 0) && (ano % 100 != 0)){
        	return true;
        } else{
            return false;
        }
	}

}
