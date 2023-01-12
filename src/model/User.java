package model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class User {
	private static int idUser;
	private String nomUser;
	private static String login;
	private static String password;
	private String contact;
	private String typeUser;
	private int etatUser;
	private String statutUser;
	private String createAt;
	private String updateAt;
	private String deleteAt;
	
	public User(String nomUser, String login, String password, String contact, String typeUser) {
		this.nomUser = nomUser;
		this.login   = login;
		this.password= password;
		this.contact = contact;
		this.typeUser= typeUser;
		this.etatUser= 0; 
		this.statutUser= "active";
		this.createAt = LocalDate.now().toString()+" "+LocalTime.now().toString();
		this.updateAt = LocalDate.now().toString()+" "+LocalTime.now().toString();;
	}
	
	public User() {
		
	}

	public static int getIdUser() {
		return idUser;
	}

	public static void setIdUser(int idUser) {
		User.idUser = idUser;
	}

	public String getNomUser() {
		return nomUser;
	}

	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(String typeUser) {
		this.typeUser = typeUser;
	}

	public int getEtatUser() {
		return etatUser;
	}

	public void setEtatUser(int etatUser) {
		this.etatUser = etatUser;
	}

	public String getStatutUser() {
		return statutUser;
	}

	public void setStatutUser(String statutUser) {
		this.statutUser = statutUser;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public String getDeleteAt() {
		return deleteAt;
	}

	public void setDeleteAt(String deleteAt) {
		this.deleteAt = deleteAt;
	}
	
	
}
