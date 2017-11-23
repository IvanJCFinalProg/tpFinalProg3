package cal.tpfinal.bean;

public class Admin {
	
	private String nom;
	private String prenom;
	protected Credential credential;
	private static volatile Admin admin = null;
	
	public final static Admin getInstance() {
		if (Admin.admin == null) {
            synchronized(Admin.class) {
              if (Admin.admin == null) {
                Admin.admin = new Admin();
              }
            }
         }
         return Admin.admin;
	}
}
