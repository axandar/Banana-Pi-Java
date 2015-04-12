package com.Axandar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class BlinkTest{
	
	public static void main(String[] args) throws Exception{
		
		/**if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
            return;
        }
		
		Gpio.pinMode(7, Gpio.OUTPUT);
		
		for(int n = 0; n < 10; n++){
			Gpio.digitalWrite(1, 1);
			System.out.println("Pin 1 wartosc 1");
			Gpio.delay(1000);
			Gpio.digitalWrite(1, 0);
			System.out.println("Pin 1 wartosc 0");
			Gpio.delay(1000);
		}**/
		
		/**LcdDisplay lcd = new LcdDisplay();

		lcd.I2CLcdDisplay(2, 16, 2, 0x38, 7, 6, 5, 4, 3, 2, 1, 0);**/
		
		/**Sftp sftp = new Sftp();
		
		sftp.connect("192.168.2.104", "bananapi", "bananapi", 22);
		sftp.channelsftp(null);
		sftp.send("D:/loremipsum.txt");
		sftp.download("loremipsum.txt", "D:/loremipsum2.txt");
		sftp.close_connection();**/
		
		String ilosc_miejsca = DiskSpace();
		System.out.println(ilosc_miejsca);

		return;
	}
	
	public static String DiskSpace() throws IOException{
		String s = null;
		String output = null;
		int where_m = 0;
		try{
			String[] command = { "du", "-hs", "/home" };         
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(        
			process.getInputStream()));
			s = reader.readLine();
			where_m = s.indexOf("M");
			output = s.substring(0, where_m);
		}catch(Exception e){
			e.printStackTrace();
		}
		return output;
	}
}
