/**
 * @author Panarosa Gaetano
 * Interface per il Front Controller dove viene inserito il prototipo del metodo
 * processRequest
 */
package presentation;

/**
	 * @param request oggetto utilizzato tutte le elaborazioni.
	 * @param switchCase variabile utilizzata per la scelta della funzione nello switch implementato nell' Application Controller.
	 * @param type indica la tipologia dell'utente che sta attualmente utilizzando il software, 0 per le operazioni comuni,
	 * 1 per UTENTE e 2 per AMMINISTRATORE.
   */

public interface FrontController_Interface {
	Object processRequest(Object request, int switchCase, int type);
}

