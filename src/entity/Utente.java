/**
 * @author Panarosa Gaetano matricola 609416
 */
package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * Classe POJO Utente
 *
 */
public class Utente {
	private SimpleIntegerProperty ID;
	private SimpleStringProperty username;
	private SimpleStringProperty password;
	private SimpleStringProperty nome;
	private SimpleStringProperty cognome;
	private SimpleStringProperty data;
	private SimpleStringProperty indirizzo;
	private SimpleStringProperty telefono;
	private SimpleStringProperty codiceFiscale;
	private SimpleStringProperty email;
	private SimpleStringProperty paese;
	private SimpleStringProperty town;
	private SimpleStringProperty sex;
	private SimpleIntegerProperty tipo;

	public Utente() {
		this.ID = new SimpleIntegerProperty();
		this.username = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
		this.nome = new SimpleStringProperty();
		this.cognome = new SimpleStringProperty();
		this.data = new SimpleStringProperty();
		this.indirizzo = new SimpleStringProperty();
		this.telefono = new SimpleStringProperty();
		this.codiceFiscale = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.paese = new SimpleStringProperty();
		this.town = new SimpleStringProperty();
		this.sex = new SimpleStringProperty();
		this.tipo = new SimpleIntegerProperty();

	}
	public int getID() {
		return ID.get();
	}

	public void setID(int ID) {
		this.ID.set(ID);
	}

	public String getUsername() {
		return username.get();
	}

	public void setUsername(String username) {
		this.username.set(username);
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public String getNome() {
		return nome.get();
	}

	public void setNome(String nome) {
		this.nome.set(nome);
	}

	public String getCognome() {
		return cognome.get();
	}

	public void setCognome(String cognome) {
		this.cognome.set(cognome);
	}

	public String getData() {
		return data.get();
	}

	public void setData(String data) {
		this.data.set(data);
	}

	public String getIndirizzo() {
		return indirizzo.get();
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo.set(indirizzo);
	}

	public String getTelefono() {
		return telefono.get();
	}

	public void setTelefono(String telefono) {
		this.telefono.set(telefono);
	}

	public String getCodiceFiscale() {
		return codiceFiscale.get();
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale.set(codiceFiscale);
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public String getPaese() {
		return paese.get();
	}

	public void setPaese(String paese) {
		this.paese.set(paese);
	}

	public String getTown() {
		return town.get();
	}

	public void setTown(String town) {
		this.town.set(town);
	}

	public String getSex() {
		return sex.get();
	}

	public void setSex(String sex) {
		this.sex.set(sex);
	}

	public int getTipo() {
		return tipo.get();
	}

	public void setTipo(int tipo) {
		this.tipo.set(tipo);
	}



}
