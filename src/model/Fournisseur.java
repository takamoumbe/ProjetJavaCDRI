package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import db.DataBase;

public class Fournisseur {
		
	private String nomFour;
	private String ville;
	private String pays;
	private String addresse;
	private String tel;
	private int idUser;
	private int etatFournisseur;
	
	public Fournisseur(String nomFour, String ville, String pays, String addrresse, String tel) {
		this.nomFour = nomFour;
		this.ville   = ville;
		this.pays    = pays;
		this.addresse= addresse;
		this.tel     = tel;
		User user    = new User();
		this.idUser  = user.getIdUser(); 
		this.etatFournisseur = 0;
	}
	
	public Fournisseur() {
		
	}
	
	/*----- Inserer un fournisseur ----*/
	public void insertFournisseur(Fournisseur fournisseur) { 
		DataBase db = new DataBase();
		 db.connect();
		 try {
			 // verifier si l'utilisateur existe
			 String query  = "Select * from fournisseur where nomFournisseur='"+fournisseur.nomFour+"' and addressFournisseur='"+fournisseur.addresse+"' and villeFournisseur='"+fournisseur.ville+"' and paysFournisseur='"+fournisseur.pays+"' and telFournisseur='"+fournisseur.tel+"' and etatFournisseur=0";
			 PreparedStatement state = (PreparedStatement) db.getConnection().prepareStatement(query);
			 ResultSet resultSet = state.executeQuery();
			 if (resultSet.next()) {
				 JOptionPane.showMessageDialog(null, "Ce fournisseur existe déja", "Message",JOptionPane.OK_OPTION);
			}else {
				String query1 = "insert into fournisseur values(null,'"+fournisseur.nomFour+"','"+fournisseur.addresse+"','"+fournisseur.ville+"','"+fournisseur.pays+"','"+fournisseur.tel+"','"+fournisseur.etatFournisseur+"','"+fournisseur.idUser+"')";
				 PreparedStatement st = (PreparedStatement) db.getConnection().prepareStatement(query1);
				 st.executeUpdate();
				 st.close();
				 db.close();
				 JOptionPane.showMessageDialog(null, "Fournisseur enregistrer", "Message",JOptionPane.INFORMATION_MESSAGE);
			}
			 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur lors de l'opération", "Message",JOptionPane.OK_OPTION);
		}
		
		
	}
	
	/*-- liste de fournisseur ---*/
	@SuppressWarnings("null")
	public String[] liste_fournisseur(){
		String[] fourn = null;
		DataBase db = new DataBase();
		db.connect();
		try {
			String query  = "Select * from fournisseur where etatFournisseur=0";
			PreparedStatement state = (PreparedStatement) db.getConnection().prepareStatement(query);
			ResultSet resultSet = state.executeQuery();
			
			int count =0;
			while (resultSet.next()) { 
				count++; 
			}
			fourn = new String[count+1];
			resultSet = null;
			resultSet = state.executeQuery();
			int i =1; 
			fourn[i-1] = "Founisseur";
			while (resultSet.next()) {
				String nom = resultSet.getString(2);
				fourn[i] = nom;
				i++; 
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		db.close();
		return fourn;
		
	}
	
	public String getNomFour() {
		return nomFour;
	}

	public void setNomFour(String nomFour) {
		this.nomFour = nomFour;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getAddresse() {
		return addresse;
	}

	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}

	public String getTel() {
		return tel; 
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	
}
