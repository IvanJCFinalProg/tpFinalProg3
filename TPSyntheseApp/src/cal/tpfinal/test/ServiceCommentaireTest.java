package cal.tpfinal.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cal.tpfinal.bean.Commentaire;
import cal.tpfinal.bean.Publication;
import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServiceCommentaire;

public class ServiceCommentaireTest {
	User user;
	Publication p;
	String fileName;
	Commentaire c1, c2;
	@Before
	public void setUp() throws Exception {
		user = new User();
		p = new Publication("", user);
		fileName = "C:/appBasesDonnees/testsServices/testCommentaireXml.xml";
		c1= new Commentaire("SUBARUSUSHI!", user, p.getId());
		c2 = new Commentaire("TOYOTA HONDA!", user, p.getId());
	}

	@After
	public void tearDown() throws Exception {
		user = null;
		p = null;
		c1= null;
		c2 = null;
		fileName= null;
	}

	@Test
	public void testAddCommentaire() {
		ServiceCommentaire.addCommentaire(p.getListeCommentaires(), c1);
		assertTrue(p.getListeCommentaires().contains(c1));
	}

	@Test
	public void testRemoveCommentaire() {
		ServiceCommentaire.addCommentaire(p.getListeCommentaires(), c1);
		ServiceCommentaire.removeCommentaire(p.getListeCommentaires(), c1);
		assertFalse(p.getListeCommentaires().contains(c1));
	}

	@Test
	public void testGetCommentaireById() {
		ServiceCommentaire.addCommentaire(p.getListeCommentaires(), c1);
		ServiceCommentaire.addCommentaire(p.getListeCommentaires(), c2);
		assertSame(c1, ServiceCommentaire.getCommentaireById(p.getListeCommentaires(), c1.getId()));
	}

	@Test
	public void testSaveListeCommentaire() {
		ServiceCommentaire.addCommentaire(p.getListeCommentaires(), c1);
		ServiceCommentaire.addCommentaire(p.getListeCommentaires(), c2);
		assertTrue(ServiceCommentaire.saveListeCommentaire(fileName, p.getListeCommentaires()));
	}

	@Test
	public void testLoadListeCommentaire() {
		ServiceCommentaire.addCommentaire(p.getListeCommentaires(), c1);
		ServiceCommentaire.addCommentaire(p.getListeCommentaires(), c2);
		ServiceCommentaire.saveListeCommentaire(fileName, p.getListeCommentaires());
		List<Commentaire> tmp = ServiceCommentaire.loadListeCommentaire(fileName);
		assertNotNull(tmp);
	}

}
