package cal.tpfinal.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cal.tpfinal.model.ServiceApp;

public class ServiceAppTest {
	String key;
	int option;
	String value;
	@Before
	public void setUp() throws Exception {
		key = "1";
		option = 1;
		value = "9";
	}

	@After
	public void tearDown() throws Exception {
		key = null;
		option = (-1);
		value = null;
	}

	@Test
	public void testGetValue() {
		assertNotNull(ServiceApp.getValue(key, option));
	}
	/*Ce test permet de modifier les fichiers de properties, 
	 * ce test est donc déconseiller surtout une fois que
	 * l'application est lancée.
	*/
	@Ignore
	public void testSetValue() {
		ServiceApp.setValue(key, value, option);
		assertTrue(ServiceApp.getValue(key, option).equals(value));
	}

}
