package cal.tpfinal.model;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServicePassword {
	
	private static Logger logger = LogManager.getLogger(ServicePassword.class);
	
	public static String encryptPassword(String password) {
		String encodedPassword = null;
		try {
			encodedPassword = new String(Base64.encodeBase64(password.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			logger.error(ServicePassword.class.getName()+" Function encryptPassword() - problême d'encryptage du mot de passe");
			logger.debug(e.getMessage());
		}
		return encodedPassword;
	}
	
	public static String decryptPassword(String password) {
		String decodedPassword = null;
		try {
			decodedPassword = new String(Base64.decodeBase64(password.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			logger.error(ServicePassword.class.getName()+" Function decryptPassword() - problême decryptage du mot de passe");
			logger.debug(e.getMessage());
		}
		System.out.println("//// "+decodedPassword);
		return decodedPassword;
	}
}
