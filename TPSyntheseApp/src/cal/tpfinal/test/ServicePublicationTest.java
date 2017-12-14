package cal.tpfinal.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cal.tpfinal.bean.Publication;
import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServicePublication;

public class ServicePublicationTest {
	User user;
	Publication p;
	List<Publication> feed;
	String fileName;
	@Before
	public void setUp() throws Exception {
		user = new User();
		feed = new ArrayList<Publication>();
		p = new Publication("", user);
		fileName = "C:/appBasesDonnees/testsServices/testPublicationXml.xml";
	}

	@After
	public void tearDown() throws Exception {
		user = null;
		feed = null;
		p = null;
		fileName= null;
	}

	@Test
	public void testAddPublication() {
		ServicePublication.addPublication(feed, p);
		assertTrue(feed.contains(p));
	}

	@Test
	public void testRemovePublication() {
		ServicePublication.addPublication(feed, p);
		ServicePublication.removePublication(feed, p);
		assertFalse(feed.contains(p));
	}

	@Test
	public void testUpdatePublication() {
		ServicePublication.addPublication(feed, p);
		p.setContent("SUBARUSUSHI!");
		Publication tmp = ServicePublication.updatePublication(feed, p, p.getId());
		assertSame(p, tmp);
	}

	@Test
	public void testGetPublicationById() {
		ServicePublication.addPublication(feed, p);
		Publication p2 = new Publication("SUBARUSUSHI!", user);
		ServicePublication.addPublication(feed, p2);
		assertTrue(ServicePublication.getPublicationById(feed, p2.getId()) == p2);
	}

	@Test
	public void testSaveListePublication() {
		ServicePublication.addPublication(feed, p);
		Publication p2 = new Publication("SUBARUSUSHI!", user);
		ServicePublication.addPublication(feed, p2);
		assertTrue(ServicePublication.saveListePublication(fileName, feed));
	}

	@Test
	public void testLoadListePublication() {
		ServicePublication.addPublication(feed, p);
		Publication p2 = new Publication("SUBARUSUSHI!", user);
		ServicePublication.addPublication(feed, p2);
		ServicePublication.saveListePublication(fileName, feed);
		List<Publication> tmp = ServicePublication.loadListePublication(fileName);
		assertFalse(tmp.isEmpty());
	}

}
