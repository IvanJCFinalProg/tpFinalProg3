package cal.tpfinal.util;

import java.util.Map;

import cal.tpfinal.bean.Credential;
import cal.tpfinal.model.ServiceConnection;
import cal.tpfinal.model.ServicePassword;

public class Authentification {
	
	public static Credential verificationUtilisateur(String email, String password, String fileName){
		if(email.equals("") || password.equals("")) 
			return null;
		Map<Integer, Credential> collectionUtilisateurs = ServiceConnection.loadMapCredentials(fileName);
		return (getUtilisateur(email, ServicePassword.encryptPassword(password), fileName, collectionUtilisateurs));
	}
	
	public static Credential getUtilisateur(String email, String password,String fileName, Map<Integer, Credential> collectionUtilisateurs) {
		for (Credential utilisateur : collectionUtilisateurs.values()) 
			if(utilisateur.getEmail().equalsIgnoreCase(email) && utilisateur.getPassword().equals(password))
				return utilisateur;
		return null;
	}
	
	public static boolean isEmailExist(String email,String fileName) {
		Map<Integer, Credential> tableLogins = ServiceConnection.loadMapCredentials(fileName);
		if(tableLogins==null)
			return false;
		for (Credential utilisateur : tableLogins.values()) 
			if(utilisateur.getEmail().equalsIgnoreCase(email))
				return true;
		return false;
	}
	
	/* En développement */
	/*public static boolean isPasswordIsCorrect(, String fileName, Map<Integer, Credential> collectionUtilisateurs) {
		for (Credential utilisateur : collectionUtilisateurs.values()) 
			if(utilisateur.getPassword().equals(password))
				return true;
		return false;
	}*/

}
