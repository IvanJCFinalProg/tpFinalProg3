package cal.tpfinal.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cal.tpfinal.util.IServiceUtils;

public class ServiceApp {
	
	private static Logger logger = LogManager.getLogger(ServiceApp.class);
	
	public static String getValue(String key, int option){
		Properties propertiesFile = new Properties();
		return getPropretiesFile(propertiesFile, option).getProperty(key).toString();
	}
	
	public static void setValue(String key, String value, int option){
		try {
			Properties propertiesFile = new Properties();
			propertiesFile.load(new FileInputStream(IServiceUtils.FILE1));
			propertiesFile.setProperty(key, value);
			propertiesFile.store(new FileOutputStream(IServiceUtils.FILE1), "");
			
		} catch (Exception e) {
			logger.error(ServiceApp.class.getName()+" - Function setValeur( Key : "+key+", value : "+value+" ) - Impossible de set dans le fichier properties");
			logger.debug(e.getMessage());
		}
	
	}
	
	// Mettre dans la classe ServiceUtils
	private static Properties getPropretiesFile(Properties props, int option) {
		try {
			switch (option) {
			case 1:
				props.load(new FileInputStream(IServiceUtils.FILE1));
				break;

			case 2:
				props.load(new FileInputStream(IServiceUtils.FILE2));
				break;
				
			case 3:
				props.load(new FileInputStream(IServiceUtils.FILE3));
				break;
			
			case 4:
				props.load(new FileInputStream(IServiceUtils.FILE4));
				break;
			}
		} catch (Exception e) {
			logger.error(ServiceApp.class.getName()+" - Function getPropretiesFile( option :"+option+" ) - Impossible d'ouvrir le fichier properties");
			logger.debug(e.getMessage());
		}
		return props;
	}
	

}
