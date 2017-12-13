package cal.tpfinal.model;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

import cal.tpfinal.bean.Publication;
import cal.tpfinal.bean.User;


public class ServicePDF {
	private User user;

	public ServicePDF(User user) {
		super();
		this.user = user;
	}
	
	private PdfPCell celluleSansBordure(String str) {
		PdfPCell cellule = new PdfPCell();
		Font font = new Font();
		cellule.setBorder(0);//sans bordure
		cellule.setHorizontalAlignment(Element.ALIGN_LEFT);//alignment horizontal
		cellule.addElement(new Paragraph(str, font));//pour ecrire
		return cellule;
	}
	private void genererBarCode(PdfWriter writer, PdfPTable table) {
		BarcodeEAN barcode = new BarcodeEAN();
	    barcode.setCodeType(Barcode.EAN8);
	    barcode.setCode("16040202");
	    PdfPCell cell1 = new PdfPCell();
	    PdfPCell cell2 = new PdfPCell(barcode.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.GRAY), true);
	    /*
		PdfContentByte cByte = writer.getDirectContent();//pour ecrire
		BarcodeEAN barCode = new BarcodeEAN();
		barCode.setCodeType(Barcode.EAN13);
		barCode.setCode("20112017-16H03-G02");
		Image imageEAN = barCode.createImageWithBarcode(cByte, null, null);
		
		PdfPCell cell = new PdfPCell(imageEAN, true);*/
		cell2.setPaddingTop(10);
		cell2.setHorizontalAlignment(Element.ALIGN_LEFT);//alignment horizontal
		cell2.setColspan(2);
		cell2.setBorder(0);
		cell1.setPaddingTop(10);
		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);//alignment horizontal
		cell1.setColspan(2);
		cell1.setBorder(0);
		table.addCell(cell1);
		table.addCell(cell2);
		//return imageEAN;
	}
	private PdfPTable genererTableauPublication(List<Publication> liste) {
		PdfPTable tableau = new PdfPTable(2);
		DecimalFormat dcf = new DecimalFormat("0.00");
		
		for(Publication p : liste) {
			tableau.addCell(p.getContent());
			tableau.addCell(p.getDate_publication());
		}
		return tableau;
	}
	
	private PdfPTable genererEntete(User user) throws Exception {
		PdfPTable tabEntete = new PdfPTable(2);
		//insertion du logo
		//Image image = Image.getInstance(filename);
		//image.setAlignment(Element.ALIGN_LEFT);
		//image.scalePercent(25);
		
		PdfPCell cell = new PdfPCell();
		//cell.setBorder(0);
		//tabEntete.addCell(cell);
		
		/*cell = celluleSansBordure("Facture PDF d'une commande");
		tabEntete.addCell(cell);*/
		
		tabEntete.addCell(celluleSansBordure("Nom"));
		tabEntete.addCell(celluleSansBordure(user.getNom()));
		
		tabEntete.addCell(celluleSansBordure("Prenom"));
		tabEntete.addCell(celluleSansBordure(user.getPrenom()));
		
		tabEntete.addCell(celluleSansBordure("Telephone "));
		tabEntete.addCell(celluleSansBordure(user.getPhoneNumber()));
		
		return tabEntete;
	}
	
	public void generationPDF(String fileName) {
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();
			PdfPTable tableauProduit = genererTableauPublication(user.getFeed());
			PdfPTable tableauEntete = genererEntete(user);
			genererBarCode(writer, tableauEntete);
			
			document.add(tableauEntete);
			document.add(tableauProduit);
			
			//Paragraph para = new Paragraph("\n\n Voici l'addresse du cegep : ");
			//Anchor anchor = new Anchor("http://www.claurendeau.qc.ca", FontFactory.getFont(FontFactory.TIMES_BOLDITALIC,13,new BaseColor(0,0,250)));
			
			//para.add(anchor);
			
			//document.add(para);
			document.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		User user = new User();
		user.setNom("Patate");
		user.setPrenom("Bob");
		user.setPhoneNumber("(514)123-4567");
		Publication p1 = new Publication("Je suis une patate", user);
		Publication p2 = new Publication("J'etais une patate", user);
		Publication p3 = new Publication("Je serai une patate", user);
		List<Publication> feed = new ArrayList<>();
		feed.add(p1);
		feed.add(p2);
		feed.add(p3);
		user.setFeed(feed);
		ServicePDF pdfService = new ServicePDF(user);
		pdfService.generationPDF("C:/appBasesDonnees/pdfs/"+user.getCredential().getId()+".pdf");
	}
}
