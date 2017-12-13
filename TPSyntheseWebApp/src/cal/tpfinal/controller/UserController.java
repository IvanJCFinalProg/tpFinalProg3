package cal.tpfinal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cal.tpfinal.bean.Commentaire;
import cal.tpfinal.bean.Publication;
import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServiceApp;
import cal.tpfinal.model.ServiceCommentaire;
import cal.tpfinal.model.ServicePublication;
import cal.tpfinal.model.ServiceUser;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(UserController.class);
	private final String AFFICHER_ACCUEIL = "LoginController?action=accueil";
	
	public void init(ServletConfig config) throws ServletException {
		logger.info("Initialisation de l'application");
		try {
			if(!(ServicePublication.loadListePublication("C:/appBasesDonnees/tableFeed.xml")!= null)) {
				Publication.setCompteur(Integer.valueOf(ServiceApp.getValue("5", 1))+1);
				Commentaire.setCompteur(Integer.valueOf(ServiceApp.getValue("6", 1))+1);
			}	
		} catch (Exception e) {
			logger.error(UserController.class.getName()+" | Probleme - Function init(UserControler) - Initialisation des donnees");
			logger.debug(e.getMessage() +" "+e.getLocalizedMessage());
		}finally {
			logger.info("Fin de l'initialisation");
		}
		
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		int idUser = Integer.parseInt(request.getParameter("idUser"));
		User user = ServiceUser.getUserById(idUser, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
		List<Publication> feedAccueil = (List<Publication>)ServicePublication.loadListePublication("C:/appBasesDonnees/tableFeed.xml");
		try {
			if(action.equalsIgnoreCase("publier")) {
				String content = request.getParameter("publication");
				
				if(!content.isEmpty()) {
					Publication p = new Publication(content, user);
					ServicePublication.addPublication(user.getFeed(), p);
					ServicePublication.addPublication(feedAccueil, p);
					ServicePublication.saveListePublication("C:/appBasesDonnees/tableFeed.xml", feedAccueil);
					ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
					ServiceApp.setValue("5", String.valueOf(p.getId()), 1);
				}
				session.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2))));
				RequestDispatcher dispatcher = request.getRequestDispatcher(AFFICHER_ACCUEIL);
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("commenter")) {
				
				int idPublication = Integer.valueOf(request.getParameter("idPublication"));
				int idUserPublication = Integer.valueOf(request.getParameter("idUserPublication"));
				User publicateur = ServiceUser.getUserById(idUserPublication, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
				String content = request.getParameter("commentaire");
				
				if(request.getParameter("commentaire")!=null && !content.isEmpty()) {
					Publication p = ServicePublication.getPublicationById(publicateur.getFeed(), idPublication);
					Commentaire c = new Commentaire(content, user, idPublication);
					
					ServiceCommentaire.addCommentaire(ServicePublication.getPublicationById(feedAccueil, idPublication).getListeCommentaires(), c);
					ServiceCommentaire.addCommentaire(p.getListeCommentaires(), c);
					ServicePublication.saveListePublication("C:/appBasesDonnees/tableFeed.xml", feedAccueil);
					ServiceApp.setValue("6", String.valueOf(c.getId()), 1);
					ServiceUser.saveUser(ServiceApp.getValue("2", 2), publicateur);
				}
				session.setAttribute("feedAccueil", (List<Publication>)ServicePublication.loadListePublication("C:/appBasesDonnees/tableFeed.xml"));
				session.setAttribute("user", ServiceUser.getUserById(idUser, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2))));
			//	request.setAttribute("user", ServiceUser.getUserById(idUser, ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(AFFICHER_ACCUEIL);
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("supprimerPublication")) {
				int idPublication = Integer.parseInt(request.getParameter("idPubli"));
				
				ServicePublication.removePublication(feedAccueil, ServicePublication.getPublicationById(feedAccueil, idPublication));
				ServicePublication.saveListePublication("C:/appBasesDonnees/tableFeed.xml", feedAccueil);
				ServicePublication.removePublication(user.getFeed(), ServicePublication.getPublicationById(user.getFeed(), idPublication));
				ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
				session.setAttribute("user", ServiceUser.getUserById(idUser, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2))));
			//	request.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(AFFICHER_ACCUEIL);
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("supprimerCommentaire")) {
				int idPublication = Integer.parseInt(request.getParameter("idPubli"));
				int idCommentaire = Integer.parseInt(request.getParameter("idCommentaire"));
				
				Publication publiFeed = ServicePublication.getPublicationById(feedAccueil, idPublication);
				ServiceCommentaire.removeCommentaire(ServicePublication.getPublicationById(feedAccueil, idPublication).getListeCommentaires(), ServiceCommentaire.getCommentaireById(publiFeed.getListeCommentaires(), idCommentaire));
				ServicePublication.saveListePublication("C:/appBasesDonnees/tableFeed.xml", feedAccueil);
				
				Publication publi = ServicePublication.getPublicationById(user.getFeed(), idPublication);
				ServiceCommentaire.removeCommentaire(ServicePublication.getPublicationById(user.getFeed(), idPublication).getListeCommentaires(), ServiceCommentaire.getCommentaireById(publi.getListeCommentaires(), idCommentaire));
				ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
				session.setAttribute("user", ServiceUser.getUserById((Integer)session.getAttribute("idAfficher"), ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2))));
			//	request.setAttribute("user", ServiceUser.getUserById((Integer)session.getAttribute("idAfficher"), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(AFFICHER_ACCUEIL);
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("afficherProfil")) {
				int idProfil = Integer.parseInt(request.getParameter("idAfficher"));
				User profil = ServiceUser.getUserById(idProfil, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
				session.setAttribute("amitie", (ServiceUser.getAmiById(idProfil, user.getListeAmi()) == null)? false:true);
				session.setAttribute("user", user);
				session.setAttribute("profil", profil);
				//request.setAttribute("user", user);
				//request.setAttribute("profil", profil);
				
				response.sendRedirect("profil.jsp");
			}
			
			
		}catch (Exception e) {
			logger.error(UserController.class.getName()+" Erreur dans la fonction processRequest()");
			logger.debug(e.getMessage());
		}
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
