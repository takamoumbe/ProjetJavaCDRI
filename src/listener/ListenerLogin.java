package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Provider.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import db.DataBase;
import model.User;
import view.Caisse;
import view.EntreeStock;
import view.Login;
import view.Services;

public class ListenerLogin implements ActionListener{
	
	private Login login;
	
	public ListenerLogin(Login login) {
		this.login = login; 
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		Object ob = ev.getSource();
		if (ob == login.bconnexion) {
			String login_clavier    = login.tlogin.getText().trim();
			String password         = login.tpassword.getText().trim(); 
			 
			if (login_clavier.equals("") || password.equals("")) { 
				// afficher le message sur la page
				JOptionPane.showMessageDialog(null, "Svp Remplisser tous les champs", "Message", JOptionPane.OK_OPTION);
			}else {
				//filtrer la taille du login et mot de passe
				
				// une fois valider connexion a la base de donnee
				DataBase db = new DataBase();
				db.connect();
				// preparation de la requete
				try {
					String query1 = "Select * from user where login='"+login_clavier+"' and password='"+password+"'";
					PreparedStatement st = (PreparedStatement) db.getConnection().prepareStatement(query1);
					
					
					ResultSet rs = st.executeQuery();
					if (rs.next()) {
		
						String type_user = rs.getString(6);
						int id_user      = rs.getInt(1);
						String login     = rs.getString(3); 
						User user = new User();
						user.setIdUser(id_user);
						user.setLogin(login);
						user.setTypeUser(type_user);
						// tester les role des utilisateurs
						if (type_user.equals("gestionnaire")) {
							this.login.dispose();
							EntreeStock entreeStock = new EntreeStock();
							entreeStock.setVisible(true);
						}else if (type_user.equals("acceuil")) {
							this.login.dispose();
							Services service = new Services();
							service.setVisible(true);
						}else if (type_user.equals("caissier")) {
							this.login.dispose();
							Caisse caisse = new Caisse();
							caisse.setVisible(true);
						}else if (type_user.equals("boss")) {
							
						}
						
					}else { 
						JOptionPane.showMessageDialog(null, "login ou mot de passe incorrect", "Message", JOptionPane.OK_OPTION);
					}
					db.close();
				} catch (SQLException e) { 
					// TODO Auto-generated catch block 
					e.printStackTrace();
				}
				
			}
		}
	}

}
