package cal.tpfinal.util;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cal.tpfinal.model.ServiceApp;

public final class ServiceValidation {
	
	private static final Logger logger = LogManager.getLogger(ServiceValidation.class.getName());
	private static Map<String, String> mapErreurs;
	// Controle du domaine de deuxieme niveau
	// Pour plus de sécuriter | permet de controler les noms de domaine
	private static final String[] tabNomDomaine = {".ca",".com",".us","fr"};
	private static final String[] tanNomEmail = {"hotmail","gmail","yahoo"};
	
	public static boolean isValideDonneesInputs(String nom, String prenom, String email, String dateBirth) {
		return valideEmail(email);
	}
	
	private static boolean valideEmail(String email) {
		if(Authentification.isEmailExist(email, ServiceApp.getValue("3",2))) {
			mapErreurs.put("errorEmail", "<h5 class=\"errormsg\">"+email+"est déjà utilisé pour un compte<h5>");
			return false;
		}
		return true;
	}
	
	private static boolean valideDateBirth(String dateBirth) {
		
		return true;
	}
	

	public static Map<String, String> getMapErreurs() {
		return mapErreurs;
	}

	public static void main(String[] args) {
		String email = "hotmail@gmail.ca";
		System.out.println(email.substring(email.indexOf("@")));
		
	}
}
