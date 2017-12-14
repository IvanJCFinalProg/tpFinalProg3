package cal.itext.service;

import java.io.File;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Tache extends TimerTask {
	private static Logger logger = LogManager.getLogger(Tache.class.getName());
	public static final String PATH = "C:\\PDFTestJava/"+System.currentTimeMillis();
	public void run() {
		try {
			String[] tableau = {".txt",".csc",".xml"};
			int numRandom = (int)(Math.random()*tableau.length);
			new File(PATH+tableau[numRandom]).createNewFile();
			logger.info("Fichier créée : "+ new File(PATH+tableau[numRandom]).exists());
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
	}
	
}
