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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServiceApp;
import cal.tpfinal.model.ServiceUser;

/**
 * Servlet implementation class RechercheContoller
 */
@WebServlet("/RechercheController")
public class RechercheController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(RechercheController.class);
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		int idUser = Integer.parseInt(request.getParameter("idUser"));
		User user = ServiceUser.getUserById(idUser, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
		
		try {
			if(action.equalsIgnoreCase("rechercher")) {
				String tag = request.getParameter("tagRecherche");
				List<User> listeRecherche = ServiceUser.getUsersByTag(tag, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
				
				session.setAttribute("listeRecherche", listeRecherche);
				RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("10",3));
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase("afficherPageRecherche")) {
				session.setAttribute("user", user);
				response.sendRedirect(ServiceApp.getValue("10",2));
			}
		} catch (Exception e) {
			logger.error(RechercheController.class.getName()+" Erreur dans la fonction processRequest()");
			logger.debug(e.getMessage());
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
		processRequest(request, response);
	}

}
