package cal.tpfinal.model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cal.tpfinal.bean.Credential;
import cal.tpfinal.bean.User;

/**
 * 
 * @author Ivan Nguemegne
 * Cette classe implemente la m�thodologie du C.R.U.D. Elle permet d'ajouter, de supprimer , de rechercher 
 * et de modifier dans une collection de type Map des objets de type Client. De plus, elle permet de sauvegarder
 * des �l�ments d'une Map dans un fichier XML et de reconvertir ce fichier en collection Map. 
 */
public class ServiceUser {

	/**
	 * Cette m�thode permet d'ajouter un objet Client dans une collection de type Map.
	 * @param user object user � ajouter dans la collection.
	 * @param tableUsers table de type Map.
	 * @return boolean true si le client est bien ajout� � la collection.
	 */
	public static boolean addUser(User user, Map<Integer, User> tableUsers) {
		if(user == null)
			return false;
		tableUsers.put(user.getCredential().getId(), user);
		return tableUsers.containsValue(user);
	}

	/**
	 * Cette m�thode permet de supprimer un objet Client dans une collection de type Map.
	 * @param user object client � supprimer dans la collection.
	 * @param tableUsers collection de type Map.
	 * @return boolean true si le client est bien supprim� de la collection.
	 */
	public static boolean deleteUser(User user, Map<Integer, User> tableUsers) {
		if(user == null || (tableUsers.containsKey(user.getCredential().getId()) == false) )
			return false;
		tableUsers.remove(user.getCredential().getId());
		return tableUsers.containsKey(user.getCredential().getId());
	}

	/**
	 * Cette m�thode permet de modifier un objet Client dans une collection de type Map.
	 * @param user object client � modifier dans la collection.
	 * @param tableUsers collection de type Map.
	 * @return Client objet client modifi�.
	 */
	public static User updateUser(int idx, User user, Map<Integer, User> tableUsers) {
		if(user == null || tableUsers.containsKey(user.getCredential().getId()) == false)
			return null;
		tableUsers.replace(idx, tableUsers.get(idx), user);
		return tableUsers.get(user.getCredential().getId());
	}

	
	/**
	 * Cette m�thode permet de supprimer un objet Client dans une collection de type Map.
	 * @param idx position  dans la collection de l'objet recherch�.
	 * @param tableUsers collection de type Map.
	 * @return User utilisateur qui est recherch�.
	 */
	public static User getUserById(int idx, Map<Integer, User> tableUsers) {
		return tableUsers.get(idx);
	}
	
	public static boolean saveClient(String fileName, User client) throws Exception {
		XStream stream = new XStream(new DomDriver());
		stream.alias("Utilisateur", User.class);
		stream.alias("Credential", Credential.class);
		Map<Integer, User> tmp = fromToXML(fileName);
		tmp.put(client.getCredential().getId(),client);
		stream.toXML(tmp, new FileOutputStream(fileName));
		
		return (getUserById(client.getCredential().getId(), fromToXML(fileName))).getCredential().getId() == client.getCredential().getId();
	}
	
	/**
	 * Cette m�thode permet de sauvegarder des objects contenues dans une Map dans un fichier XML.
	 * @param collectionClient collection de type Map � sauvegarder.
	 * @param fileName nom du fichier pour la sauvegarde.
	 * @return boolean true si le fichier existe.
	 */
	public static boolean saveToXML(Map<Integer, User> collectionClient, String fileName) {
		try {
			XStream stream = new XStream(new DomDriver());
			stream.alias("Utilisateur", User.class);
			stream.alias("Credential", Credential.class);
			stream.toXML(collectionClient, new FileOutputStream(fileName));
			return new File(fileName).exists();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}

	/**
	 * Cette m�thode permet de convertir un fichier XML en collection de type Map.
	 * @param fileName nom du fichier.
	 * @return Object Map ou List contenant des objets Client ou Transaction.
	 */
	@SuppressWarnings("unchecked")
	public static Map<Integer, User> fromToXML(String fileName) {
		try {
			XStream stream = new XStream(new DomDriver());
			stream.alias("Utilisateur", User.class);
			stream.alias("Credential", Credential.class);
			return (Map<Integer, User>)stream.fromXML(new FileInputStream(fileName));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}

	

}