package cal.tpfinal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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

import cal.tpfinal.bean.Credential;
import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServiceApp;
import cal.tpfinal.model.ServiceConnection;
import cal.tpfinal.model.ServicePassword;
import cal.tpfinal.model.ServiceUser;
import cal.tpfinal.util.Authentification;
import cal.tpfinal.util.IService;

/**
 * Servlet implementation class controllerAuthentification
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(LoginController.class);

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		logger.info("Initialisation de l'application");
		try {
			if(!(ServiceUser.fromToXML(ServiceApp.getValue("3",2))!= null)) {
				//new User().getCredential().setId(Integer.valueOf(ServiceApp.getValue("1", 1)));
				User.setCompteur(Integer.valueOf(ServiceApp.getValue("1", 1))+1);
			}	
		} catch (Exception e) {
			logger.error("Problême - Function init(LoginControler) - Initialisation des données");
			logger.debug(e.getMessage() +" "+e.getLocalizedMessage());
		}finally {
			logger.info("Fin de l'initialisation");
		}
		
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		PrintWriter writer = response.getWriter();
		
		try {
			if(action.equalsIgnoreCase(IService.TYPE_FORM1)) {
				String nom = request.getParameter("nomInscript") ;
				String prenom = request.getParameter("prenomInscript") ;
				String email = request.getParameter("emailInscript") ;
				String password = request.getParameter("passwordInscript") ;
				String passwordConfirm = request.getParameter("passwordInscriptConfirm") ;
				String sexe = request.getParameter("sexe");
				String dateBirth = request.getParameter("dateBirth");
				
				if(nom.equals("") && nom.isEmpty()) {
					response.setContentType("text/html");
					RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("1",2));
					dispatcher.forward(request, response);
				}
				else if(prenom.equals("") && prenom.isEmpty()) {
					
				}
				else if(email.equals("") && email.isEmpty()) {
					
				}
				if(!passwordConfirm.equals(password)) {
					
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
					// set le dernier id du user crée dans le file properties
					ServiceApp.setValue("1", String.valueOf(user.getCredential().getId()), 1);
					
					Map<Integer, User> tableUsers = ServiceUser.fromToXML(ServiceApp.getValue("2", 2));
					if(tableUsers == null) {
						tableUsers = new HashMap<>();
						Map<Integer, Credential> tableLogins = new HashMap<>();
						ServiceConnection.addUtilisateur(user.getCredential(), tableLogins);
						ServiceConnection.saveCollectionUtilisateurs(ServiceApp.getValue("3", 2), tableLogins);
						ServiceUser.addUser(user, tableUsers);
						ServiceUser.saveToXML(tableUsers, ServiceApp.getValue("2", 2));
					}
					else {
						ServiceUser.saveClient(ServiceApp.getValue("2", 2), user);
						ServiceConnection.saveUtilisateur(ServiceApp.getValue("3", 2), user.getCredential());
					}
					response.sendRedirect(ServiceApp.getValue("1", 2));
				}
				
			}
			else if(action.equalsIgnoreCase(IService.TYPE_FORM2)) {
				HttpSession session = request.getSession();
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				
				if(email.equals(ServiceApp.getValue("2", 1)) && password.equals(ServiceApp.getValue("3", 1))) {
					request.setAttribute("mapUsers", ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
					RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("4", 2));
					dispatcher.forward(request, response);
				}
				else {
					Credential user = Authentification.verificationUtilisateur(request.getParameter("email"),request.getParameter("password"), ServiceApp.getValue("2", 2));
					if( user != null) {
						request.setAttribute("user", ServiceUser.getUserById(user.getId(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2))));
						RequestDispatcher dispatcher = request.getRequestDispatcher(ServiceApp.getValue("5", 2));
						dispatcher.forward(request, response);
					}
					else {
						response.sendRedirect(ServiceApp.getValue("1", 2));
					}
				}
			}
			
			
		}catch (Exception e) {
			logger.error(LoginController.class.getName()+" Erreur dans le fonction processRequest()");
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

