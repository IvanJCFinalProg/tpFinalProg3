package cal.tpfinal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import cal.tpfinal.bean.Credential;
import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServiceApp;
import cal.tpfinal.model.ServiceConnection;
import cal.tpfinal.model.ServicePassword;
import cal.tpfinal.model.ServiceUser;
import cal.tpfinal.util.Authentification;
import cal.tpfinal.util.IService;
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
			if(!(ServiceUser.fromToXML(ServiceApp.getValue("3",2))!= null)) {
				User.setCompteur(Integer.valueOf(ServiceApp.getValue("1", 1))+1);
			}	
		} catch (Exception e) {
			logger.error("Problï¿½me - Function init(LoginControler) - Initialisation des donnï¿½es");
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
			System.out.println("entrer DONNEES");
			if(action.equalsIgnoreCase(IService.TYPE_FORM1)) {
				String nom = request.getParameter("nomInscript") ;
				String prenom = request.getParameter("prenomInscript") ;
				String email = request.getParameter("emailInscript") ;
				String password = request.getParameter("passwordInscript") ;
				String passwordConfirm = request.getParameter("passwordInscriptConfirm") ;
				String sexe = request.getParameter("sexe");
				String dateBirth = request.getParameter("dateBirth");
				int age = Years.yearsBetween(LocalDate.parse(dateBirth), new LocalDate()).getYears();
				
				System.out.println("entrer dONNEES SET");
				
				if(!ServiceValidation.isValideDonneesInputs(nom, prenom, email)) {
					request.setAttribute("mapErreurs",ServiceValidation.getMapErreurs());
					System.out.println(ServiceValidation.getMapErreurs());
					RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("1",2));
					dispatcher.forward(request, response);
				}
				else{
					System.out.println("entrer dONNEES Get");
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
					// set le dernier id du user crï¿½e dans le file properties
					ServiceApp.setValue("1", String.valueOf(user.getCredential().getId()), 1);
					
					Map<Integer, User> tableUsers = ServiceUser.fromToXML(ServiceApp.getValue("2", 2));
					if(tableUsers == null) {
						tableUsers = new HashMap<>();
						Map<Integer, Credential> tableLogins = new HashMap<>();
						ServiceConnection.addCredential(user.getCredential(), tableLogins);
						ServiceConnection.saveMapCredentials(ServiceApp.getValue("3", 2), tableLogins);
						ServiceUser.addUser(user, tableUsers);
						ServiceUser.saveToXML(tableUsers, ServiceApp.getValue("2", 2));
					}
					else {
						ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
						ServiceConnection.saveCredential(ServiceApp.getValue("3", 2), user.getCredential());
					}
					response.sendRedirect(ServiceApp.getValue("1", 2));
				}
				
			}
			else if(action.equalsIgnoreCase(IService.TYPE_FORM2)) {
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				if(email.substring(0,email.indexOf("@")).equals(ServiceApp.getValue("4", 1))) {
					if(email.equals(ServiceApp.getValue("2", 1)) && password.equals(ServiceApp.getValue("3", 1))) {
						request.setAttribute("mapUsers", ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
						RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("4", 2));
						dispatcher.forward(request, response);
					}
				}else {
					Credential user = Authentification.verificationUtilisateur(request.getParameter("email"),request.getParameter("password"), ServiceApp.getValue("3", 2));
					if( user != null) {
						session.setAttribute("user", ServiceUser.getUserById(user.getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
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
						response.sendRedirect(ServiceApp.getValue("1", 2));
					}
				}
			}else if(action.equalsIgnoreCase("accueil")) {
				User user= (User)session.getAttribute("user");
				
				/* En développement */
				session.setAttribute("user", user);
				response.sendRedirect(ServiceApp.getValue("5", 2));
				
			}else if(action.equalsIgnoreCase("deconnexion")) {
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

