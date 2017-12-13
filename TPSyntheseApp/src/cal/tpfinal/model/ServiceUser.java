package cal.tpfinal.model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cal.tpfinal.bean.Commentaire;
import cal.tpfinal.bean.Credential;
import cal.tpfinal.bean.Publication;
import cal.tpfinal.bean.User;

/**
 * 
 * @author Ivan Nguemegne
 * Cette classe implemente la méthodologie du C.R.U.D. Elle permet d'ajouter, de supprimer , de rechercher 
 * et de modifier dans une collection de type Map des objets de type Client. De plus, elle permet de sauvegarder
 * des éléments d'une Map dans un fichier XML et de reconvertir ce fichier en collection Map. 
 */
public class ServiceUser {
	
	private static Logger logger = LogManager.getLogger(ServiceUser.class.getName());
	
	public static boolean addFriend(User user, List<User>listeAmi) {
		return listeAmi.add(user);
	}
	
	public static boolean removeFriend(User user, List<User>listeAmi) {
		return listeAmi.remove(user);
	}
	public static User getAmiById(int id, List<User> listeAmi) {
		for(User user : listeAmi) {
			if(user.getCredential().getId() == id) {
				return user;
			}
		}
		return null;
	}
	public static List<User> getUsersByTag(String tag, Map<Integer, User> tableUsers){
		List<User> liste = new ArrayList<User>();
		String regex = "(?i).*"+tag+".*";
		for(User user : tableUsers.values()) {
			if((user.getPrenom()+" "+user.getNom()).matches(regex) ) {
				liste.add(user);
			}
		}
		return liste;
	}
	
	/**
	 * Cette méthode permet d'ajouter un objet User dans une collection de type Map.
	 * @param user object user à ajouter dans la collection.
	 * @param tableUsers table de type Map.
	 * @return boolean true si le client est bien ajouté à la collection.
	 */
	public static boolean addUser(User user, Map<Integer, User> tableUsers) {
		if(user == null)
			return false;
		tableUsers.put(user.getCredential().getId(), user);
		return tableUsers.containsValue(user);
	}

	/**
	 * Cette méthode permet de supprimer un objet User dans une collection de type Map.
	 * @param user object client à supprimer dans la collection.
	 * @param tableUsers collection de type Map.
	 * @return boolean true si le client est bien supprimé de la collection.
	 */
	public static boolean deleteUser(User user, Map<Integer, User> tableUsers) {
		if(user == null || (tableUsers.containsKey(user.getCredential().getId()) == false) )
			return false;
		tableUsers.remove(user.getCredential().getId());
		return tableUsers.containsKey(user.getCredential().getId());
	}

	/**
	 * Cette méthode permet de modifier un objet User dans une collection de type Map.
	 * @param user object client à modifier dans la collection.
	 * @param tableUsers collection de type Map.
	 * @return Client objet client modifié.
	 */
	public static User updateUser(int idx, User user, Map<Integer, User> tableUsers) {
		if(user == null || tableUsers.containsKey(user.getCredential().getId()) == false)
			return null;
		tableUsers.replace(idx, tableUsers.get(idx), user);
		return tableUsers.get(user.getCredential().getId());
	}

	
	/**
	 * Cette méthode permet de supprimer un objet User dans une collection de type Map.
	 * @param idx position  dans la collection de l'objet recherché.
	 * @param tableUsers collection de type Map.
	 * @return User utilisateur qui est recherché.
	 */
	public static User getUserById(int idx, Map<Integer, User> tableUsers) {
		return tableUsers.get(idx);
	}
	
	public static void blockUser(int id, Map<Integer, User> tableUsers) {
		getUserById(id, tableUsers).setBlocked(true);
	}
	
	public static boolean saveUser(String fileName, User user) {
		try {
			XStream stream = new XStream(new DomDriver());
			stream.alias("tableUsers", Map.class);
			stream.alias("feed", List.class);
			stream.alias("User", Entry.class);
			stream.alias("Utilisateur", User.class);
			stream.alias("Credential", Credential.class);
			stream.alias("publication", Publication.class);
			stream.alias("commentaire", Commentaire.class);
			stream.alias("Date", DateTime.class);
			Map<Integer, User> tmp = loadMapUserFromXML(fileName);
			tmp.put(user.getCredential().getId(),user);
			stream.toXML(tmp, new FileOutputStream(fileName));
		}catch (Exception e) {
			logger.error(ServiceUser.class.getName() +" Probleme dans la fonction saveClient()");
			logger.debug(e.getMessage() +" "+e.getLocalizedMessage());
		}
		return (getUserById(user.getCredential().getId(), loadMapUserFromXML(fileName))).getCredential().getId() == user.getCredential().getId();
	}
	
	/**
	 * Cette méthode permet de sauvegarder des objects contenues dans une Map dans un fichier XML.
	 * @param tableUsers collection de type Map à sauvegarder.
	 * @param fileName nom du fichier pour la sauvegarde.
	 * @return boolean true si le fichier existe.
	 */
	public static boolean saveToXML(Map<Integer, User> tableUsers, String fileName) {
		try {
			XStream stream = new XStream(new DomDriver());
			stream.alias("tableUsers", Map.class);
			stream.alias("feed", List.class);
			stream.alias("User", Entry.class);
			stream.alias("Utilisateur", User.class);
			stream.alias("Credential", Credential.class);
			stream.alias("publication", Publication.class);
			stream.alias("commentaire", Commentaire.class);
			stream.alias("Date", DateTime.class);
			stream.toXML(tableUsers, new FileOutputStream(fileName));
			return new File(fileName).exists();
			
		} catch (Exception e) {
			logger.error(ServiceUser.class.getName() +" Probleme dans la fonction saveToXML()");
			logger.debug(e.getMessage() +" "+e.getLocalizedMessage());
		}
		
		return false;
	}

	/**
	 * Cette méthode permet de convertir un fichier XML en collection de type Map.
	 * @param fileName nom du fichier.
	 * @return Object Map ou List contenant des objets Client ou Transaction.
	 */
	@SuppressWarnings("unchecked")
	public static Map<Integer, User> loadMapUserFromXML(String fileName) {
		try {
			XStream stream = new XStream(new DomDriver());
			stream.alias("tableUsers", Map.class);
			stream.alias("feed", List.class);
			stream.alias("User", Entry.class);
			stream.alias("Utilisateur", User.class);
			stream.alias("Credential", Credential.class);
			stream.alias("publication", Publication.class);
			stream.alias("commentaire", Commentaire.class);
			stream.alias("Date", DateTime.class);
			return (Map<Integer, User>)stream.fromXML(new FileInputStream(fileName));
			
		} catch (Exception e) {
			logger.error(ServiceUser.class.getName() +" Probleme dans la fonction fromToXML()");
			logger.debug(e.getMessage() +" "+e.getLocalizedMessage());
		}
		
		return null;
	}

}
