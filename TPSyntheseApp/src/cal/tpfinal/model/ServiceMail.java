package cal.tpfinal.model;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cal.tpfinal.util.MailProperties;


public class ServiceMail {
private static Logger logger = LogManager.getLogger(ServiceMail.class);
	
	public static void sendMail(String to, String from, String subject, String message) {
		
		try {
			logger.info("Début de l'envoi !");
			Properties props = MailProperties.getMailProperties();
			Authenticator auth = new SMTPAuthenticator(props.getProperty("mail.user"), props.getProperty("mail.password"));
			Session session = Session.getInstance(props, auth);
			
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(message);
			Transport.send(mimeMessage);
			logger.info("Le mail a été envoyé");
		} 
		catch (Exception e) {
			logger.error(ServiceMail.class.getName()+" | Problême ");
			logger.debug(e.getMessage());
		}
		
	}
	
	private static class SMTPAuthenticator extends Authenticator{
		
		private PasswordAuthentication authentication;
		public SMTPAuthenticator(String login, String password){
			authentication = new PasswordAuthentication(login, password);
		}
		
		protected PasswordAuthentication getPasswordAuthentication(){
			return authentication;
		}
	}
}
