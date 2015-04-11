package com.Axandar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	public static int temp;
	
	
	public static void main(String[] args) throws Exception{
		while(true){
			try{
				temp = Temperature();
				System.out.println(temp);
				if(temp>28000){
					System.out.println("Turning Off");
					TurnOff();
				}
				Thread.sleep(5000);
			}catch(InterruptedException e){	}
		}

	}
	public static int Temperature() throws IOException{
		String s = null;
		int output = 0;
		try{
			String[] command = { "cat", "/sys/devices/platform/sunxi-i2c.0/i2c-0/0-0034/temp1_input"}; 
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(        
			process.getInputStream()));
			s = reader.readLine();
			output = Integer.parseInt(s);
		}catch(Exception e){
			e.printStackTrace();
		}
		return output;
	}
	public static void TurnOff(){
		try{
			String[] command = { "shutdown", "-h", "now"}; 
			@SuppressWarnings("unused")
			Process process = Runtime.getRuntime().exec(command);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
