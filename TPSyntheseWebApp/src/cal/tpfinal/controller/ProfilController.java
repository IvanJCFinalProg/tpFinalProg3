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
		int idUser = Integer.valueOf(request.getParameter("idUser"));	
		int idProfil = Integer.valueOf(request.getParameter("idAfficher"));
		User user = ServiceUser.getUserById(idProfil, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
		request.setAttribute("idUser", idUser);
		request.setAttribute("idAfficher", idProfil);
		
		try {
			if(action.equalsIgnoreCase("publier")) {
				String content = request.getParameter("publication");
				if(!content.isEmpty()) {
					ServicePublication.addPublication(user.getFeed(), new Publication(content, idUser));
					ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				}
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("commenter")) {
				int idPublication = Integer.valueOf(request.getParameter("idPublication"));
				String content = request.getParameter("commentaire");
				
				if(content!=null && !content.isEmpty()) {
					List<Publication> feed = user.getFeed();
					Publication p = ServicePublication.getPublicationById(feed, idPublication);
					ServiceCommentaire.addCommentaire(p.getListeCommentaires(), new Commentaire(content, idUser, idPublication));
					ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				}
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("supprimerPublication")) {
				int idPublication = Integer.parseInt(request.getParameter("idPubli"));
				
				ServicePublication.removePublication(user.getFeed(), ServicePublication.getPublicationById(user.getFeed(), idPublication));
				ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=afficherProfil");
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("supprimerCommentaire")) {
				int idPublication = Integer.parseInt(request.getParameter("idPubli"));
				int idCommentaire = Integer.parseInt(request.getParameter("idCommentaire"));
				
				Publication publi = ServicePublication.getPublicationById(user.getFeed(), idPublication);
				ServiceCommentaire.removeCommentaire(ServicePublication.getPublicationById(user.getFeed(), idPublication).getListeCommentaires(), ServiceCommentaire.getCommentaireById(publi.getListeCommentaires(), idCommentaire));
				
				ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);

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
