package cal.tpfinal.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cal.tpfinal.bean.Credential;

public class ServiceConnection {
	
	private static Logger logger = LogManager.getLogger(ServiceConnection.class.getName());
	
	public static boolean addCredential(Credential credential, Map<Integer, Credential> mapCredential){
		mapCredential.put(credential.getId(), credential);
		return mapCredential.containsKey(credential.getId());
	}
	
	public static boolean deleteCredential(Credential credential, Map<Integer, Credential> mapCredential){
		mapCredential.remove(credential.getId());
		return mapCredential.containsKey(credential.getId());
	}
	
	public static Credential updateCredential(int idx, Credential credential, Map<Integer, Credential> mapCredential) {
		mapCredential.replace(idx, mapCredential.get(idx), credential);
		return mapCredential.get(credential.getId());
	}
	
	public static Credential getCredentialById(int id, Map<Integer, Credential> mapCredential){
		return mapCredential.get(id);
	}
	
	public static boolean saveCredential(String fileName, Credential credential) throws Exception {

		XStream stream = new XStream(new DomDriver());
		stream.alias("Credential", Credential.class);
		stream.alias("tableLogins", Map.class);
		stream.alias("Login", Entry.class);
		Map<Integer, Credential> tmp = loadMapCredentials(fileName);
		tmp.put(credential.getId(),credential);
		stream.toXML(tmp, new FileOutputStream(fileName));
		
		return (getCredentialById(credential.getId(), loadMapCredentials(fileName))).getId() == credential.getId();
	}

	public static boolean saveMapCredentials(String fileName, Map<Integer, Credential> mapCredential) throws Exception {

		XStream stream = new XStream(new DomDriver());
		stream.alias("Credential", Credential.class);
		stream.alias("tableLogins", Map.class);
		stream.alias("Login", Entry.class);
		stream.toXML(mapCredential, new FileOutputStream(fileName));
		return new File(fileName).exists();
	}
	
	@SuppressWarnings("unchecked")
	public static Map<Integer, Credential> loadMapCredentials(String fileName) {
		
		Map<Integer, Credential> temp = null;
		try {
			XStream stream = new XStream(new DomDriver());
			stream.alias("Credential", Credential.class);
			stream.alias("tableLogins", Map.class);
			stream.alias("Login", Entry.class);
			temp = (Map<Integer, Credential>) stream.fromXML(new FileInputStream(fileName));
		}catch (Exception e) {
			logger.error(ServiceConnection.class.getName()+" | Probleme dans la fonction loadMapCredentials() ");
			logger.debug(e.getMessage());
		}
		return temp;
	}
	
}
