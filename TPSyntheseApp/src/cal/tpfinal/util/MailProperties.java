package cal.tpfinal.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailProperties {

	private static Logger logger = LogManager.getLogger(MailProperties.class.getName());
	
	public static Properties getMailProperties(){
		
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(IServiceUtils.FILE4));
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.socketFactory.port", "465");
			props.setProperty("mail.smtp.port", "465");
			props.setProperty("mail.user", props.getProperty("login"));
			props.setProperty("mail.password", props.getProperty("password"));
		} 
		catch (Exception e) {
			logger.error(MailProperties.class.getName()+" | Probleme dans la fonction getMailProperties()");
			logger.debug(e.getMessage());
		}
		return props;
		
	}
	

}
