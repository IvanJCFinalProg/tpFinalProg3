package cal.tpfinal.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map.Entry;

import org.joda.time.DateTime;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cal.tpfinal.bean.Commentaire;
import cal.tpfinal.bean.Credential;
import cal.tpfinal.bean.Publication;
import cal.tpfinal.bean.User;

public class ServicePublication {
	
	public static boolean addPublication(List<Publication> liste, Publication p) {
		return liste.add(p);
	}
	
	public static boolean removePublication(List<Publication> liste, Publication p) {
		return liste.remove(p);
	}
	
	public static Publication updatePublication(List<Publication> liste, Publication p, int id) {
		liste.remove(getPublicationById(liste, id));
		liste.add(p);
		return getPublicationById(liste, p.getId());
	}
	
	public static Publication getPublicationById(List<Publication> liste, int id) {
		for(Publication tmp : liste)
			if(tmp.getId() == id)
				return tmp;
		return null;
	}
	
	public static boolean saveListePublication(String fileName, List<Publication> liste) {
		try {
			XStream stream = new XStream(new DomDriver());
			stream.alias("commentaire", Commentaire.class);
			stream.alias("publication", Publication.class);
			stream.alias("Utilisateur", User.class);
			stream.alias("Credential", Credential.class);
			stream.alias("feed", List.class);
			stream.alias("User", Entry.class);
			stream.alias("Date", DateTime.class);
			stream.toXML(liste, new FileOutputStream(fileName));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new File(fileName).exists();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Publication> loadListePublication(String fileName) {
		
		List<Publication> temp = null;
		try {
			XStream stream = new XStream(new DomDriver());
			stream.alias("commentaire", Commentaire.class);
			stream.alias("publication", Publication.class);
			stream.alias("Utilisateur", User.class);
			stream.alias("Credential", Credential.class);
			stream.alias("feed", List.class);
			stream.alias("User", Entry.class);
			stream.alias("Date", DateTime.class);
			temp = (List<Publication>) stream.fromXML(new FileInputStream(fileName));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
}
