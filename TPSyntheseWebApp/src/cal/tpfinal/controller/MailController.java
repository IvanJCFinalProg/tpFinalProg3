package cal.tpfinal.controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
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
import cal.tpfinal.model.ServiceMail;

/**
 * Servlet implementation class MailController
 */
@WebServlet("/MailController")
public class MailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(MailController.class);

	
	public void init(ServletConfig config) throws ServletException {
		
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter(ServiceApp.getValue("1", 3));
		HttpSession session = request.getSession();
		try {
			User user = (User) session.getAttribute("user");
			if(action.equalsIgnoreCase("envoieMail")) {
				String from = request.getParameter("from");
				String to = request.getParameter("to");
				String subject = request.getParameter("subject");
				String message = request.getParameter("message");
				message += "\r\n\nDe "+user.getPrenom()+" "+user.getNom();
				ServiceMail.sendMail(to, from, subject, message);
				response.sendRedirect(ServiceApp.getValue("11", 3));
			}
			
			
		}catch (Exception e) {
			logger.error(MailController.class.getName()+" | Probleme dans la fonction processRequest()");
			logger.debug(e.getMessage());
		}
		finally {
			
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
