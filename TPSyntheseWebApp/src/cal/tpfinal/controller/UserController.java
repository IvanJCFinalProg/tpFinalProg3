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
	
	/*public void init(ServletConfig config) throws ServletException {
		logger.info("Initialisation de l'application");
		try {
			if(!(ServiceUser.fromToXML(ServiceApp.getValue("3",2))!= null)) {
				User.setCompteur(Integer.valueOf(ServiceApp.getValue("1", 1))+1);
			}	
		} catch (Exception e) {
			logger.error("Probl�me - Function init(LoginControler) - Initialisation des donn�es");
			logger.debug(e.getMessage() +" "+e.getLocalizedMessage());
		}finally {
			logger.info("Fin de l'initialisation");
		}
		
	}*/
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		try {
			if(action.equalsIgnoreCase("publier")) {
				int id = Integer.valueOf(request.getParameter("idUser"));	
				//int idFeed = Integer.valueOf(request.getParameter("idProfil"));
				User user;
				//if( id == idFeed) {
					user =ServiceUser.getUserById(id, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				/*}else {
					user =ServiceUser.getUserById(idFeed, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				}*/
				String texte_publier = request.getParameter("publication");
				if(request.getParameter("publi")!=null && !texte_publier.isEmpty()) {
					List<Publication> liste = user.getFeed();
					
					//logger.info(texte_publier);
					ServicePublication.addPublication(liste, new Publication(texte_publier, id));//(id!=idFeed)? id:idFeed));
					user.setFeed(liste);
					ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				}
				session.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				request.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				//response.sendRedirect(ServiceApp.getValue("5", 2));
				RequestDispatcher dispatcher = request.getRequestDispatcher("LoginController?action=accueil");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("commenter")) {
				int idPublication = Integer.valueOf(request.getParameter("idPublication"));
				int idUser = Integer.valueOf(request.getParameter("idUser"));
				int idUserPublication = Integer.valueOf(request.getParameter("idUserPublication"));
				String content = request.getParameter("commentaire");
				User user = ServiceUser.getUserById(idUserPublication, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				
				if(request.getParameter("commentaire")!=null && !content.isEmpty()) {
					List<Publication> feed = user.getFeed();
					Publication p = ServicePublication.getPublicationById(feed, idPublication);
					
					ServiceCommentaire.addCommentaire(p.getListeCommentaires(), new Commentaire(content, idUser, idPublication));
					ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				}
				session.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				request.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				RequestDispatcher dispatcher = request.getRequestDispatcher("LoginController?action=accueil");
				dispatcher.forward(request, response);
			}else if(action.equalsIgnoreCase("supprimerPublication")) {
				int id = Integer.parseInt(request.getParameter("idPubli"));
				int idUser=Integer.parseInt(request.getParameter("idUser"));
				
				User user = ServiceUser.getUserById(idUser, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				ServicePublication.removePublication(user.getFeed(), ServicePublication.getPublicationById(user.getFeed(), id));
				ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				
				session.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				request.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("LoginController?action=accueil");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("supprimerCommentaire")) {
				int idPublication = Integer.parseInt(request.getParameter("idPubli"));
				int idUser = Integer.parseInt(request.getParameter("idUserPublication"));
				int idCommentaire = Integer.parseInt(request.getParameter("idCommentaire"));
				
				User user = ServiceUser.getUserById(idUser, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				Publication publi = ServicePublication.getPublicationById(user.getFeed(), idPublication);
				ServiceCommentaire.removeCommentaire(ServicePublication.getPublicationById(user.getFeed(), idPublication).getListeCommentaires(), ServiceCommentaire.getCommentaireById(publi.getListeCommentaires(), idCommentaire));
				
				ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				
				session.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				request.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("LoginController?action=accueil");
				dispatcher.forward(request, response);
			}else if(action.equalsIgnoreCase("afficherProfil")) {
				int idProfil;
				int idUser;
				/*if(request.getParameter("idAfficher") == null) {
					idProfil = (Integer)(session.getAttribute("idAfficher"));
					idUser = (Integer)(session.getAttribute("idUser"));*/
				//}else {
					idProfil = Integer.parseInt(request.getParameter("idAfficher"));
					idUser = Integer.parseInt(request.getParameter("idUser"));
				/*}*/
				
				
				User profil = ServiceUser.getUserById(idProfil, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				User user = ServiceUser.getUserById(idUser, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				
				session.setAttribute("user", user);
				session.setAttribute("profil", profil);
				//request.setAttribute("user", user);
				//request.setAttribute("profil", profil);
				
				response.sendRedirect("profil.jsp");
				//RequestDispatcher dispatcher = request.getRequestDispatcher("profil.jsp");
				//dispatcher.forward(request, response);
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
