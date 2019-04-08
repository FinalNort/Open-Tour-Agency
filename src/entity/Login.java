package entity;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Login {

	private SimpleIntegerProperty idLogin;
	private SimpleStringProperty username;
	private SimpleStringProperty password;
	private SimpleIntegerProperty tipo;
	
	private SimpleIntegerProperty status;
	
	public Login() {
		this.idLogin = new SimpleIntegerProperty();
		this.username = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
		this.tipo = new SimpleIntegerProperty();
		this.status = new SimpleIntegerProperty();
	}
	
	public int getIDLogin() {
		return idLogin.get();
	}

	public void setIDLogin(int idLogin) {
		this.idLogin.set(idLogin);
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
	
	public int getTipo() {
		return tipo.get();
	}
	
	public void setTipo(int tipo) {
		this.tipo.set(tipo);
	}
	
	public int getStatus() {
		return status.get();
	}
	
	public void setStatus(int status) {
		this.status.set(status);
	}
}
