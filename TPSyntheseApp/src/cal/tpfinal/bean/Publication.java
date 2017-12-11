package cal.tpfinal.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Publication {
	private static final DateFormat DF = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private List<Commentaire> listeCommentaires;
	private static int compteur = 1;
	private String content;
	private String date_publication;
	private String date_modification;
	private int id;
	private int id_User;
	private User user;
	
	public Publication(String content, int id_User) {
		this.id = compteur;
		compteur++;
		this.content = content;
		this.listeCommentaires = new ArrayList<Commentaire>();
		this.date_publication = DF.format(Calendar.getInstance().getTime());
		this.id_User = id_User;
	}
	
	public Publication(String content, User user) {
		this.id = compteur;
		compteur++;
		this.content = content;
		this.listeCommentaires = new ArrayList<Commentaire>();
		this.date_publication = DF.format(Calendar.getInstance().getTime());
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public List<Commentaire> getListeCommentaires() {
		return listeCommentaires;
	}

	public void setListeCommentaires(List<Commentaire> listeCommentaires) {
		this.listeCommentaires = listeCommentaires;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		this.date_modification = DF.format(Calendar.getInstance().getTime());
	}

	public int getId() {
		return id;
	}
	
	public int getId_User() {
		return id_User;
	}
	
	public String getDate_publication() {
		return date_publication;
	}

	public String getDate_modification() {
		return date_modification;
	}

	public static void main(String[] args) {
		Publication p = new Publication("Potato", 0);
		System.out.println(p.getDate_publication());
		 DateTime dt = new DateTime();
		 DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
		 String str = fmt.print(dt);
		 System.out.println(str);
	}
}

