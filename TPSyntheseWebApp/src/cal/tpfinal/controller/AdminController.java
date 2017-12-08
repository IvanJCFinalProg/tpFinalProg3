package cal.tpfinal.controller;

import java.io.IOException;
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

import cal.tpfinal.bean.User;
import cal.tpfinal.model.ServiceApp;
import cal.tpfinal.model.ServiceUser;

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
		Map<Integer,User> mapUsers = ServiceUser.fromToXML(ServiceApp.getValue("2", 2));
		int idUser = Integer.valueOf(request.getParameter(ServiceApp.getValue("5", 3))); 
		try {
			
			if(action.equals(ServiceApp.getValue("2", 3))) {
				mapUsers.get(idUser).setBlocked(true);
				ServiceUser.saveToXML(mapUsers, ServiceApp.getValue("2", 2));
				RequestDispatcher dispatcher = request.getRequestDispatcher("AdminController?action=pageAdmin");
				dispatcher.forward(request, response);
			}
			else if(action.equals(ServiceApp.getValue("3", 3))) {
				
			}
			else if(action.equals(ServiceApp.getValue("4", 3))) {
				
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

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
