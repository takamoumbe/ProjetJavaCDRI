package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JOptionPane;

import db.DataBase;

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
	
	/*------ selectionner un utilisateur --------*/
	
	public User select_user(String login, String pass) {
		User user = new User();
		DataBase db = new DataBase();
		db.connect();
		
 		String query = "select * from user where etatUser=0 and login='"+login+"' and password='"+pass+"'";
 		
 		try {
 			PreparedStatement statement = (PreparedStatement) db.getConnection().prepareStatement(query);
 			ResultSet resultSet = statement.executeQuery();
 			
 			if (resultSet.next()) {
 				
 				user.idUser 	= resultSet.getInt(1);
 				user.nomUser	= resultSet.getString(2);
 				user.login  	= resultSet.getString(3);
 				user.password	= resultSet.getString(4);
 				user.contact 	= resultSet.getString(5);
 				user.typeUser	= resultSet.getString(6);
 				user.etatUser	= resultSet.getInt(7);
 				user.statutUser	= resultSet.getString(8);
 				user.createAt 	= resultSet.getString(9);
 				user.updateAt   = resultSet.getString(10);
 				user.deleteAt   = resultSet.getString(11);
 				
 			}else {
 				user = null;
 			}
 					
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Messsage", JOptionPane.CLOSED_OPTION);
		}
		
		return user;
	}
	
	/*----- modifier un utilisateur -----*/
	public boolean update_user(User user) {
		
		DataBase db = new DataBase();
		db.connect();
		
 		String query = "update user set password='"+user.password+"' where etatUser=0 and idUser="+user.idUser+"";
 		
 		try {
 			PreparedStatement statement = (PreparedStatement) db.getConnection().prepareStatement(query);
 			int result = statement.executeUpdate();
 		}catch (Exception e) {
			// TODO: handle exception
 			JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Messsage", JOptionPane.CLOSED_OPTION);
 			return false;
		}
		
		return true;
		
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
