package cal.tpfinal.util;

import cal.tpfinal.model.ServiceApp;

public interface IServiceUtils {

	public static String FILE1 ="C:\\appBasesDonnees/appRessources/appRessourcesData.properties"; 
	public static String FILE2 ="C:\\appBasesDonnees/appRessources/appRessourcesFiles.properties";
	public static String FILE3 ="C:\\appBasesDonnees/appRessources/appRessourcesActions.properties";
	public static String FILE4 = ServiceApp.getValue("7", 2);
	public static String TYPE_FORM1= "inscriptionMembre";
	public static String TYPE_FORM2= "loginMembre";
	public static String REDIRECTION_ADMIN= "AdminController?action=pageAdmin";
	public static String REGEX_NAME = "^[A-Za-z]{2,20}$";
	public static String ERROR_NOT_USER="<h5 class=\"errormsg\">Votre email ou mot de passe est incorrect<h5>";
}
