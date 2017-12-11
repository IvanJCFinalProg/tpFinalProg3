package cal.tpfinal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cal.tpfinal.model.ServiceApp;

public final class ServiceValidation {
	
	private static final Logger logger = LogManager.getLogger(ServiceValidation.class.getName());
	private static Map<String, String> mapErreurs = new HashMap<>();
	// Controle du domaine de deuxieme niveau
	// Pour plus de sécuriter | permet de controler les noms de domaine
	private static final String[] tabNomDomaine = {".ca",".com",".us","fr"};
	private static final String[] tabNomEmail = {"hotmail","gmail","yahoo"};
	
	public static boolean isValideDonneesInputs(String nom, String prenom, String email) {
		return valideEmail(email) && valideNomPrenom(nom, prenom);
	}
	
	private static boolean valideNomPrenom(String nom, String prenom) {
		if(!nom.matches(IServiceUtils.REGEX_NAME) || !prenom.matches(IServiceUtils.REGEX_NAME)) {
			mapErreurs.put("errorName", "<h5 class=\"errormsg\">Le nom ou le prénom rentré est incorrect<h5>");
			return false;
		}
		return true;
	}
	
	private static boolean valideEmail(String email) {
		if(Authentification.isEmailExist(email, ServiceApp.getValue("3",2))) {
			mapErreurs.put("errorEmail", "<h5 class=\"errormsg\">"+email+" est déjà utilisé par un autre compte<h5>");
			return false;
		}
		return true;
	}

	public static Map<String, String> getMapErreurs() {
		return mapErreurs;
	}

}
