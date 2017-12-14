package cal.tpfinal.model;

import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
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
		PdfContentByte cByte = writer.getDirectContent(); // Pour écrire
		// Je choisis le type de barre code
		BarcodeEAN barCode = new BarcodeEAN();
		// mettre en un format voulu
		barCode.setCodeType(Barcode.EAN13);
		// ce qui sera afficher en bas
		String code = System.currentTimeMillis()+"";
		if(code.length()<13)
			while(code.length()<13)
				code+=""+(int)(Math.random()*9);
		else code = code.substring(0, 13);
		barCode.setCode(code);
		// generer une image du barre code
		Image imageEAN = barCode.createImageWithBarcode(cByte, null, null);
		// Mettre le barre code dans une cellule
		PdfPCell cell = new PdfPCell(imageEAN,false);
		cell.setPaddingTop(10);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);
		cell.setBorder(2);
		table.addCell(cell);
	}
	private PdfPTable genererTableauPublication(List<Publication> liste) {
		PdfPTable tableau = new PdfPTable(2);
		
		for(Publication p : liste) {
			tableau.addCell(p.getContent());
			tableau.addCell(p.getDate_publication());
		}
		return tableau;
	}
	
	private PdfPTable genererEntete(User user) throws Exception {
		PdfPTable tabEntete = new PdfPTable(2);
		
		tabEntete.addCell(celluleSansBordure("Nom"));
		tabEntete.addCell(celluleSansBordure(user.getNom()));
		
		tabEntete.addCell(celluleSansBordure("Prenom"));
		tabEntete.addCell(celluleSansBordure(user.getPrenom()));
		
		tabEntete.addCell(celluleSansBordure("Age "));
		tabEntete.addCell(celluleSansBordure(user.getAge()+""));
		
		tabEntete.addCell(celluleSansBordure("Email "));
		tabEntete.addCell(celluleSansBordure(user.getCredential().getEmail()));
		
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
			document.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
