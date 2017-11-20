package cal.tpfinal.util;

import java.util.Map;

import cal.tpfinal.bean.Credential;
import cal.tpfinal.model.ServiceConnection;

public class Authentification {
	
	public static Credential verificationUtilisateur(String email, String password, String fileName){
		if(email.equals("") || password.equals("")) 
			return null;
		Map<Integer, Credential> collectionUtilisateurs = ServiceConnection.loadCollectionMap(fileName);
		return (getUtilisateur(email, password, fileName, collectionUtilisateurs));
	}
	
	public static Credential getUtilisateur(String email, String password,String fileName, Map<Integer, Credential> collectionUtilisateurs) {
		for (Credential utilisateur : collectionUtilisateurs.values()) 
			if(utilisateur.getEmail().equalsIgnoreCase(email) && utilisateur.getPassword().equals(password))
				return utilisateur;
		return null;
	}
	
	public static boolean isEmailExist(String email,String fileName) {
		Map<Integer, Credential> tableUsers = ServiceConnection.loadCollectionMap(fileName);
		if(tableUsers==null)
			return false;
		for (Credential utilisateur : tableUsers.values()) 
			if(utilisateur.getEmail().equalsIgnoreCase(email))
				return true;
		return false;
	}
	
	/*public static boolean isPasswordIsCorrect(, String fileName, Map<Integer, Credential> collectionUtilisateurs) {
		for (Credential utilisateur : collectionUtilisateurs.values()) 
			if(utilisateur.getPassword().equals(password))
				return true;
		return false;
	}*/

}
