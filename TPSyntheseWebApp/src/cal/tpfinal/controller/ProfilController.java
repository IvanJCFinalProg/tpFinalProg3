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
 * Servlet implementation class ProfilController
 */
@WebServlet("/ProfilController")
public class ProfilController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(ProfilController.class);
	
	public void init(ServletConfig config) throws ServletException {
		logger.info("Initialisation de l'application");
		try {
			if(!(ServicePublication.loadListePublication("C:/appBasesDonnees/tableFeed.xml")!= null)) {
				Publication.setCompteur(Integer.valueOf(ServiceApp.getValue("5", 1))+1);
				Commentaire.setCompteur(Integer.valueOf(ServiceApp.getValue("6", 1))+1);
			}	
		} catch (Exception e) {
			logger.error(ProfilController.class.getName()+" | Probleme - Function init(ProfilControler) - Initialisation des donnees");
			logger.debug(e.getMessage() +" "+e.getLocalizedMessage());
		}finally {
			logger.info("Fin de l'initialisation");
		}
		
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		int idUser = (Integer)(session.getAttribute("idUser"));
		
		int idProfil = (Integer)(session.getAttribute("idAfficher"));
		User user = ServiceUser.getUserById(idProfil, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
		User userAuth = ServiceUser.getUserById(idUser, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
		request.setAttribute("idUser", idUser);
		session.setAttribute("idUser", idUser);
		session.setAttribute("idAfficher", idProfil);
		request.setAttribute("idAfficher", idProfil);
		List<Publication> feedAccueil = (List<Publication>)ServicePublication.loadListePublication("C:/appBasesDonnees/tableFeed.xml");
		
		try {
			if(action.equalsIgnoreCase("publier")) {
				String content = request.getParameter("publication");
				if(!content.isEmpty()) {
					Publication p = new Publication(content, userAuth);
					ServicePublication.addPublication(user.getFeed(), p);
					ServicePublication.addPublication(feedAccueil, p);
					ServicePublication.saveListePublication("C:/appBasesDonnees/tableFeed.xml", feedAccueil);
					ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
					ServiceApp.setValue("5", String.valueOf(p.getId()), 1);
				}
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("commenter")) {
				int idPublication = Integer.valueOf(request.getParameter("idPublication"));
				String content = request.getParameter("commentaire");
				
				if(content!=null && !content.isEmpty()) {
					Publication p = ServicePublication.getPublicationById(user.getFeed(), idPublication);
					Commentaire c = new Commentaire(content, userAuth, idPublication);
					
					ServiceCommentaire.addCommentaire(ServicePublication.getPublicationById(feedAccueil, idPublication).getListeCommentaires(), c);
					ServiceCommentaire.addCommentaire(p.getListeCommentaires(), c);
					ServicePublication.saveListePublication("C:/appBasesDonnees/tableFeed.xml", feedAccueil);
					ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
					ServiceApp.setValue("6", String.valueOf(c.getId()), 1);
				}
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("supprimerPublication")) {
				int idPublication = Integer.parseInt(request.getParameter("idPubli"));
				
				ServicePublication.removePublication(user.getFeed(), ServicePublication.getPublicationById(user.getFeed(), idPublication));
				ServicePublication.removePublication(feedAccueil, ServicePublication.getPublicationById(feedAccueil, idPublication));
				ServicePublication.saveListePublication("C:/appBasesDonnees/tableFeed.xml", feedAccueil);
				ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil");
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

				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("ajouterAmi")) {
				ServiceUser.addFriend(user, userAuth.getListeAmi());
				ServiceUser.addFriend(userAuth, user.getListeAmi());
				ServiceUser.saveUser(ServiceApp.getValue("2", 2), userAuth);
				ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
				request.setAttribute("idUser", idUser);
				request.setAttribute("idAfficher", idProfil);
				//logger.info(idProfil+"");
				session.setAttribute("idAfficher", idProfil);
				session.setAttribute("idUser", idUser);
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil&idUser="+idUser+"&idAfficher="+idProfil+"");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("enleverAmi")) {
				int idRemove;
				if(request.getParameter("idRemove") != null) {
					logger.info(request.getParameter("idRemove"));
					idRemove = Integer.parseInt(request.getParameter("idRemove"));
				}else {
					idRemove = idProfil;
				}
				user = ServiceUser.getAmiById(idRemove, userAuth.getListeAmi());
				ServiceUser.removeFriend(userAuth, user.getListeAmi());
				ServiceUser.removeFriend(user, userAuth.getListeAmi());
				ServiceUser.saveUser(ServiceApp.getValue("2", 2), userAuth);
				ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
				request.setAttribute("idUser", idUser);
				request.setAttribute("idAfficher", idProfil);
				//logger.info(idProfil+"");
				session.setAttribute("idAfficher", idProfil);
				session.setAttribute("idUser", idUser);
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil&idUser="+idUser+"&idAfficher="+idProfil+"");
				dispatcher.forward(request, response);
			}
			
			
		}catch (Exception e) {
			//logger.error(LoginController.class.getName()+" Erreur dans la fonction processRequest()");
			//logger.debug(e.getMessage());
		}
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
