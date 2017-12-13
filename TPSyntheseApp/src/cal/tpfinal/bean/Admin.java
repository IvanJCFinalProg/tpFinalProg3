package cal.tpfinal.bean;

import java.util.List;

public class Admin {
	
	private static volatile Admin admin = null;
	
	private static List<Publication> listPublicationsToDelete;
	private static List<Commentaire> listCommentsToDelete;
	
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

	public static List<Publication> getListPublicationsToDelete() {
		return listPublicationsToDelete;
	}

	public static void setListPublicationsToDelete(List<Publication> listPublicationsToDelete) {
		Admin.listPublicationsToDelete = listPublicationsToDelete;
	}

	public static List<Commentaire> getListCommentsToDelete() {
		return listCommentsToDelete;
	}

	public static void setListCommentsToDelete(List<Commentaire> listCommentsToDelete) {
		Admin.listCommentsToDelete = listCommentsToDelete;
	}
	
	
}
