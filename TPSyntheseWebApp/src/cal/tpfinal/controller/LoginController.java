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
	private static final String PAGE_COMPTE_USER = "AchatActions.jsp";
	
	//private static final String FICHIER_USERS_DATA = "C:\\appBasesDonnees/tableUsers.xml" ;
	//private static final String FICHIER_LOGIN_DATA = "C:\\appBasesDonnees/tableLogin.xml" ;
		
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		logger.info("Initialisation de l'application");
		try {
			System.out.println(!(ServiceUser.fromToXML(ServiceApp.getValue("3",2))!= null));
			if(!(ServiceUser.fromToXML(ServiceApp.getValue("3",2))!= null)) {
				System.out.println("passer "+Integer.valueOf(ServiceApp.getValue("1", 1)));
				//new User().getCredential().setId(Integer.valueOf(ServiceApp.getValue("1", 1))+1);
			}	
		} catch (Exception e) {
			logger.error("Probl�me - Function init(LoginControler) - Initialisation des donn�es");
			logger.debug(e.getMessage() +" "+e.getLocalizedMessage());
		}finally {
			logger.info("Fin de l'initialisation");
		}
		
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
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
				if(Authentification.isEmailExist(email, ServiceApp.getValue("3",2))) {
					// mettre une erreur dans le tableau
					// afficher � l'utilisateur
				}
				if(!passwordConfirm.equals(password)) {
					
				}
				else{
					User user = new User();
					//
					System.out.println(user.getCredential().getId());
					logger.info(LoginController.class.getName()+" | Id User Creation "+user.getCredential().getId());
					user.setNom(nom);
					user.setPrenom(prenom);
					user.setPhoneNumber("");
					user.setSexe(sexe);
					user.setDateInscription(new DateTime());
					user.setBirthDate(DateTime.parse(dateBirth));
					user.getCredential().setEmail(email);
					user.getCredential().setPassword(ServicePassword.encryptPassword(passwordConfirm));
					// set le dernier id du user cr�e dans le file properties
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
				
				
				Credential user = Authentification.verificationUtilisateur(request.getParameter("email"),request.getParameter("password"), ServiceApp.getValue("2", 2));
				if( user != null) {
					Map<Integer, User> collectionClients = ServiceUser.fromToXML(ServiceApp.getValue("2", 2));
					request.setAttribute("clientActuel", ServiceUser.getUserById(user.getId(), collectionClients));
					request.setAttribute("utilisateurActuel", user);
					RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE_COMPTE_USER);
					dispatcher.forward(request, response);
				}
				else {
					response.sendRedirect(ServiceApp.getValue("1", 2));
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
