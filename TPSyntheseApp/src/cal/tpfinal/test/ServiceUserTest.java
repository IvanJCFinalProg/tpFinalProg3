package cal.tpfinal.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServiceUser;

public class ServiceUserTest {
	User user;
	User ami;
	Map<Integer, User> mapUsers;
	String fileName;

	@Before
	public void setUp() throws Exception {
		user = new User();
		ami = new User();
		user.setNom("patata");
		user.setPrenom("datTest");
		ami.setNom("Subarusushi");
		ami.setPrenom("Honda");
		mapUsers = new HashMap<Integer, User>();
		fileName = "C:/appBasesDonnees/testsServices/testuserXml.xml";
	}

	@After
	public void tearDown() throws Exception {
		user = null;
		ami = null;
		mapUsers.clear();
		fileName=null;
	}

	@Test
	public void testAddFriend() {
		ServiceUser.addFriend(user, ami.getListeAmi());
		assertTrue(ami.getListeAmi().contains(user));
	}

	@Test
	public void testRemoveFriend() {
		ServiceUser.addFriend(user, ami.getListeAmi());
		ServiceUser.removeFriend(user, ami.getListeAmi());
		assertFalse(ami.getListeAmi().contains(user));
	}

	@Test
	public void testGetAmiById() {
		ServiceUser.addFriend(user, ami.getListeAmi());
		User tmp= ServiceUser.getAmiById(user.getCredential().getId(), ami.getListeAmi());
		assertSame(tmp, user);
		
	}

	@Test
	public void testGetUsersByTag() {
		ServiceUser.addUser(user, mapUsers);
		ServiceUser.addUser(ami, mapUsers);
		List<User> liste = ServiceUser.getUsersByTag("Subaru", mapUsers);
		assertTrue(liste.contains(ami));
	}

	@Test
	public void testAddUser() {
		ServiceUser.addUser(user, mapUsers);
		assertTrue(mapUsers.containsKey(user.getCredential().getId()) && mapUsers.containsValue(user));
	}

	@Test
	public void testDeleteUser() {
		ServiceUser.addUser(user, mapUsers);
		ServiceUser.deleteUser(user, mapUsers);
		assertFalse(mapUsers.containsKey(user.getCredential().getId()) && mapUsers.containsValue(user));
	}

	@Test
	public void testUpdateUser() {
		ServiceUser.addUser(user, mapUsers);
		ServiceUser.addUser(ami, mapUsers);
		user.setNom("Toyota");
		User tmp = ServiceUser.updateUser(user.getCredential().getId(), user, mapUsers);
		assertSame(tmp, user);
	}

	@Test
	public void testGetUserById() {
		ServiceUser.addUser(user, mapUsers);
		ServiceUser.addUser(ami, mapUsers);
		assertSame(user, ServiceUser.getUserById(user.getCredential().getId(), mapUsers));
	}

	@Test
	public void testBlockUser() {
		ServiceUser.addUser(user, mapUsers);
		ServiceUser.addUser(ami, mapUsers);
		ServiceUser.blockUser(user.getCredential().getId(), mapUsers);
		assertTrue(user.isBlocked());
	}

	@Test
	public void testSaveUser() {
		ServiceUser.addUser(ami, mapUsers);
		ServiceUser.saveToXML(mapUsers, fileName);
		assertTrue(ServiceUser.saveUser(fileName, user));
	}

	@Test
	public void testSaveToXML() {
		ServiceUser.addUser(user, mapUsers);
		ServiceUser.addUser(ami, mapUsers);
		assertTrue(ServiceUser.saveToXML(mapUsers, fileName));
	}

	@Test
	public void testLoadMapUserFromXML() {
		ServiceUser.addUser(user, mapUsers);
		ServiceUser.addUser(ami, mapUsers);
		ServiceUser.saveToXML(mapUsers, fileName);
		assertNotNull(ServiceUser.loadMapUserFromXML(fileName));
	}

}
