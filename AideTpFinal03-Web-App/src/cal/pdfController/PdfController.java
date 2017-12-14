package cal.pdfController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cal.itext.bean.Client;
import cal.itext.bean.Commande;
import cal.itext.bean.Produit;
import cal.itext.service.PDFService;

/**
 * Servlet implementation class PdfController
 */
@WebServlet("/PdfController")
public class PdfController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = LogManager.getLogger(PdfController.class.getName());
	private static final String LOGO = "C:\\PDFTestJava/logoAL.jpg";
	private static final String FILE_NAME= "C:\\PDFTestJava/commande-servlet.pdf";

	
	public void init(ServletConfig config) throws ServletException {
		try {
			Client client = new Client("Elvis Presley","111 Montréal \n www.itext.com","514-111-1111");
			
			Produit p1 = new Produit("Ordi",1500d);
			Produit p2 = new Produit("Iphone",2500d);
			Produit p3 = new Produit("Frigo",1900d);
			Produit p4 = new Produit("TV Samsumg LED",1850.99);
			Produit p5 = new Produit("Bouteille",34.99);
			Produit p6 = new Produit("PS4",334.99);
			
			List<Produit> listeProduits = new ArrayList<Produit>();
			listeProduits.add(p1);listeProduits.add(p2);listeProduits.add(p3);listeProduits.add(p4);listeProduits.add(p5);listeProduits.add(p6);
			
			Commande commande = new Commande(client, listeProduits);
			
			PDFService pdfService = new PDFService(commande);
			logger.info("generation d'un fichier pdf à partir de la méthode Init() de la servlet");
			pdfService.generatePDF(LOGO, FILE_NAME);
			
		} catch (Exception e) {
			logger.error(PdfController.class.getName()+""+"Problême function ");
			logger.debug(e.getMessage());
		}
		
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
