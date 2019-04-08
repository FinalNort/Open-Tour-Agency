/**
 * @author Panarosa Gaetano matricola 609416
 */

package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import java.util.Iterator;

/**
 * Classe POJO Escursione
 *
 */
public class Escursione {
	private SimpleIntegerProperty idUtente;
	private SimpleIntegerProperty idEscursione;
	private SimpleIntegerProperty idAdmin;
	private SimpleStringProperty codiceEscursione;
	private SimpleIntegerProperty idTipoEscursione;
	private SimpleStringProperty tipoEscursione;
	private SimpleStringProperty dataEscursione;
	private SimpleStringProperty minPartecipanti;
	private SimpleStringProperty maxPartecipanti;
	private SimpleStringProperty postiDisponibili;
	private SimpleIntegerProperty idStato;
	private SimpleStringProperty stato;
	private SimpleListProperty<Object> optional;
	private SimpleStringProperty prezzo;


	public Escursione() {
		this.idUtente = new SimpleIntegerProperty();
		this.idEscursione = new SimpleIntegerProperty();
		this.idAdmin = new SimpleIntegerProperty();
		this.idTipoEscursione = new SimpleIntegerProperty();
		this.tipoEscursione = new SimpleStringProperty();
		this.dataEscursione = new SimpleStringProperty();
		this.minPartecipanti = new SimpleStringProperty();
		this.maxPartecipanti = new SimpleStringProperty();
		this.postiDisponibili=new SimpleStringProperty();
		this.idStato = new SimpleIntegerProperty();
		this.stato = new SimpleStringProperty();
		this.optional = new SimpleListProperty<Object>();
		this.prezzo = new SimpleStringProperty();
		this.codiceEscursione = new SimpleStringProperty();

	}


	public int getIDUtente() {
		return idUtente.get();
	}

	public void setIDUtente(int idUtente) {
		this.idUtente.set(idUtente);
	}

	public int getIDEscursione() {
		return idEscursione.get();
	}

	public void setIDEscursione(int idEscursione) {
		this.idEscursione.set(idEscursione);
	}
	
	public int getIDAdmin() {
		return idAdmin.get();
	}
	
	public void setIDAdmin(int idAdmin) {
		this.idAdmin.set(idAdmin);
	}
	
	public int getIDTipoEscursione() {
		return idTipoEscursione.get();
	}
	
	public void setIDTipoEscursione(int idTipoEscursione) {
		this.idTipoEscursione.set(idTipoEscursione);
	}
	
	public String getTipoEscursione() {
		return tipoEscursione.get();
	}

	public void setTipoEscursione(String tipoEscursione) {
		this.tipoEscursione.set(tipoEscursione);
	}

	public String getDataEscursione() {
		return dataEscursione.get();
	}

	public void setDataEscursione(String dataEscursione) {
		this.dataEscursione.set(dataEscursione);
	}

	public String getMinPartecipanti() {
		return minPartecipanti.get();
	}

	public void setMinPartecipanti(String minPartecipanti) {
		this.minPartecipanti.set(minPartecipanti);
	}

	public String getMaxPartecipanti() {
		return maxPartecipanti.get();
	}

	public void setMaxPartecipanti(String maxPartecipanti) {
		this.maxPartecipanti.set(maxPartecipanti);
	}
	public String getPostiDisponibili() {
		return postiDisponibili.get();
	}

	public void setPostiDisponibili(String postiDisponibili) {
		this.postiDisponibili.set(postiDisponibili);
	}


	public ObservableList<Object> getOptional(int i) {
		return optional;
	}

	public String getOptional() {
		String optional="Nessun optional";
		Iterator<Object> it = this.optional.iterator();
			optional="";
			for (int i = 0; it.hasNext(); it.next(), i++) {
				entity.Optional op = (entity.Optional) this.optional.get(i);
				optional= optional + "|" + op.getOptional()+"| ";
			}
		
		return optional;
	}

	public void setOptional(ObservableList<Object> optional) {
		this.optional.set(optional);
	}

	public int getIDStato() {
		return idStato.get();
	}
	
	public void setIDStato(int idStato) {
		this.idStato.set(idStato);
	}
	
	public String getStato() {
		return stato.get();
	}

	public void setStato(String stato) {
		this.stato.set(stato);
	}

	public String getPrezzo() {
		return prezzo.get();
	}

	public void setPrezzo(String prezzo) {
		this.prezzo.set(prezzo);
	}

	public String getCodiceEscursione(){
		return codiceEscursione.get();
	}

	public void setCodiceEscursione(String codiceEscursione){
		this.codiceEscursione.set(codiceEscursione);
	}

}
