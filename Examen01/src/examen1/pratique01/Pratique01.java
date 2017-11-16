package examen1.pratique01;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Pratique01 {
	public static void main(String[] args) throws Exception{
		BufferedWriter fdout = null;
		String extension = "";
		try {
			switch((int)(Math.random()*3)) {
				case 0: extension = ".txt";
				break;
				case 1: extension = ".csv";
				break;
				default: extension = ".xml";
			}
			System.out.println(extension);
			fdout= new BufferedWriter(new FileWriter(System.currentTimeMillis() + extension));
			fdout.close();
		}catch(Exception e) {e.printStackTrace();}
	}
}
