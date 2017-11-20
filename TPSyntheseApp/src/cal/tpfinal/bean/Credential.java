package cal.tpfinal.bean;

public class Credential {
	
	private int idUser;
	private String email;
	private String password;
	
	public Credential(int id) {
		this.idUser = id;
	}	
	

	/**
	 * @param idUser the idUser to set
	 */
	public void setId(int idUser) {
		this.idUser = idUser;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return idUser;
	}

	/**
	 * @return the username
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the username to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Credential [id=" + idUser + ", username=" + email + ", password=" + password + "]";
	}
}
