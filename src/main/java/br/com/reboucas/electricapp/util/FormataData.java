package br.com.reboucas.electricapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormataData {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
	String strData;
	
	public Date getDataAtual() {		
		Date data = new Date(System.currentTimeMillis());
		strData = sdf.format(data);
		try {
			return data = sdf.parse(strData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Date dataFormatada(Date data) {
		strData = sdf.format(data);
		try {
			return data = sdf.parse(strData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
