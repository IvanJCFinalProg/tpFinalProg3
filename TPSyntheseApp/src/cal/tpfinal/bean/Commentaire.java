package cal.tpfinal.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Commentaire {
	private static final DateFormat DF = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private static int compteur = 1;
	private String content;
	private String date_publication;
	private int id;
	private int id_Publication;
	private int id_User;
	private User user;
	
	public Commentaire(String content, User user, int id_publication) {
		this.id = compteur;
		compteur++;
		this.content = content;
		this.date_publication = DF.format(Calendar.getInstance().getTime());
		this.user = user;
		this.id_Publication = id_publication;
		this.id_User = user.getCredential().getId();
	}
	
	public static void setCompteur(int compteur) {
		Commentaire.compteur= compteur;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getContent() {
		return content;
	}

	public int getId() {
		return id;
	}
	
	public int getId_User() {
		return id_User;
	}
	
	public int getId_Publication() {
		return id_Publication;
	}

	public String getDate_publication() {
		return date_publication;
	}
	
}
