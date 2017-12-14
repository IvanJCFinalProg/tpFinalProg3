package cal.itext.service;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import cal.itext.bean.Client;
import cal.itext.bean.Commande;
import cal.itext.bean.Produit;

public class PDFService {
	private Commande commande;

	public PDFService(Commande commande) {
		super();
		this.commande = commande;
	}
	
	private PdfPCell celluleSansBordure(String str) {
		PdfPCell cellule = new PdfPCell();
		Font font = new Font();
		// dire une cellule sans bordure
		cellule.setBorder(0);
		cellule.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		// écrire dans une cellule
		cellule.addElement(new Paragraph(str,font));
		return cellule;
	}
	
	// generer le code barre
	private Image generateBarreCode(PdfWriter writer, PdfPTable tableau) {
		PdfContentByte cByte = writer.getDirectContent(); // Pour écrire
		// Je choisis le type de barre code
		BarcodeEAN barCode = new BarcodeEAN();
		// mettre en un format voulu
		barCode.setCodeType(Barcode.EAN13);
		// ce qui sera afficher en bas
		barCode.setCode("20112017160302");
		// generer une image du barre code
		Image imageEAN = barCode.createImageWithBarcode(cByte, null, null);
		// Mettre le barre code dans une cellule
		PdfPCell cell = new PdfPCell(imageEAN,false);
		cell.setPaddingTop(10);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);
		cell.setBorder(2);
		tableau.addCell(cell);
		return imageEAN;
	}
	
	private PdfPTable generateTableProducts(List<Produit> listeProduits) {
		// Pour mettre 2 colonnes dans le tableau dans le pdf
		PdfPTable tableau = new PdfPTable(2);
		// mettre les points pour les décimals
		DecimalFormat dcf = new DecimalFormat("0.00");
		
		// Boucler dans le tab pour ajouter la descripton et le prix dans le tableau
		for (Produit produit : listeProduits) {
			tableau.addCell(produit.getDescription());
			tableau.addCell(produit.getPrix()+" CAD");
		}
		tableau.addCell("TAXE-PROVINCIALE");
		tableau.addCell("7.5%");
		tableau.addCell("TAXE_REGIONNAL");
		tableau.addCell("5.5%");
		tableau.addCell("5.5%");
		
		// Calcul du total de la commande
		double totalCommandeAvecTaxes = commande.getTotalCommande()+ commande.getTotalCommande()*0.15;
		tableau.addCell("Le total de la commande");
		tableau.addCell(dcf.format(totalCommandeAvecTaxes)+" CAD");
		return tableau;
	}
	
	private PdfPTable generateEntete(Client client, String logo) {
		PdfPTable tableauEntete = new PdfPTable(2);
		try {
			
			// Insertion du logo
			Image image = Image.getInstance(logo);
			image.setAlignment(Element.ALIGN_LEFT);
			image.scalePercent(25); // Avoir un bon affichage du logo
			
			PdfPCell cell = new PdfPCell(image, false);
			cell.setBorder(0);
			tableauEntete.addCell(cell);
			cell = celluleSansBordure("Facture PDF d'une commande");
			tableauEntete.addCell(cell);
			
			tableauEntete.addCell(celluleSansBordure("Nom client"));
			tableauEntete.addCell(celluleSansBordure(client.getNom()));
			
			tableauEntete.addCell(celluleSansBordure("Adresse client"));
			tableauEntete.addCell(celluleSansBordure(client.getAdresse()));
			
			tableauEntete.addCell(celluleSansBordure("Téléphone client"));
			tableauEntete.addCell(celluleSansBordure(client.getTelephone()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tableauEntete;
	}
	
	public void generatePDF(String logo, String filename) {
		Document document = new Document();
		
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
			document.open();
			PdfPTable tableauProduit = generateTableProducts(commande.getListeProduits());
			PdfPTable tableauEnTete = generateEntete(commande.getClient(), logo);
			generateBarreCode(writer, tableauEnTete);
			
			document.add(tableauEnTete);
			document.add(tableauProduit);
			
			// Juste pour écrire quelque chose
			Paragraph paragraphe = new Paragraph("\n\n Voici l'adresse du CÉGÉP : ");
			Anchor anchor = new Anchor("http://www.claurendeau.qc.ca", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.UNDERLINE, new BaseColor(0,0,255)));
			paragraphe.add(anchor);
			document.add(paragraphe);
			// Ferme le document IMPORTANT!!!
			document.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
