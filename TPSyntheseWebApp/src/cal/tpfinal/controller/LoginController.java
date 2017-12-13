package cal.tpfinal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import cal.tpfinal.bean.Admin;
import cal.tpfinal.bean.Commentaire;
import cal.tpfinal.bean.Credential;
import cal.tpfinal.bean.Publication;
import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServiceApp;
import cal.tpfinal.model.ServiceConnection;
import cal.tpfinal.model.ServicePassword;
import cal.tpfinal.model.ServicePublication;
import cal.tpfinal.model.ServiceUser;
import cal.tpfinal.util.Authentification;
import cal.tpfinal.util.IServiceUtils;
import cal.tpfinal.util.ServiceValidation;

/**
 * Servlet implementation class controllerAuthentification
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(LoginController.class);
	private static final int DUREE_VIE_COOKIE = 60*60*24*7;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		logger.info("Initialisation de l'application");
		try {
			ServiceValidation.getMapErreurs().clear();
			if(!(ServiceUser.loadMapUserFromXML(ServiceApp.getValue("3",2))!= null)) {
				User.setCompteur(Integer.valueOf(ServiceApp.getValue("1", 1))+1);
			}
			if((ServicePublication.loadListePublication("C:/appBasesDonnees/tableFeed.xml")!= null)) {
				Publication.setCompteur(Integer.valueOf(ServiceApp.getValue("5", 1))+1);
				Commentaire.setCompteur(Integer.valueOf(ServiceApp.getValue("6", 1))+1);
			}	
		} catch (Exception e) {
			logger.error(LoginController.class.getName()+" | Probleme - Function init(LoginControler) - Initialisation des donnees");
			logger.debug(e.getMessage() +" "+e.getLocalizedMessage());
		}finally {
			logger.info("Fin de l'initialisation");
		}
		
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		List<Publication> feedAccueil = (List<Publication>)ServicePublication.loadListePublication("C:/appBasesDonnees/tableFeed.xml");
		if(feedAccueil == null) {
			feedAccueil = new ArrayList<Publication>();
			try {
				ServicePublication.saveListePublication("C:/appBasesDonnees/tableFeed.xml", feedAccueil);
			}catch(Exception e) {}
		}
		session.setAttribute("feedAccueil", feedAccueil);
		
		try {
			if(action.equalsIgnoreCase(IServiceUtils.TYPE_FORM1)) {
				String nom = request.getParameter("nomInscript") ;
				String prenom = request.getParameter("prenomInscript") ;
				String email = request.getParameter("emailInscript") ;
				String password = request.getParameter("passwordInscript") ;
				String passwordConfirm = request.getParameter("passwordInscriptConfirm") ;
				String sexe = request.getParameter("sexe");
				String dateBirth = request.getParameter("dateBirth");
				int age = Years.yearsBetween(LocalDate.parse(dateBirth), new LocalDate()).getYears();
				
				if(!ServiceValidation.isValideDonneesInputs(nom, prenom, email)) {
					request.setAttribute("mapErreurs",ServiceValidation.getMapErreurs());
					request.getRequestDispatcher(ServiceApp.getValue("1",2)).forward(request, response);
				}
				else{
					User user = new User();
					logger.info(LoginController.class.getName()+" | Id User Creation "+user.getCredential().getId());
					user.setNom((nom.trim()).substring(0, 1).toUpperCase()+(nom.trim()).substring(1).toLowerCase());
					user.setPrenom((prenom.trim()).substring(0, 1).toUpperCase()+(prenom.trim()).substring(1).toLowerCase());
					user.setPhoneNumber("");
					user.setSexe(sexe);
					user.setDateInscription(DateTime.now());
					user.setBirthDate(DateTime.parse(dateBirth));
					user.getCredential().setEmail(email);
					user.getCredential().setPassword(ServicePassword.encryptPassword(passwordConfirm));
					user.setAge(age);
					// set le dernier id du user cree dans le file properties
					ServiceApp.setValue("1", String.valueOf(user.getCredential().getId()), 1);
					
					Map<Integer, User> tableUsers = ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2));
					if(tableUsers == null) {
						tableUsers = new HashMap<>();
						Map<Integer, Credential> tableLogins = new HashMap<>();
						ServiceConnection.addCredential(user.getCredential(), tableLogins);
						ServiceConnection.saveMapCredentials(ServiceApp.getValue("3", 2), tableLogins);
						ServiceUser.addUser(user, tableUsers);
						ServiceUser.saveToXML(tableUsers, ServiceApp.getValue("2", 2));
					}
					else {
						ServiceUser.saveUser(ServiceApp.getValue("2", 2), user);
						ServiceConnection.saveCredential(ServiceApp.getValue("3", 2), user.getCredential());
					}
					response.sendRedirect(ServiceApp.getValue("1", 2));
				}
				
			}
			else if(action.equalsIgnoreCase(IServiceUtils.TYPE_FORM2)) {
				if(request.getAttribute("notUser")!=null)request.removeAttribute("notUser");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				if(email.substring(0,email.indexOf("@")).equals(ServiceApp.getValue("4", 1))) {
					if(email.equals(ServiceApp.getValue("2", 1)) && password.equals(ServiceApp.getValue("3", 1))) {
						request.setAttribute("mapUsers", ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
						request.setAttribute("admin", Admin.getInstance());
						request.getRequestDispatcher(ServiceApp.getValue("4", 2)).forward(request, response);
					}
				}else {
					Credential user = Authentification.verificationUtilisateur(request.getParameter("email"),request.getParameter("password"), ServiceApp.getValue("3", 2));
					if( user != null) {
						User userAuth = ServiceUser.getUserById(user.getId(), ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
						userAuth.setConnected(true);
						ServiceUser.saveUser(ServiceApp.getValue("2", 2), userAuth);
						session.setAttribute("user", userAuth);
						//request.setAttribute("user", ServiceUser.getUserById(user.getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
						/* En développement */
						/*if(request.getParameter("checkbox") != null) {
							 Cookie cookie = new Cookie( "email", email );
							    cookie.setMaxAge( DUREE_VIE_COOKIE );
							    response.addCookie( cookie );
						}else {
							Cookie cookie = new Cookie(request.getParameter("email"), "");
							cookie.setMaxAge(0);
							response.addCookie(cookie);
						}*/
						RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("5", 2));
						dispatcher.forward(request, response);
					}
					else {
						request.setAttribute("notUser", IServiceUtils.ERROR_NOT_USER);
						request.getRequestDispatcher(ServiceApp.getValue("1",2)).forward(request, response);
					}
				}
			}else if(action.equalsIgnoreCase("accueil")) {
				User user= (User)session.getAttribute("user");
				/* En développement */
				session.setAttribute("user", user);
				response.sendRedirect(ServiceApp.getValue("5", 2));
				
			}else if(action.equalsIgnoreCase("deconnexion")) {
				int idUser = Integer.parseInt(request.getParameter("idUser"));
				User userAuth = ServiceUser.getUserById(idUser, ServiceUser.loadMapUserFromXML(ServiceApp.getValue("2", 2)));
				userAuth.setConnected(false);
				ServiceUser.saveUser(ServiceApp.getValue("2", 2), userAuth);
				response.sendRedirect(ServiceApp.getValue("1", 2));
				/* En développement */
				//RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("1", 2));
				//dispatcher.forward(request, response);
			}
		}catch (Exception e) {
			logger.error(LoginController.class.getName()+" Erreur dans la fonction processRequest()");
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

