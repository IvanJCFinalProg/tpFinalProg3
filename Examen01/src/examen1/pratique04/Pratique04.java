package examen1.pratique04;

import java.util.List;

import com.examen.Bean;
import com.examen.ServiceBean;

public class Pratique04 {
	public static void main(String[] args) {
		ServiceBean service = new ServiceBean();
		List<Bean> liste = service.loadFromXML("./Beans.xml");
		System.out.println("Nb femme riche et celibataire : " + service.getNbFemmeRicheCelibataire(liste));
		System.out.println("Nb retraite : " + service.augmenterSalaireRetraite(liste));
		System.out.println("Nb temps partiel & en couple : "+ service.getNbTempsPartielEtEnCouple(liste));
		service.saveBeanToXml(liste);
	}
}
