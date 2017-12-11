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
		return valideEmail(email);
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

	public static void main(String[] args) {
		//String email = "hotmail@gmail.ca";
		//System.out.println(email.substring(email.indexOf("@")));
		
		
		Pattern pattern = Pattern.compile("^[A-Za-z]{2,20}$");
		String name = "Michel";
		//System.out.println(name.matches(""));
	}
}
