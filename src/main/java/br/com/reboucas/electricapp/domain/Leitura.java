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
	
	@NotNull
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
		return ultimaLeitura;
	}

	public void setUltimaLeitura(Date ultimaLeitura) {
		Calendar data = Calendar.getInstance();
		data.setTime(ultimaLeitura);
		data.add(Calendar.DATE, +1);
		this.ultimaLeitura = data.getTime();
		setProximaLeitura(this.ultimaLeitura);
	}

	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getProximaLeitura() {
		return proximaLeitura;
	}

	public void setProximaLeitura(Date ultimaLeitura) {
		Calendar aux = Calendar.getInstance();
		aux.setTime(ultimaLeitura);
		aux.add(Calendar.DATE, +31);
		this.proximaLeitura = aux.getTime();
	}

	public BigDecimal getValorUltimaLeitura() {
		return valorUltimaLeitura;
	}

	public void setValorUltimaLeitura(BigDecimal valorUltimaLeitura) {
		this.valorUltimaLeitura = valorUltimaLeitura;
	}

}
