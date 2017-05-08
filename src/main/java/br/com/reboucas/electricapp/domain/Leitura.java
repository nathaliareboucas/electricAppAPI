package br.com.reboucas.electricapp.domain;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
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
		Calendar date = Calendar.getInstance();
		date.setTime(ultimaLeitura);	
		date.add(Calendar.DATE, +1);
		this.ultimaLeitura = date.getTime();
		setProximaLeitura(this.ultimaLeitura);
	}

	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getProximaLeitura() {
		return this.proximaLeitura;
	}

	public void setProximaLeitura(Date ultimaLeitura) {
		Calendar date = Calendar.getInstance();
		date.setTime(ultimaLeitura);
		date.add(Calendar.DATE, +31);
		this.proximaLeitura = date.getTime();
	}

	public BigDecimal getValorUltimaLeitura() {
		return valorUltimaLeitura;
	}

	public void setValorUltimaLeitura(BigDecimal valorUltimaLeitura) {
		this.valorUltimaLeitura = valorUltimaLeitura;
	}

}
