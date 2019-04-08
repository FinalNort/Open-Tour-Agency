package integration;

public abstract class DAO_Abstract_Factory {


    public static final int DAO_Utente = 1;
    public static final int DAO_Admin = 2;
    public static final int DAO_Login = 3;

    public abstract DAO_Utente getUtenteDAO();
    public abstract DAO_Admin getAdminDAO();
    public abstract DAO_Login getLoginDAO();

    public static DAO_Abstract_Factory getDAOFactory(int whichFactory) {

    	    switch (whichFactory) {
    	      case DAO_Utente:
    	          return new DAO_Factory();
    	      case DAO_Admin:
    	          return new DAO_Factory();
    	      case DAO_Login:
    	    	  return new DAO_Factory();
    	      default:
    	          return null;
    	    }
    	  }
}
