package cal.tpfinal.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cal.tpfinal.bean.Commentaire;
import cal.tpfinal.bean.Publication;

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
	
	public static boolean saveListePublication(String fileName, List<Publication> liste) throws Exception {

		XStream stream = new XStream(new DomDriver());
		stream.alias("Commentaire", Commentaire.class);
		stream.alias("Publication", Publication.class);
		stream.alias("Publications", List.class);
		
		stream.toXML(liste, new FileOutputStream(fileName));
		return new File(fileName).exists();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Publication> loadListePublication(String fileName) {
		
		List<Publication> temp = null;
		try {
			XStream stream = new XStream(new DomDriver());
			stream.alias("Commentaire", Commentaire.class);
			stream.alias("Publication", Publication.class);
			stream.alias("Publications", List.class);
			temp = (List<Publication>) stream.fromXML(new FileInputStream(fileName));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
}
