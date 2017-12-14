package cal.itext.service;

import java.util.ArrayList;
import java.util.List;

import cal.itext.bean.Client;
import cal.itext.bean.Commande;
import cal.itext.bean.Produit;

public class TestGenerationPDF {
	
	public static void main(String[] args) throws Exception {
		Client client = new Client("Elvis Presley","111 Montréal \n www.itext.com","514-111-1111");
		
		Produit p1 = new Produit("Ordi",1500d);
		Produit p2 = new Produit("Iphone",2500d);
		Produit p3 = new Produit("Frigo",1900d);
		Produit p4 = new Produit("TV Samsumg LED",1850.99);
		
		List<Produit> listeProduits = new ArrayList<Produit>();
		listeProduits.add(p1);listeProduits.add(p2);listeProduits.add(p3);listeProduits.add(p4);
		
		Commande commande = new Commande(client, listeProduits);
		
		PDFService pdfService = new PDFService(commande);
		pdfService.generatePDF("C:\\PDFTestJava/logoAL.jpg", "C:\\PDFTestJava/commande2.pdf");
		
	}

}
