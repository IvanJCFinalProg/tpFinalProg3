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
	
	public Commentaire(String content, int id_User, int id_publication) {
		this.id = compteur;
		compteur++;
		this.date_publication = DF.format(Calendar.getInstance().getTime());
		this.id_User = id_User;
		this.id_Publication = id_publication;
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
