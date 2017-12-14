package cal.tpfinal.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cal.tpfinal.model.ServicePassword;

public class ServicePasswordTest {
	String pwd;
	@Before
	public void setUp() throws Exception {
		pwd = "SUBARUSUSHI!";
	}

	@After
	public void tearDown() throws Exception {
		pwd = null;
	}

	@Test
	public void testEncryptPassword() {
		assertNotSame(pwd, ServicePassword.encryptPassword(pwd));
	}

	@Test
	public void testDecryptPassword() {
		String tmp = ServicePassword.encryptPassword(pwd);
		assertTrue(pwd.equalsIgnoreCase(ServicePassword.decryptPassword(tmp)));
	}

}
