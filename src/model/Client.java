package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import db.DataBase;

public class Client {
	
	public int idClient;
	public String nomClient;
	public String contactClient;
	public int idUser;
	
	public Client(int idClient, String nomClient,  String contactClient, int idUser) {
		this.idClient = idClient;
		this.nomClient= nomClient;
		this.contactClient= contactClient;
		this.idUser=idUser;
	}
	
	public Client() {
		
	}
	
	/*--- inserer client ----*/
	public void inserer_client() {
		
	}
	
	/*---- selectionner client ---*/
	 public void select_client(int idClient) {
		 DataBase db = new DataBase();
		 db.connect();
		 
		 try {
			
			 String query = "select * from client where idClient="+idClient+"";
			 PreparedStatement state = (PreparedStatement) db.getConnection().prepareStatement(query);
				ResultSet resultSet = state.executeQuery();
				
				while (resultSet.next()) {
					this.idClient = resultSet.getInt(1);
					this.nomClient= resultSet.getString(2);
					this.contactClient=resultSet.getString(3);
					this.idUser = resultSet.getInt(4);
				}
				state.close();
				resultSet.close();  
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Message",JOptionPane.OK_OPTION);
		}
		 db.close();
	 }

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public String getContactClient() {
		return contactClient;
	}

	public void setContactClient(String contactClient) {
		this.contactClient = contactClient;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	 
}
