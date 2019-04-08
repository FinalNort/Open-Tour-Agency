/**
 * @author Panarosa Gaetano matricola 609416
 */
package business;

import integration.DAO_Admin;
import integration.DAO_Utente;
import integration.DAO_Login;

/**
 *
 * Pattern per centralizzare e/o aggregare comportamenti. Utilizzato assieme al
 * Front Controller per gestire le richieste del sistema.
 *
 */
public class ApplicationController implements ApplicationController_Interface {

	private Object result = null;

	/**
	 * @param request    oggetto utilizzato tutte le elaborazioni.
	 * @param switchCase variabile utilizzata per la scelta della funzione nello
	 *                   switch implementato nell' Application Controller.
	 * @param type       indica la tipologia dell'utente che sta attualmente
	 *                   utilizzando il software, 0 per le operazioni comuni, 1 per
	 *                   UTENTE e 2 per AMMINISTRATORE.
	 * @return restitusce i valori dai singoli case di tipo Object
	 * @param handleRequest Metodo di tipo Object responsabile dello smistamento
	 *                      della richiesta in base ai parametri forniti dal Front
	 *                      Controller
	 */
	@Override
	public Object handleRequest(Object request, int switchCase, int type) {

		try {
			// TYPE = 0 OPERAZIONI COMUNI
			if (type == 0) {
				DAO_Login loginOP = new DAO_Login();
				switch (switchCase) {
				// Caso 0: effettua il login
				case 0:
					result = loginOP.Login(request);
					break;

				// Caso 1: effettua la richiesta di visualizzazione del nome
				case 1:
					result = loginOP.statusName(request);
					break;
				}

			}
			// TYPE = 1 OPERAZIONI UTENTE
			else if (type == 1) {
				DAO_Utente userOperation = new DAO_Utente();

				switch (switchCase) {
				// Caso 0: effettua la registrazione dell'utente
				case 0:
					result = userOperation.regUtente(request);
					break;

				// Caso 1: effettua la richiesta di visualizzazione dei dati dell'utente
				case 1:
					result = userOperation.userDetails(request);
					break;

				// Caso 2: effettua l'update dei dati modificati dell'utente
				case 2:
					userOperation.updateUserDetails(request);
					break;

				// Caso 3: effettua la richiesta di visualizzazione delle escursioni a cui un
				// utente si è iscritto
				case 3:
					result = userOperation.userExcursions(request);
					break;
				// Caso 4: effettua la richiesta di visualizzazione delle escursioni disponibili
				// per l'utente
				case 4:
					result = userOperation.avaiableExcursions(request);
					break;
				// Caso 5: effettua la richiesta di visualizzare tutti gli optional
				// dell'escursione
				case 5:
					result = userOperation.getOptional(request);
					break;
				// Caso 6: effettua l'iscrizione dell'utente ad una escursione
				case 6:
					userOperation.iscrizioneEscursione(request);
					break;
				// Caso 7: effettua la cancellazione dell'utente da una escursione
				case 7:
					userOperation.deleteExcursionUser(request);
					break;
				//Caso 8: effettua la modifica dell'escursione selezionata in cui si è iscritto un utente 
				case 8:
					userOperation.modExcursion(request);
					break;
				//Caso 9: effettua la cancellazione di un utente da un'escursione selezionata 
				case 9:
					userOperation.deleteUser(request);
					break;

				}
			}
			// TYPE = 2 OPERAZIONI AMMINISTRATORE
			else if (type == 2) {

				DAO_Admin adminOperation = new DAO_Admin();

				switch (switchCase) {
				// Caso 0: effettua la registrazione di un amministratore
				case 0:
					adminOperation.regAdmin(request);
					break;

				// Caso 1: effettua la richiesta di visualizzazione di tutti gli utenti
				// registrati
				case 1:
					result = adminOperation.showUsers();
					break;

				// Caso 2: effettua la richiesta di visualizzazione di tutti gli amministratori
				// registrati
				case 2:
					result = adminOperation.showAdmins();
					break;

				// Caso 3: effettua la cancellazione di un utente già registrato
				case 3:
					adminOperation.deleteUser(request);
					break;

				case 4:
					result = adminOperation.showAdminDetails(request);
					break;

				case 5:
					result = adminOperation.showEscursioni(request);
					break;

				case 6:
					adminOperation.insertExcursion(request);
					break;

				case 7:
					result = adminOperation.getOptional(request);
					break;

				case 8:
					adminOperation.deleteAdmin(request);
					break;

				case 9:
					adminOperation.deleteExcursion(request);
					break;

				case 10:
					result = adminOperation.showExcursionDetails(request);
					break;

				case 11:
					adminOperation.modifyExcursion(request);
					break;

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
