package cal.tpfinal.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cal.tpfinal.bean.Commentaire;

public class ServiceCommentaire {
	
	public static boolean addCommentaire(List<Commentaire> liste, Commentaire c) {
		return liste.add(c);
	}
	
	public static boolean removeCommentaire(List<Commentaire> liste, Commentaire c) {
		return liste.remove(c);
	}
	
	public static Commentaire getCommentaireById(List<Commentaire> liste, int id) {
		for(Commentaire tmp : liste)
			if(tmp.getId() == id)
				return tmp;
		return null;
	}
	
	public static boolean saveListeCommentaire(String fileName, List<Commentaire> liste) {
		try {
			XStream stream = new XStream(new DomDriver());
			stream.alias("Commentaire", Commentaire.class);
			stream.alias("Commentaires", List.class);
			
			stream.toXML(liste, new FileOutputStream(fileName));
		}catch(Exception e){}
		return new File(fileName).exists();
		
	}
	
	@SuppressWarnings("unchecked")
	public static List<Commentaire> loadListeCommentaire(String fileName) {
		
		List<Commentaire> temp = null;
		try {
			XStream stream = new XStream(new DomDriver());
			stream.alias("Commentaire", Commentaire.class);
			stream.alias("Commentaires", List.class);
			temp = (List<Commentaire>) stream.fromXML(new FileInputStream(fileName));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
}
