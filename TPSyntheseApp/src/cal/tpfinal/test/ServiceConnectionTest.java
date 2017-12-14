package cal.tpfinal.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cal.tpfinal.bean.Credential;
import cal.tpfinal.model.ServiceConnection;

public class ServiceConnectionTest {
	Map<Integer, Credential> mapCredential;
	Credential c1;
	Credential c2;
	String fileName;
	@Before
	public void setUp() throws Exception {
		mapCredential = new HashMap<Integer, Credential>();
		c1 = new Credential(0);
		c2 = new Credential(1);
		fileName = "C:/appBasesDonnees/testsServices/testCredentialXml.xml";
	}

	@After
	public void tearDown() throws Exception {
		mapCredential.clear();
		c1 =null;
		c2 =null;
		fileName = null;
	}

	@Test
	public void testAddCredential() {
		ServiceConnection.addCredential(c1, mapCredential);
		assertTrue(mapCredential.containsKey(c1.getId()));
	}

	@Test
	public void testDeleteCredential() {
		ServiceConnection.addCredential(c1, mapCredential);
		ServiceConnection.deleteCredential(c1, mapCredential);
		assertFalse(mapCredential.containsKey(c1.getId()));
	}

	@Test
	public void testUpdateCredential() {
		ServiceConnection.addCredential(c1, mapCredential);
		c1.setEmail("datEmail@gmail.com");
		Credential tmp = ServiceConnection.updateCredential(c1.getId(), c1, mapCredential);
		assertSame(c1, tmp);
		
	}

	@Test
	public void testGetCredentialById() {
		ServiceConnection.addCredential(c1, mapCredential);
		ServiceConnection.addCredential(c2, mapCredential);
		Credential tmp = ServiceConnection.getCredentialById(c2.getId(), mapCredential);
		assertSame(tmp, c2);
	}

	@Test
	public void testSaveCredential() {
		assertTrue(ServiceConnection.saveCredential(fileName, c1));
	}

	@Test
	public void testSaveMapCredentials() {
		ServiceConnection.addCredential(c1, mapCredential);
		ServiceConnection.addCredential(c2, mapCredential);
		assertTrue(ServiceConnection.saveMapCredentials(fileName, mapCredential));
	}

	@Test
	public void testLoadMapCredentials() {
		ServiceConnection.addCredential(c1, mapCredential);
		ServiceConnection.addCredential(c2, mapCredential);
		ServiceConnection.saveMapCredentials(fileName, mapCredential);
		Map<Integer, Credential> tmp = ServiceConnection.loadMapCredentials(fileName);
		assertTrue(tmp.get(0).getId() == c1.getId());
	}

}
