/**
 * @author Panarosa Gaetano matricola 609416
 */
package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Classe POJO Admin
 *
 */

public class Admin {
	private SimpleStringProperty username;
	private SimpleStringProperty password;
	private SimpleStringProperty nome;
	private SimpleStringProperty cognome;
	private SimpleStringProperty email;
	private SimpleIntegerProperty idAdmin;
	private SimpleIntegerProperty tipo; 

	public Admin(){
		this.username = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
		this.nome = new SimpleStringProperty();
		this.cognome = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.idAdmin = new SimpleIntegerProperty();
		this.tipo = new SimpleIntegerProperty();
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
	public String getEmail() {
		return email.get();
	}
	public void setEmail(String email) {
		this.email.set(email);
	}
	public int getIdAdmin() {
		return idAdmin.get();
	}
	public void setIdAdmin(int idAdmin) {
		this.idAdmin.set(idAdmin);
	}
	
	public int getTipo() {
		return tipo.get();
	}

	public void setTipo(int tipo) {
		this.tipo.set(tipo);
	}


}
