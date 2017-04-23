package br.com.reboucas.electricapp.serial;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SerialRxTx implements SerialPortEventListener {

	static SerialPort serialPort = null;
	private Protocolo protocolo = new Protocolo();   // objeto de gestão de protocolo
	private String appName = "Teste"; 				// nome da aplicação
	private BufferedReader input; 				    // objeto para leitura na serial
	private static OutputStream output; 				   // objeto para escrita na serial
	private static final int TIME_OUT = 1000;      // tempo de espera de comunicação
	private static int DATA_RATE = 9600;          // velocidade da comunicação
	private String serialPortName = "COM7";      //porta serial

	public boolean iniciaSerial() {
		boolean status = false;

		try {
			// obtem portas seriais do sistema
			CommPortIdentifier portId = null;
			Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

			while (portId == null && portEnum.hasMoreElements()) {
				CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

				if (currPortId.getName().equals(serialPortName) || currPortId.getName().startsWith(serialPortName)) {
					serialPort = (SerialPort) currPortId.open(appName, TIME_OUT);
					portId = currPortId;
					System.out.println("Conectado em: " + currPortId.getName());
					break;
				}
			}

			if (portId == null || serialPort == null) {
				return false;
			}

			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			status = true;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	// Método que envia dado pela serial
	public static void sendData(String data) {
		try {
			output = serialPort.getOutputStream();
			output.write(data.getBytes());
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	// Método que fecha a porta serial
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	// Método que lida com a chegada de dados pela porta serial do computador
	public void serialEvent(SerialPortEvent spe) {
		try {
			switch (spe.getEventType()) {
			case SerialPortEvent.DATA_AVAILABLE:
				if (input == null) {
					input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
				}

				if (input.ready()) {
					protocolo.setLeituraComando(input.readLine()); // recebe valor consumo e atribui a leituraComando do protocolo
					sendData(String.valueOf(protocolo.getConsumoTotal())); // envia o consumo total em kW
				}

				break;

			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Protocolo getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}

	public static int getDATA_RATE() {
		return DATA_RATE;
	}

	public static void setDATA_RATE(int dATA_RATE) {
		DATA_RATE = dATA_RATE;
	}

	public String getSerialPortName() {
		return serialPortName;
	}

	public void setSerialPortName(String serialPortName) {
		this.serialPortName = serialPortName;
	}

}
