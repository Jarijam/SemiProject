 package com.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SerialArduinoLEDControl {
	OutputStream out;
	InputStream in;
	public void connect(String portName) {
		try {
			CommPortIdentifier comportidentifier = CommPortIdentifier.getPortIdentifier(portName);
			if(comportidentifier.isCurrentlyOwned()) {
				System.out.println("포트 사용불가");
			}else {
				System.out.println("포트 사용가능");
				CommPort commport = comportidentifier.open("basic_serial", 3000);
				System.out.println(commport);
				
				if(commport instanceof SerialPort) {
					SerialPort serialport = (SerialPort)commport;
					serialport.setSerialPortParams(
							9600,//통신 속도
							SerialPort.DATABITS_8,//데이터 길이
							SerialPort.STOPBITS_1,//stop bit
							SerialPort.PARITY_NONE); //오류검출
							in = serialport.getInputStream();
							out = serialport.getOutputStream();
					
				}
			}
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public OutputStream getOutput() {
		return out;
	}
	
	public InputStream getInput() {
		return in;
	}
}
