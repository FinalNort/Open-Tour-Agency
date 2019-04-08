/**
 * @author Panarosa Gaetano matricola 609416
 * Classe Interfaccia per la classe "Application Controller"
 */
package business;
/**
 * @param handleRequest Metodo di tipo Object responsabile dello smistamento della richiesta in base ai parametri:
 * @param request oggetto utilizzato tutte le elaborazioni.
	 * @param switchCase variabile utilizzata per la scelta della funzione nello switch implementato nell' Application Controller.
	 * @param type indica la tipologia dell'utente che sta attualmente utilizzando il software:
	 *  0 per le operazioni comuni
	 *  1 per UTENTE
	 *  2 per AMMINISTRATORE
 */
//Dichiarazione dell'interfaccia
public interface ApplicationController_Interface {
	Object handleRequest(Object request, int switchCase, int type);
}
