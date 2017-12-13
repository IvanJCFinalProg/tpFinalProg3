package cal.tpfinal.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cal.tpfinal.bean.Commentaire;
import cal.tpfinal.bean.Credential;
import cal.tpfinal.bean.Publication;
import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServiceApp;
import cal.tpfinal.model.ServiceCommentaire;
import cal.tpfinal.model.ServiceConnection;
import cal.tpfinal.model.ServicePublication;
import cal.tpfinal.model.ServiceUser;
import cal.tpfinal.util.IServiceUtils;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(AdminController.class);
    
	
	public void init(ServletConfig config) throws ServletException {
		
	}
	
	public void destroy() {
		
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter(ServiceApp.getValue("1", 3));
		try {
			Map<Integer, User> mapUsers = null; int idUser = -1;
			if(request.getParameter(ServiceApp.getValue("5", 3))!=null) {
				mapUsers = ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2));
				idUser = Integer.valueOf(request.getParameter(ServiceApp.getValue("5", 3)));
			}
			if(action.equals(ServiceApp.getValue("2", 3))) {
				ServiceUser.blockUser(idUser, mapUsers);
				ServiceUser.saveToXML(mapUsers, ServiceApp.getValue("2", 2));
				RequestDispatcher dispatcher = request.getRequestDispatcher(IServiceUtils.REDIRECTION_ADMIN);
				dispatcher.forward(request, response);
			}
			else if(action.equals(ServiceApp.getValue("3", 3))) {
				// Si je supprime le dernier id, la propertie dans le file doit changer
				if(idUser==Integer.valueOf(ServiceApp.getValue("1", 1)))
					ServiceApp.setValue("1", String.valueOf(Integer.valueOf(ServiceApp.getValue("1", 1))-1), 1);
				Map<Integer, Credential> tableLogins = ServiceConnection.loadMapCredentials(ServiceApp.getValue("3", 2));
				ServiceUser.deleteUser(mapUsers.get(idUser), mapUsers);
				ServiceConnection.deleteCredential(tableLogins.get(idUser), tableLogins);
				ServiceUser.saveToXML(mapUsers, ServiceApp.getValue("2", 2));
				ServiceConnection.saveMapCredentials(ServiceApp.getValue("3", 2), tableLogins);
				List<Publication> feedAccueil = (List<Publication>)ServicePublication.loadListePublication("C:/appBasesDonnees/tableFeed.xml");
				for(Publication p : feedAccueil) {
					if(p.getId_User() == idUser) {
						ServicePublication.removePublication(feedAccueil, ServicePublication.getPublicationById(feedAccueil, p.getId()));
						ServicePublication.saveListePublication("C:/appBasesDonnees/tableFeed.xml", feedAccueil);
					}else {
						for(Commentaire c : p.getListeCommentaires()) {
							if(c.getId_User() == idUser) {
								Publication publiFeed = ServicePublication.getPublicationById(feedAccueil, p.getId());
								ServiceCommentaire.removeCommentaire(ServicePublication.getPublicationById(feedAccueil, p.getId()).getListeCommentaires(), ServiceCommentaire.getCommentaireById(publiFeed.getListeCommentaires(), c.getId()));
								ServicePublication.saveListePublication("C:/appBasesDonnees/tableFeed.xml", feedAccueil);
							}
						}
					}
				}
				ServicePublication.saveListePublication("C:/appBasesDonnees/tableFeed.xml", feedAccueil);
				for(User user : mapUsers.values()) {
					for(User ami : user.getListeAmi()) {
						if(ami.getCredential().getId() == idUser) {
							ServiceUser.removeFriend(ami, user.getListeAmi());
							ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
							break;
						}
					}
					for(Publication p : user.getFeed()) {
						if(p.getId_User() == idUser) {
							ServicePublication.removePublication(user.getFeed(), ServicePublication.getPublicationById(user.getFeed(), p.getId()));
							ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
						}else {
							for(Commentaire c : p.getListeCommentaires()) {
								if(c.getId_User() == idUser) {
									Publication publi = ServicePublication.getPublicationById(user.getFeed(), p.getId());
									ServiceCommentaire.removeCommentaire(ServicePublication.getPublicationById(user.getFeed(), p.getId()).getListeCommentaires(), ServiceCommentaire.getCommentaireById(publi.getListeCommentaires(), c.getId()));
									ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
								}
							}
						}
					}
				}
				ServiceUser.saveToXML(mapUsers, ServiceApp.getValue("2", 2));
				RequestDispatcher dispatcher = request.getRequestDispatcher(IServiceUtils.REDIRECTION_ADMIN);
				dispatcher.forward(request, response);
			}
			else if(action.equals(ServiceApp.getValue("4", 3))) {
				//request.getRequestDispatcher("UserController?action=accueil").forward(request, response);
			}
			else if(action.equals(ServiceApp.getValue("8", 3))) {
				response.sendRedirect(ServiceApp.getValue("1", 2));
			}
			else if(action.equals(ServiceApp.getValue("7", 3))) {
				request.setAttribute("mapUsers", mapUsers);
				RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("4", 2));
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			logger.error(AdminController.class.getName()+" Erreur dans la fonction processRequest()");
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
