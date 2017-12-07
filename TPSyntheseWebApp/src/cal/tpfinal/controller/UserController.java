package cal.tpfinal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cal.tpfinal.bean.Commentaire;
import cal.tpfinal.bean.Publication;
import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServiceApp;
import cal.tpfinal.model.ServiceCommentaire;
import cal.tpfinal.model.ServicePublication;
import cal.tpfinal.model.ServiceUser;
import cal.tpfinal.util.IService;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		PrintWriter writer = response.getWriter();
		
		try {
			if(action.equalsIgnoreCase("publier")) {
				int id = Integer.valueOf(request.getParameter("idUser"));
				User user = ServiceUser.getUserById(id, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				String texte_publier = request.getParameter("publication");
				if(request.getParameter("publi")!=null) {
					List<Publication> liste = user.getFeed();
					
					//logger.info(texte_publier);
					ServicePublication.addPublication(liste, new Publication(texte_publier, id));
					user.setFeed(liste);
					ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				}
				request.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				//response.sendRedirect(ServiceApp.getValue("5", 2));
				RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("5", 2));
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("commenter")) {
				int idPublication = Integer.valueOf(request.getParameter("idPublication"));
				int idUser = Integer.valueOf(request.getParameter("idUser"));
				int idUserPublication = Integer.valueOf(request.getParameter("idUserPublication"));
				String content = request.getParameter("commentaire");
				User user = ServiceUser.getUserById(idUserPublication, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				List<Publication> feed = user.getFeed();
				Publication p = ServicePublication.getPublicationById(feed, idPublication);
				ServiceCommentaire.addCommentaire(p.getListeCommentaires(), new Commentaire(content, idUser, idPublication));
				ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				request.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				RequestDispatcher dispatcher = request.getRequestDispatcher("LoginController?action=accueil");
				dispatcher.forward(request, response);
			}else if(action.equalsIgnoreCase("supprimerPublication")) {
				int id = Integer.parseInt(request.getParameter("idPubli"));
				int idUser=Integer.parseInt(request.getParameter("idUser"));
				
				User user = ServiceUser.getUserById(idUser, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				ServicePublication.removePublication(user.getFeed(), ServicePublication.getPublicationById(user.getFeed(), id));
				ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				
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
				request.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
				RequestDispatcher dispatcher = request.getRequestDispatcher("LoginController?action=accueil");
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
