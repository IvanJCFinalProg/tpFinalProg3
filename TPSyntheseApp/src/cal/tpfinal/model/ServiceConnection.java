package cal.tpfinal.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cal.tpfinal.bean.Credential;

public class ServiceConnection {
	
	public static boolean addUtilisateur(Credential credential, Map<Integer, Credential> collectionUtilisateur){
		collectionUtilisateur.put(credential.getId(), credential);
		return collectionUtilisateur.containsKey(credential.getId());
	}
	
	public static boolean deleteUtilisateur(Credential credential, Map<Integer, Credential> collectionUtilisateur){
		collectionUtilisateur.remove(credential.getId());
		return collectionUtilisateur.containsKey(credential.getId());
	}
	
	public Credential updateUtilisateur(int idx, Credential credential, Map<Integer, Credential> collectionUtilisateur) {
		collectionUtilisateur.replace(idx, collectionUtilisateur.get(idx), credential);
		return collectionUtilisateur.get(credential.getId());
	}
	
	public static Credential getUtilisateurById(int id, Map<Integer, Credential> collectionUtilisateur){
		return collectionUtilisateur.get(id);
	}
	
	public static boolean saveUtilisateur(String fileName, Credential credential) throws Exception {

		XStream stream = new XStream(new DomDriver());
		stream.alias("utilisateur", Credential.class);
		stream.alias("collectionUtilisateurs", Map.class);
		Map<Integer, Credential> tmp = loadCollectionMap(fileName);
		tmp.put(credential.getId(),credential);
		stream.toXML(tmp, new FileOutputStream(fileName));
		
		return (getUtilisateurById(credential.getId(), loadCollectionMap(fileName))).getId() == credential.getId();
	}

	public static boolean saveCollectionUtilisateurs(String fileName, Map<Integer, Credential> collectionUtilisateur) throws Exception {

		XStream stream = new XStream(new DomDriver());
		stream.alias("utilisateur", Credential.class);
		stream.alias("collectionUtilisateurs", Map.class);
		
		stream.toXML(collectionUtilisateur, new FileOutputStream(fileName));
		return new File(fileName).exists();
	}
	
	@SuppressWarnings("unchecked")
	public static Map<Integer, Credential> loadCollectionMap(String fileName) {
		
		Map<Integer, Credential> temp = null;
		try {
			XStream stream = new XStream(new DomDriver());
			stream.alias("utilisateur", Credential.class);
			stream.alias("collectionUtilisateurs", Map.class);
			temp = (Map<Integer, Credential>) stream.fromXML(new FileInputStream(fileName));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
}
