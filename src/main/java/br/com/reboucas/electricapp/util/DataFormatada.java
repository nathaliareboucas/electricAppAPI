package br.com.reboucas.electricapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormatada {
	
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	public String dateToString(Date data) {			
		return df.format(data);
	}
	
	public Date stringToDate(String strData) {			
		try {
			Date dataFormatada = df.parse(strData);			
			return dataFormatada;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
