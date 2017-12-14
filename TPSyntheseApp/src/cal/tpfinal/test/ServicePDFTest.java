package cal.tpfinal.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cal.tpfinal.bean.Publication;
import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServicePDF;

public class ServicePDFTest {
	User user = new User();
	Publication p1;
	Publication p2;
	Publication p3;
	List<Publication> feed;
    ServicePDF pdfService;
	@Before
	public void setUp() throws Exception {
		user.setNom("Patate");
		user.setPrenom("Bob");
		user.setPhoneNumber("(514)123-4567");
		p1 = new Publication("Je suis une patate", user);
		p2 = new Publication("J'etais une patate", user);
		p3 = new Publication("Je serai une patate", user);
		feed = new ArrayList<>();
		feed.add(p1);
		feed.add(p2);
		feed.add(p3);
		user.setFeed(feed);
		pdfService = new ServicePDF(user);
	}

	@After
	public void tearDown() throws Exception {
		user = null;
		p1 = null;
		p2 = null;
		p3 = null;
		feed.clear();
		pdfService = null;
	}

	@Test
	public void testGenerationPDF() {
		pdfService.generationPDF("C:/appBasesDonnees/pdfs/"+user.getCredential().getId()+".pdf");
		assertTrue(new File("C:/appBasesDonnees/pdfs/"+user.getCredential().getId()+".pdf").exists());
	}

}
