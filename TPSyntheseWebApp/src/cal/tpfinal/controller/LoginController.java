package cal.tpfinal.controller;

import java.io.IOException;
import java.util.HashMap;
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

import cal.tpfinal.bean.Credential;
import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServiceApp;
import cal.tpfinal.model.ServiceConnection;
import cal.tpfinal.model.ServicePassword;
import cal.tpfinal.model.ServiceUser;
import cal.tpfinal.util.Authentification;

/**
 * Servlet implementation class controllerAuthentification
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FICHIER_USERS_DATA = "C:\\appBasesDonnees/tableUsers.xml" ;
	private static final String FICHIER_LOGIN_DATA = "C:\\appBasesDonnees/tableLogin.xml" ;
	
	private static final String PAGE_COMPTE_USER = "AchatActions.jsp";
	private static Logger logger = LogManager.getLogger(LoginController.class);
	
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		logger.info("Initialisation de l'application");
		try {
			//Map<Integer, User> tableUsers = ServiceUser.fromToXML(FICHIER_LOGIN_DATA);
			//new File(FICHIER_LOGIN_DATA).exists() || ServiceUser.fromToXML(FICHIER_LOGIN_DATA)!= null
			if(ServiceUser.fromToXML(FICHIER_LOGIN_DATA)!= null)
				new User().getCredential().setId(Integer.valueOf(ServiceApp.getValue("1", 1)));
		} catch (Exception e) {
			logger.error("Problême - Function init(LoginControler) - Initialisation des données");
			logger.debug(e.getMessage());
		}finally {
			logger.info("Fin de l'initialisation");
		}
		
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		try {
			if(action.equalsIgnoreCase("inscriptionClient")) {
				String nom = request.getParameter("nomInscript") ;
				String prenom = request.getParameter("prenomInscript") ;
				String email = request.getParameter("emailInscript") ;
				String password = request.getParameter("passwordInscript") ;
				String passwordConfirm = request.getParameter("passwordInscriptConfirm") ;
				
				if(nom.equals("") && nom.isEmpty()) {
					
				}
				else if(prenom.equals("") && prenom.isEmpty()) {
					
				}
				else if(email.equals("") && email.isEmpty()) {
					
				}
				if(Authentification.isEmailExist(email, FICHIER_LOGIN_DATA)) {
					// mettre une erreur dans le tableau
					// afficher à l'utilisateur
				}
				if(!passwordConfirm.equals(password)) {
					
				}
				else{
					User user = new User();
					logger.info(LoginController.class.getName()+" | Id User Creation "+user.getCredential().getId());
					user.setNom(nom);
					user.setPrenom(prenom);
					user.getCredential().setEmail(email);
					user.getCredential().setEmail(email);
					user.getCredential().setPassword(ServicePassword.encryptPassword(passwordConfirm));
					// set le dernier id du user crée dans le file properties
					ServiceApp.setValue("1", String.valueOf(user.getCredential().getId()), 1);
					
					Map<Integer, User> tableUsers = ServiceUser.fromToXML(FICHIER_USERS_DATA);
					if(tableUsers == null) {
						tableUsers = new HashMap<>();
						Map<Integer, Credential> tableLogins = new HashMap<>();
						ServiceConnection.addUtilisateur(user.getCredential(), tableLogins);
						ServiceConnection.saveCollectionUtilisateurs(FICHIER_LOGIN_DATA, tableLogins);
						ServiceUser.addUser(user, tableUsers);
						ServiceUser.saveToXML(tableUsers, FICHIER_USERS_DATA);
					}
					else {
						System.out.println("Il y a deja un client dans la collection !");
						ServiceUser.saveClient(FICHIER_USERS_DATA, user);
						ServiceConnection.saveUtilisateur(FICHIER_LOGIN_DATA, user.getCredential());
					}
					response.sendRedirect("index.jsp");
					
				}
				
			}
			else if(action.equalsIgnoreCase("loginClient")) {
				
				
				Credential user = Authentification.verificationUtilisateur(request.getParameter("email"),request.getParameter("password"), FICHIER_USERS_DATA);
				System.out.println("Credential ---> "+user);
				if( user != null) {
					Map<Integer, User> collectionClients = ServiceUser.fromToXML(FICHIER_USERS_DATA);
					request.setAttribute("clientActuel", ServiceUser.getUserById(user.getId(), collectionClients));
					request.setAttribute("utilisateurActuel", user);
					RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE_COMPTE_USER);
					dispatcher.forward(request, response);
				}
				else {
					response.sendRedirect("index.jsp");
				}
			}
			
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
