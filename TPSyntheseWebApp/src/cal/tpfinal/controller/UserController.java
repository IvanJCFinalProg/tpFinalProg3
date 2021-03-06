package cal.tpfinal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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
import cal.tpfinal.bean.Credential;
import cal.tpfinal.bean.Publication;
import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServiceApp;
import cal.tpfinal.model.ServiceCommentaire;
import cal.tpfinal.model.ServiceConnection;
import cal.tpfinal.model.ServicePassword;
import cal.tpfinal.model.ServicePublication;
import cal.tpfinal.model.ServiceUser;
import cal.tpfinal.util.ServiceValidation;

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
			ServiceValidation.getMapErreurs().clear();
			if((ServicePublication.loadListePublication(ServiceApp.getValue("9",2))!= null)) {
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
		String action = request.getParameter(ServiceApp.getValue("1", 3));
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		int idUser = Integer.parseInt(request.getParameter("idUser"));
		User user = ServiceUser.getUserById(idUser, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
		List<Publication> feedAccueil = (List<Publication>)ServicePublication.loadListePublication(ServiceApp.getValue("9",2));
		try {
			if(action.equalsIgnoreCase(ServiceApp.getValue("17", 3))) {
				String content = new String((request.getParameter("publication")+"").getBytes(), "UTF-8");
				if(!content.isEmpty()) {
					Publication p = new Publication(content, user);
					ServicePublication.addPublication(user.getFeed(), p);
					ServicePublication.addPublication(feedAccueil, p);
					ServicePublication.saveListePublication(ServiceApp.getValue("9",2), feedAccueil);
					ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
					ServiceApp.setValue("5", String.valueOf(p.getId()), 1);
				}
				session.setAttribute("user", ServiceUser.getUserById(user.getCredential().getId(), ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2))));
				RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("11",3));
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase(ServiceApp.getValue("16", 3))) {
				
				int idPublication = Integer.valueOf(request.getParameter("idPublication"));
				int idUserPublication = Integer.valueOf(request.getParameter("idUserPublication"));
				User publicateur = ServiceUser.getUserById(idUserPublication, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
				String content = new String((request.getParameter("commentaire")+"").getBytes(), "UTF-8");
				
				if(request.getParameter("commentaire")!=null && !content.isEmpty()) {
					Publication p = ServicePublication.getPublicationById(publicateur.getFeed(), idPublication);
					Commentaire c = new Commentaire(content, user, idPublication);
					
					ServiceCommentaire.addCommentaire(ServicePublication.getPublicationById(feedAccueil, idPublication).getListeCommentaires(), c);
					ServiceCommentaire.addCommentaire(p.getListeCommentaires(), c);
					ServicePublication.saveListePublication(ServiceApp.getValue("9",2), feedAccueil);
					ServiceApp.setValue("6", String.valueOf(c.getId()), 1);
					ServiceUser.saveUser(ServiceApp.getValue("2", 2), publicateur);
				}
				session.setAttribute("feedAccueil", (List<Publication>)ServicePublication.loadListePublication(ServiceApp.getValue("9",2)));
				session.setAttribute("user", ServiceUser.getUserById(idUser, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2))));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("11",3));
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase(ServiceApp.getValue("14", 3))) {
				int idPublication = Integer.parseInt(request.getParameter("idPubli"));
				
				ServicePublication.removePublication(feedAccueil, ServicePublication.getPublicationById(feedAccueil, idPublication));
				ServicePublication.saveListePublication(ServiceApp.getValue("9",2), feedAccueil);
				ServicePublication.removePublication(user.getFeed(), ServicePublication.getPublicationById(user.getFeed(), idPublication));
				ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
				session.setAttribute("user", ServiceUser.getUserById(idUser, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2))));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("11",3));
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase(ServiceApp.getValue("13", 3))) {
				int idPublication = Integer.parseInt(request.getParameter("idPubli"));
				int idCommentaire = Integer.parseInt(request.getParameter("idCommentaire"));
				int idAfficher = Integer.parseInt(request.getParameter("idAfficher"));
				User publicateur = ServiceUser.getUserById(idAfficher, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
				
				Publication publiFeed = ServicePublication.getPublicationById(feedAccueil, idPublication);
				ServiceCommentaire.removeCommentaire(ServicePublication.getPublicationById(feedAccueil, idPublication).getListeCommentaires(),
						ServiceCommentaire.getCommentaireById(publiFeed.getListeCommentaires(), idCommentaire));
				ServicePublication.saveListePublication(ServiceApp.getValue("9",2), feedAccueil);
				
				Publication publi = ServicePublication.getPublicationById(publicateur.getFeed(), idPublication);
				ServiceCommentaire.removeCommentaire(ServicePublication.getPublicationById(publicateur.getFeed(), idPublication).getListeCommentaires(),
						ServiceCommentaire.getCommentaireById(publi.getListeCommentaires(), idCommentaire));
				ServiceUser.saveUser(ServiceApp.getValue("2", 2), publicateur);
				session.setAttribute("user", ServiceUser.getUserById(idUser, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2))));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("11",3));
				dispatcher.forward(request, response);
				
			}else if(action.equalsIgnoreCase(ServiceApp.getValue("15", 3))) {
				int idProfil = Integer.parseInt(request.getParameter("idAfficher"));
				User profil = ServiceUser.getUserById(idProfil, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
				session.setAttribute("amitie", (ServiceUser.getAmiById(idProfil, user.getListeAmi()) == null)? false:true);
				session.setAttribute("user", user);
				session.setAttribute("profil", profil);
				
				response.sendRedirect(ServiceApp.getValue("11", 2));
			}
			else if(action.equalsIgnoreCase(ServiceApp.getValue("26", 3))) {
				session.setAttribute("user", user);
				request.getRequestDispatcher(ServiceApp.getValue("6", 2)).forward(request, response);
			}
			else if(action.equalsIgnoreCase(ServiceApp.getValue("27", 3))) {
				session.setAttribute("user", user);
				request.getRequestDispatcher(ServiceApp.getValue("8", 2)).forward(request, response);
			}
			else if (action.equalsIgnoreCase(ServiceApp.getValue("28", 3))) {
				String nom = "", prenom = "", email = "";
				if(!request.getParameter("newNom").isEmpty()) {
					nom = request.getParameter("newNom");
					user.setNom((nom.trim()).substring(0, 1).toUpperCase()+(nom.trim()).substring(1).toLowerCase());
				}
				else if(!request.getParameter("newPrenom").isEmpty()) {
					prenom = request.getParameter("newPrenom") ;
					user.setPrenom((prenom.trim()).substring(0, 1).toUpperCase()+(prenom.trim()).substring(1).toLowerCase());
				}
				else if(!request.getParameter("newEmail").isEmpty()) {
					email = request.getParameter("newEmail") ;
					user.getCredential().setEmail(email);
				}
				
				if(!ServiceValidation.valideEmail(email)) {
					request.setAttribute("mapErreurs",ServiceValidation.getMapErreurs());
					request.getRequestDispatcher(ServiceApp.getValue("6",2)).forward(request, response);
				}
				
				Map<Integer, User> tableUsers = ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2));
				Map<Integer, Credential> tableLogins = ServiceConnection.loadMapCredentials(ServiceApp.getValue("3", 2));
				
				ServiceUser.updateUser(user.getCredential().getId(), user, tableUsers);
				ServiceConnection.updateCredential(user.getCredential().getId(), user.getCredential(), tableLogins);
				ServiceUser.saveToXML(tableUsers, ServiceApp.getValue("2", 2));
				ServiceConnection.saveMapCredentials(ServiceApp.getValue("3", 2), tableLogins);
				session.setAttribute("idAfficher", user.getCredential().getId());
				session.setAttribute("idUser", user.getCredential().getId());
				session.setAttribute("user", user);
				request.getRequestDispatcher(ServiceApp.getValue("5", 2)).forward(request, response);
			}
			else if (action.equalsIgnoreCase(ServiceApp.getValue("29", 3))) {
				String password = "";
				if(!request.getParameter("newPassword").isEmpty()) {
					password = request.getParameter("newPassword");
					user.getCredential().setPassword(ServicePassword.encryptPassword(password));
				}
				if(!ServiceValidation.validePassword(user.getCredential().getId(), password)) {
					request.setAttribute("mapErreurs",ServiceValidation.getMapErreurs());
					request.getRequestDispatcher(ServiceApp.getValue("6",2)).forward(request, response);
				}
				
				Map<Integer, User> tableUsers = ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2));
				Map<Integer, Credential> tableLogins = ServiceConnection.loadMapCredentials(ServiceApp.getValue("3", 2));
				ServiceUser.updateUser(user.getCredential().getId(), user, tableUsers);
				ServiceConnection.updateCredential(user.getCredential().getId(), user.getCredential(), tableLogins);
				ServiceUser.saveToXML(tableUsers, ServiceApp.getValue("2", 2));
				ServiceConnection.saveMapCredentials(ServiceApp.getValue("3", 2), tableLogins);
				session.setAttribute("idAfficher", user.getCredential().getId());
				session.setAttribute("idUser", user.getCredential().getId());
				session.setAttribute("user", user);
				request.getRequestDispatcher(ServiceApp.getValue("5", 2)).forward(request, response);
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
