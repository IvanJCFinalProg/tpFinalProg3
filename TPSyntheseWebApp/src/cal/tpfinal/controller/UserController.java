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
	
	public void init(ServletConfig config) throws ServletException {
		logger.info("Initialisation de l'application");
		try {
			
		} catch (Exception e) {
			logger.error(UserController.class.getName()+" | Probleme - Function init(LoginControler) - Initialisation des donneees");
			logger.debug(e.getMessage() +" "+e.getLocalizedMessage());
		}finally {
			logger.info("Fin de l'initialisation");
		}
		
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		try {
			if(action.equalsIgnoreCase("publier")) {
				int idUser = Integer.valueOf(request.getParameter("idUser"));	
				User user=ServiceUser.getUserById(idUser, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				String content = request.getParameter("publication");
				
				if(!content.isEmpty()) {
					ServicePublication.addPublication(user.getFeed(), new Publication(content, user));//(id!=idFeed)? id:idFeed));
					ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				}
				session.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				request.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("LoginController?action=accueil");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("commenter")) {
				int idPublication = Integer.valueOf(request.getParameter("idPublication"));
				int idUser = Integer.valueOf(request.getParameter("idUser"));
				int idUserPublication = Integer.valueOf(request.getParameter("idUserPublication"));
				String content = request.getParameter("commentaire");
				User user = ServiceUser.getUserById(idUser, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));//Publication, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				
				if(request.getParameter("commentaire")!=null && !content.isEmpty()) {
					Publication p = ServicePublication.getPublicationById(user.getFeed(), idPublication);
					ServiceCommentaire.addCommentaire(p.getListeCommentaires(), new Commentaire(content, user, idPublication));
					ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				}
				session.setAttribute("user", ServiceUser.getUserById(idUser, ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				request.setAttribute("user", ServiceUser.getUserById(idUser, ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				
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
				
			}else if(action.equalsIgnoreCase("supprimerCommentaire")) {//A modifier!!
				int idPublication = Integer.parseInt(request.getParameter("idPubli"));
				int idUser = Integer.parseInt(request.getParameter("idUser"));//Publication"));
				int idCommentaire = Integer.parseInt(request.getParameter("idCommentaire"));
				
				User user = ServiceUser.getUserById(idUser, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				Publication publi = ServicePublication.getPublicationById(user.getFeed(), idPublication);
				ServiceCommentaire.removeCommentaire(ServicePublication.getPublicationById(user.getFeed(), idPublication).getListeCommentaires(), ServiceCommentaire.getCommentaireById(publi.getListeCommentaires(), idCommentaire));
				ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				
				session.setAttribute("user", ServiceUser.getUserById((Integer)session.getAttribute("idAfficher"), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				request.setAttribute("user", ServiceUser.getUserById((Integer)session.getAttribute("idAfficher"), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("LoginController?action=accueil");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("afficherProfil")) {
				int idProfil = Integer.parseInt(request.getParameter("idAfficher"));
				int idUser = Integer.parseInt(request.getParameter("idUser"));
				
				User profil = ServiceUser.getUserById(idProfil, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				User user = ServiceUser.getUserById(idUser, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				
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
