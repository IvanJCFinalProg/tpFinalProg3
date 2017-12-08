package cal.tpfinal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
       
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		
		try {
			if(action.equalsIgnoreCase("publier")) {
				int idUser = Integer.valueOf(request.getParameter("idUser"));	
				int idProfil = Integer.valueOf(request.getParameter("idAfficher"));
				User user;
				if( idUser == idProfil) {
					user =ServiceUser.getUserById(idUser, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				}else {
					user =ServiceUser.getUserById(idProfil, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				}
				String texte_publier = request.getParameter("publication");
				if(request.getParameter("publi")!=null && !texte_publier.isEmpty()) {
					List<Publication> liste = user.getFeed();
					
					//logger.info(texte_publier);
					ServicePublication.addPublication(liste, new Publication(texte_publier, (idUser!=idProfil)? idUser:idProfil));
					user.setFeed(liste);
					ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				}
			
				request.setAttribute("idAfficher", idProfil);
				request.setAttribute("idUser", idUser);
				//response.sendRedirect("UserController?action=afficherProfil");
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("commenter")) {
				int idPublication = Integer.valueOf(request.getParameter("idPublication"));
				int idUser = Integer.valueOf(request.getParameter("idUser"));
				int idProfil = Integer.valueOf(request.getParameter("idAfficher"));
				String content = request.getParameter("commentaire");
				
				User user = ServiceUser.getUserById(idProfil, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				
				if(request.getParameter("commentaire")!=null && !content.isEmpty()) {
					List<Publication> feed = user.getFeed();
					Publication p = ServicePublication.getPublicationById(feed, idPublication);
					
					ServiceCommentaire.addCommentaire(p.getListeCommentaires(), new Commentaire(content, idUser, idPublication));
					ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				}
				
				request.setAttribute("idAfficher", idProfil);
				request.setAttribute("idUser", idUser);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil");
				dispatcher.forward(request, response);
			}else if(action.equalsIgnoreCase("supprimerPublication")) {
				int id = Integer.parseInt(request.getParameter("idPubli"));
				int idUser=Integer.parseInt(request.getParameter("idUser"));
				int idProfil = Integer.parseInt(request.getParameter("idAfficher"));
				
				User user = ServiceUser.getUserById(idProfil, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				ServicePublication.removePublication(user.getFeed(), ServicePublication.getPublicationById(user.getFeed(), id));
				ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				
				request.setAttribute("idAfficher", idProfil);
				request.setAttribute("idUser", idUser);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("supprimerCommentaire")) {
				int idPublication = Integer.parseInt(request.getParameter("idPubli"));
				int idUser = Integer.parseInt(request.getParameter("idUser"));
				int idCommentaire = Integer.parseInt(request.getParameter("idCommentaire"));
				int idProfil = Integer.parseInt(request.getParameter("idAfficher"));
				
				User user = ServiceUser.getUserById(idProfil, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
				Publication publi = ServicePublication.getPublicationById(user.getFeed(), idPublication);
				ServiceCommentaire.removeCommentaire(ServicePublication.getPublicationById(user.getFeed(), idPublication).getListeCommentaires(), ServiceCommentaire.getCommentaireById(publi.getListeCommentaires(), idCommentaire));
				
				ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				
				request.setAttribute("idUser", idUser);
				request.setAttribute("idAfficher", idProfil);
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil");
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