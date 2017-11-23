package cal.tpfinal.bean;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class User {
	
	private String nom;
	private String prenom;
	private int age;
	private String phoneNumber;
	// voir
	private String sexe;
	private DateTime birthDate;
	private DateTime dateInscription;
	private boolean connected = false;
	private boolean blocked = false;
	protected Credential credential;
	private static int compteur = 1;
	private List<Publication> feed;
		
	public User() {
		this.credential = new Credential(compteur);
		compteur++;
		feed = new ArrayList<Publication>();
	}
	
	/**
	 * @param compteur the compteur to set
	 */
	public static void setCompteur(int compteur) {
		User.compteur = compteur;
	}


	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the sexe
	 */
	public String getSexe() {
		return sexe;
	}

	/**
	 * @param sexe the sexe to set
	 */
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}


	/**
	 * @return the birthDate
	 */
	public DateTime getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(DateTime birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the dateInscription
	 */
	public DateTime getDateInscription() {
		return dateInscription;
	}

	/**
	 * @param dateInscription the dateInscription to set
	 */
	public void setDateInscription(DateTime dateInscription) {
		this.dateInscription = dateInscription;
	}

	/**
	 * @return the connected
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * @param connected the connected to set
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	/**
	 * @return the blocked
	 */
	public boolean isBlocked() {
		return blocked;
	}

	/**
	 * @param blocked the blocked to set
	 */
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	/**
	 * @return the credential
	 */
	public Credential getCredential() {
		return credential;
	}

	/**
	 * @param credential the credential to set
	 */
	public void setCredential(Credential credential) {
		this.credential = credential;
	}


	public String toString() {
		return "User [nom=" + nom + ", prenom=" + prenom + ", age=" + age + ", phoneNumber=" + phoneNumber + ", sexe="
				+ sexe + ", birthDate=" + birthDate + ", dateInscription=" + dateInscription + ", connected="
				+ connected + ", blocked=" + blocked + ", credential=" + credential + "]";
	}
		
}
