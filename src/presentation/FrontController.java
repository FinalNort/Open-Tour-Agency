
/**
 * @author Panarosa Gaetano
 *
 * Utilizzo dei design pattern FrontController e Singleton
 *
 */

package presentation;

import business.ApplicationController;

public class FrontController implements FrontController_Interface {

    /**
     * Il Singleton rende necessario creare un costruttore privato per evitare che venga istanziato in memoria ogni volta che viene
     * richiamato da altre classi durante l'esecuzione del sistema.
     */

    private FrontController() {}  //Costruttore reso privato a causa della presenza del Singleton

    /**
     * @param instance Viene creata una variabile nulla di tipo FrontController.
     */

    private static FrontController instance = null;

    /**
     * Il metodo getInstance() effettua un controllo sulla variabile
     * instance, settando una nuova istanza del costruttore FrontController
     * nel caso in cui la variabile instance sia nulla.
     * @return instance
     */
    public static FrontController getInstance() {
        if (instance == null) {
            FrontController.instance = new FrontController();
        }
        return instance;
    }

    ApplicationController AC;

    /**
	 * @param request oggetto utilizzato in tutte le elaborazioni
	 * @param switchCase variabile utilizzata per la scelta della funzione nello switch implementato nell' Application Controller
	 * @param type indica la tipologia dell'utente che sta attualmente utilizzando il software, 0 per le operazioni comuni, 1
	 *  per UTENTE  e 2 per AMMINISTRATORE
	 * @return tutto ciò che ci viene restituito dai singoli case di tipo Object
    */

    @Override
    public Object processRequest(Object request, int switchCase, int type) {
        Object result;
        try {
            AC = new ApplicationController();
            result = AC.handleRequest(request, switchCase, type);
        } catch (Exception e) {
            System.err.println("Errore durante il ritorno del risultato");
            System.err.println(e.getCause());
            System.err.println(e.getLocalizedMessage());
            result = null;
        }
        return result;
    }


}
