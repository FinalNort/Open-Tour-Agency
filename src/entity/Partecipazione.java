package entity;

import java.util.Iterator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

/**
 *CLASSE POJO Partecipazione
 */
public class Partecipazione {
	private SimpleIntegerProperty idUtente;
	private SimpleIntegerProperty idEscursione;
	private SimpleStringProperty prezzo;
	private SimpleListProperty<Object> optional;

	public Partecipazione() {
		this.idUtente = new SimpleIntegerProperty();
		this.idEscursione = new SimpleIntegerProperty();
		this.prezzo = new SimpleStringProperty();
		this.optional=new SimpleListProperty<Object>();

	}
	public int getIdUtente() {
		return idUtente.get();
	}

	public void setIdUtente(int idUtente) {
		this.idUtente.set(idUtente);
	}

	public int getIdEscursione() {
		return idEscursione.get();
	}

	public void setIdEscursione(int idEscursione) {
		this.idEscursione.set(idEscursione);
	}


	public ObservableList<Object> getOptional(int i) {
		return optional;
	}

	/**
	 * @return stringa contenente i nomi degli optional o nel caso non ci fossero "Nessun optional"
	 */
	public String getOptional() {
		String optional="nessun optional";
		Iterator<Object> it = this.optional.iterator();
		if(it.hasNext()){
			optional="";
			for (int i = 0; it.hasNext(); it.next(), i++) {
				entity.Optional op = (entity.Optional) this.optional.get(i);
				optional= optional + "|" + op.getOptional()+"|";
			}
		}
		return optional;
	}

	public void setOptional(ObservableList<Object> optional) {
		this.optional.set(optional);
	}
	public String getPrezzo() {
		return prezzo.get();
	}

	public void setPrezzo(String totale) {
		this.prezzo.set(totale);
	}


}