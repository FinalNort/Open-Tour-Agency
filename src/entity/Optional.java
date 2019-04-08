/**
 * @author Panarosa Gaetano matricola 609416
 */

package entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * Classe POJO Optional
 *
 */
public class Optional {
	private SimpleStringProperty idOptional;
	private SimpleStringProperty optional;
	private SimpleStringProperty prezzoOptional;
	private SimpleBooleanProperty stato;

	public Optional() {
		this.idOptional = new SimpleStringProperty();
		this.optional = new SimpleStringProperty();
		this.prezzoOptional = new SimpleStringProperty();
		this.stato = new SimpleBooleanProperty();
	}


	public String getIDOptional() {
		return idOptional.get();
	}

	public void setIDOptional(String idOptional) {
		this.idOptional.set(idOptional);
	}

	public String getOptional() {
		return optional.get();
	}

	public void setOptional(String optional) {
		this.optional.set(optional);
	}

	public String getPrezzo() {
		return prezzoOptional.get();
	}

	public void setPrezzo(String prezzoOptional) {
		this.prezzoOptional.set(prezzoOptional);
	}


	public boolean getStato() {
		return stato.get();
	}

	public void setStato(boolean stato) {
		this.stato.set(stato);
	}
}
