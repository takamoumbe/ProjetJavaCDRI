package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import db.DataBase;

public class Article {
	
	private int idArticle;
	private String codeArticle;
	private String designationArticle;
	private int quantiteArticle;
	private float prixArticle;
	private String emplacement;
	private String createAt;
	private String updateAt;
	private String deleteAt;
	private int nbrePaquet;
	private int etatArticle;
	private int idUser;
	private String fournisseur;
	private String nomArticle;
	
	public int somme_qty = 0;
	public int somme_money = 0;
	
	
	public Article(String codeArticle, String designationArticle, int quantiteArticle, float prixArticle, String emplacement, int nbrePaquet, String fournisseur, String nomArticle) {
		this.codeArticle        = codeArticle;  
		this.designationArticle = designationArticle;
		this.quantiteArticle    = quantiteArticle;  
		this.prixArticle        = prixArticle;
		this.emplacement        = emplacement;
		this.createAt           = LocalDate.now().toString()+" "+LocalTime.now().toString();
		this.updateAt           = LocalDate.now().toString()+" "+LocalTime.now().toString();
		this.deleteAt           = "";
		this.nbrePaquet         = nbrePaquet;
		this.etatArticle        = 0;
		User user    = new User();
		this.idUser  = user.getIdUser(); 
		this.fournisseur = fournisseur;
		this.nomArticle  = nomArticle;  
	}
	
	public Article() {  
		
	}
	
	/*----- modifier l article ---*/
	public boolean update_article(Article article) {
		
		DataBase db = new DataBase();
		db.connect();
		
		try {
			// selectionner le fournisseur
			String query = "select idFournisseur from fournisseur where nomFournisseur = '"+article.fournisseur+"'";  
			PreparedStatement statement1 = (PreparedStatement) db.getConnection().prepareStatement(query);
			ResultSet resultSet = statement1.executeQuery();  
			
			if (resultSet.next()) {
				// selectionner l article
				Article article5 = new Article();
				article5 = article5.getOneArticle(article.codeArticle);
				
				//modifier le fournisseur
				String query2 = "update article_fournisseur set idFournisseur="+resultSet.getInt(1)+" where idArticle = "+article5.idArticle+"";
				PreparedStatement statement2 = (PreparedStatement) db.getConnection().prepareStatement(query2);    
				statement2.executeUpdate();  
				
				// modifier l article
				String query3 = "update article set codeArticle = '"+article.codeArticle+"', nomArticle='"+article.nomArticle+"', designationArticle='"+article.designationArticle+"', quantiteArticle='"+article.quantiteArticle+"', prixArticle='"+article.prixArticle+"', emplacement='"+article.emplacement+"', nbrePaquet='"+article.nbrePaquet+"', updateAt='"+article.updateAt+"' where idArticle='"+article5.idArticle+"'";
				PreparedStatement statement3 = (PreparedStatement) db.getConnection().prepareStatement(query3); 
				statement3.executeUpdate();
				
				statement3.close();  
				statement2.close();
				statement1.close();
				//JOptionPane.showMessageDialog(null, ""+query2, "Message",JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Message",JOptionPane.OK_OPTION);
		}
		
		db.close();
		
		return false;
		
		
	}
	
	/*----- Inserer un article ---*/
	public void insert_article(Article article) { 
		DataBase db = new DataBase();
		db.connect();
		
		try { 
			 // verifier si l'article existe deja
			 String query  = "Select * from article where codeArticle='"+article.codeArticle+"' and etatArticle=0";
			 
			 PreparedStatement state = (PreparedStatement) db.getConnection().prepareStatement(query);
			 ResultSet resultSet = state.executeQuery();
			 
			 if (resultSet.next()) {
				 JOptionPane.showMessageDialog(null, "Cet article existe déja", "Message",JOptionPane.OK_OPTION);
			}else {
				// inserer l'article
				String query1 = "insert into article values(null,'"+article.codeArticle+"','"+article.nomArticle+"','"+article.designationArticle+"','"+article.quantiteArticle+"','"+article.prixArticle+"','"+article.emplacement+"','"+article.createAt+"','"+article.updateAt+"','"+article.deleteAt+"','"+article.nbrePaquet+"','"+article.etatArticle+"','"+article.idUser+"')";
				PreparedStatement st = (PreparedStatement) db.getConnection().prepareStatement(query1);  
				 st.executeUpdate();
				 st.close();
				// joindre l'article au fournisseur
				 String query2 = "select idArticle from article where etatArticle = 0 order by idArticle DESC";
				 PreparedStatement statement = (PreparedStatement) db.getConnection().prepareStatement(query2);
				 ResultSet resultSet2 = statement.executeQuery();
				 if (resultSet2.next()) {
					 String query3 = "select idFournisseur from fournisseur where nomFournisseur = '"+article.fournisseur+"'";
					 PreparedStatement statement2 = (PreparedStatement) db.getConnection().prepareStatement(query3);
					 ResultSet resultSet3 = statement2.executeQuery();
					 if (resultSet3.next()) {
						String query4 = "insert into article_fournisseur values('"+resultSet2.getInt(1)+"', '"+resultSet3.getInt(1)+"')";
						PreparedStatement statement3 = (PreparedStatement) db.getConnection().prepareStatement(query4); 
						statement3.executeUpdate();
						
						 statement3.close();
						 st.close();
						 db.close();
						 JOptionPane.showMessageDialog(null, "Article enregistrer", "Message",JOptionPane.INFORMATION_MESSAGE);
					}
					statement2.close();
					resultSet3.close();
				}
				 statement.close();
				 resultSet2.close();
				 
			}
			 
		} catch (Exception e) {  
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Message",JOptionPane.OK_OPTION);
		}
		db.close();
	}
	
	/*----- Liste des article  ---*/
	public Object[][] liste_article(){
		Object[][] liste = null;
		DataBase db = new DataBase();
		
		db.connect();
		try {
			String query  = "select * from article where etatArticle = 0";
			PreparedStatement state = (PreparedStatement) db.getConnection().prepareStatement(query);
			ResultSet resultSet = state.executeQuery();
			
			int count =0;
			while (resultSet.next()) { 
				count++; 
			}
			liste = new Object[count][8];
			resultSet = null;
			resultSet = state.executeQuery();  
			int i = 0;
			while (resultSet.next()) {
				liste[i][0] = i+1;
				liste[i][1] = resultSet.getString(2);
				liste[i][2] = resultSet.getString(3);
				liste[i][3] = resultSet.getString(4);
				liste[i][4] = resultSet.getString(5);
				liste[i][5] = resultSet.getString(6)+" fcfa";
				liste[i][6] = Integer.parseInt(resultSet.getString(6))*Integer.parseInt(resultSet.getString(5))+" xaf";
				liste[i][7] = resultSet.getString(7);  
				i++; 
			}
			
			state.close();
			resultSet.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Message",JOptionPane.OK_OPTION);
		}
		
		db.close();
		
		return liste;
		
	}
	
	/*----- liste code des articles ---*/
	public String[] liste_code_article() {
		DataBase db = new DataBase();
		db.connect();
		String[] liste = new String[0];
		
		try {
			String query  = "select * from article where etatArticle = 0";
			PreparedStatement state = (PreparedStatement) db.getConnection().prepareStatement(query);
			ResultSet resultSet = state.executeQuery();
			
			int count =0;
			while (resultSet.next()) { 
				count++; 
			}
			
			liste = new String[count+1];
			resultSet = state.executeQuery();
			int i = 1;
			liste[0] = "Code Article"; 
			
			while (resultSet.next()) {
				liste[i] = resultSet.getString(2);  
				i++; 
			}
			
			state.close();
			resultSet.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Erreur",JOptionPane.OK_OPTION);
		}
		
		db.close();
		return liste;
	}
	
	/*----- supprimer article ----------*/
	public void delete_article(String code) {
		int count = 0;
		DataBase db = new DataBase();   
		db.connect();
		
		try {
			String query1 = "update article set etatArticle = 1 where codeArticle='"+code+"'";
			PreparedStatement st = (PreparedStatement) db.getConnection().prepareStatement(query1);    
			 st.executeUpdate();
			 count = 1;
			 JOptionPane.showMessageDialog(null, "Suppression reussir", "Message",JOptionPane.INFORMATION_MESSAGE);
			 db.close();
			 st.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur lors de l'opération", "Message",JOptionPane.OK_OPTION);
		}
		
	}
	
	/*------ selectionner un article -----*/
	public Article getOneArticle(String code) {
		DataBase db = new DataBase();
		db.connect();
		Article article = new Article();
		try {
			String query = "select * from article,article_fournisseur,fournisseur  where codeArticle='"+code+"' and etatArticle=0 and article_fournisseur.idArticle = article.idArticle and article_fournisseur.idFournisseur = fournisseur.idFournisseur and etatFournisseur = 0";
			PreparedStatement statement = (PreparedStatement) db.getConnection().prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery();
			 if (resultSet.next()) {
				 // contruction de l object article
				 article.setIdArticle(resultSet.getInt(1));
				 article.setCodeArticle(resultSet.getString(2));
				 article.setNomArticle(resultSet.getString(3));
				 article.setDesignationArticle(resultSet.getString(4));
				 article.setQuantiteArticle(resultSet.getInt(5));
				 article.setPrixArticle(resultSet.getInt(6));
				 article.setEmplacement(resultSet.getString(7));
				 article.setNbrePaquet(resultSet.getInt(11));
				 article.setFournisseur(resultSet.getString(17));   
			 }else {
				 JOptionPane.showMessageDialog(null, "Erreur lors de l'opération", "Message",JOptionPane.OK_OPTION);
			 }
			 statement.close();
			 resultSet.close();
		} catch (Exception e) {  
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur lors de l'opération", "Message",JOptionPane.OK_OPTION);
		}
		db.close();
		return article;
	}
	
	/*--- entrer en stock ----*/
	public void entrer_stock(DefaultTableModel table_model) {
		DataBase db = new DataBase();
		db.connect();
		
		try {
			
			int row = table_model.getRowCount();
			String code = "";
			String quantite = "";
			String prix = "";
			
			for (int i = 0; i < row; i++) {
				
				code 		= table_model.getValueAt(i, 1).toString();
				quantite 	= table_model.getValueAt(i, 4).toString();
				prix 		= table_model.getValueAt(i, 5).toString();
				
				String[] qu = quantite.split("|");
				String[] pr = prix.split("f");
				
				String qu_ = "";
				String pr_ = "";
				
				 for (String word : qu) {
					 if (word.equals("|")) {
						break;
					}
					 qu_ = qu_+""+word;
				}
				 
				 for (String words : pr) {
					 if (words.equals("c")) {
						break;
					}  
					 pr_ = pr_+""+words;
				}
				 
				 int quantite_ = 0; 
				 float prix_     = 0;
				
				try {
					quantite_ = Integer.parseInt(qu_.trim());
					prix_     =  Float.parseFloat(pr_.trim()); 
					
					//JOptionPane.showMessageDialog(null, "quantite="+quantite_+" prix="+prix_, "Message",JOptionPane.OK_OPTION);
					// selectionner l'article
					String query = "select * from article,article_fournisseur,fournisseur  where codeArticle='"+code+"' and etatArticle=0 and article_fournisseur.idArticle = article.idArticle and article_fournisseur.idFournisseur = fournisseur.idFournisseur and etatFournisseur = 0";
					PreparedStatement statement = (PreparedStatement) db.getConnection().prepareStatement(query);
					 ResultSet resultSet = statement.executeQuery();
					 if (resultSet.next()) {
						 // quantite
						 int result_qty  = resultSet.getInt(5);
						 int result_prix =  resultSet.getInt(6);
						 // preparer l article a modifier
						 Article article = new Article();
						 if (prix_> result_prix) {
							article.setPrixArticle(prix_);
						}else {
							article.setPrixArticle(result_prix);
						}
						 article.setQuantiteArticle(quantite_+result_qty);
						 
						 String query1 = "update article set quantiteArticle = '"+article.quantiteArticle+"', prixArticle='"+article.prixArticle+"' where codeArticle='"+code+"'";
						 PreparedStatement st = (PreparedStatement) db.getConnection().prepareStatement(query1);    
						 st.executeUpdate();
						 
						 JOptionPane.showMessageDialog(null, "Entrée de stock valider", "Message",JOptionPane.INFORMATION_MESSAGE);
						 
						 // vider le tableau
						 int row2 = table_model.getRowCount();
						 int j= row2-1;
						 while (j>=0) {
							 table_model.removeRow(j);
							 j--;
						}
						 st.close();
						 statement.close();
						 resultSet.close();
					 }
					
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Message",JOptionPane.OK_OPTION);
				}
				db.close();
			}
					
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Message",JOptionPane.OK_OPTION);
		}
	}
	
	/*---- commander un article ----*/
	public boolean commander_article(DefaultTableModel table_model, String nom_client) {
		
		DataBase db = new DataBase();
		db.connect();
		
		int row = table_model.getRowCount();
		String code = "";
		String quantite = "";
		String prix = "";
	
		try {
			
			 // insererer la client et les joindre
			 User user = new User();
			 String query_client = "insert into client values(null, '"+nom_client+"','', "+user.getIdUser()+")";
			 PreparedStatement statements = (PreparedStatement) db.getConnection().prepareStatement(query_client);  
			 statements.executeUpdate();
			// recuperer l id du client qui vient d'entrer
			 String query3 = "select idClient from client order by idClient DESC";
			 PreparedStatement state = (PreparedStatement) db.getConnection().prepareStatement(query3);
			 ResultSet resultSet3 = state.executeQuery();
			
			 int id_client = 0;
			 if (resultSet3.next()) {
				 id_client = resultSet3.getInt(1);
			 }
			 String mum_fact = "FACT"+id_client;
			 statements.close();
			 state.close();
			 resultSet3.close();
			 
		for (int i = 0; i < row; i++) {
			
			code 		= table_model.getValueAt(i, 1).toString();
			quantite 	= table_model.getValueAt(i, 4).toString();
			prix 		= table_model.getValueAt(i, 5).toString();
			
			String[] qu = quantite.split("|");
			String[] pr = prix.split("f");
			
			String qu_ = "";
			String pr_ = "";
			
			 for (String word : qu) {
				 if (word.equals("|")) {
					break;
				}
				 qu_ = qu_+""+word;
			}
			 
			 for (String words : pr) {
				 if (words.equals("c")) {
					break;
				}  
				 pr_ = pr_+""+words;
			}
			 
			 int quantite_ = 0; 
			 float prix_     = 0;
			
			try {
				quantite_ = Integer.parseInt(qu_.trim());
				prix_     =  Float.parseFloat(pr_.trim()); 
				
				//JOptionPane.showMessageDialog(null, "quantite="+quantite_+" prix="+prix_, "Message",JOptionPane.OK_OPTION);
				// selectionner l'article
				String query = "select * from article,article_fournisseur,fournisseur  where codeArticle='"+code+"' and etatArticle=0 and article_fournisseur.idArticle = article.idArticle and article_fournisseur.idFournisseur = fournisseur.idFournisseur and etatFournisseur = 0";
				PreparedStatement statement = (PreparedStatement) db.getConnection().prepareStatement(query);
				 ResultSet resultSet = statement.executeQuery();
				 if (resultSet.next()) {
					 // quantite
					 int result_qty  = resultSet.getInt(5);
					 int result_prix =  resultSet.getInt(6);
					 // preparer l article a modifier
					 Article article = new Article();
					article.setPrixArticle(prix_);
					article.setIdArticle(resultSet.getInt(1));
					
					 article.setQuantiteArticle(result_qty-quantite_);
					 // sortir le stock
					 String query1 = "update article set quantiteArticle = '"+article.quantiteArticle+"' where codeArticle='"+code+"'";
					 PreparedStatement st = (PreparedStatement) db.getConnection().prepareStatement(query1);    
					 st.executeUpdate();
					
					 //remplir la table commande
					 String date_create = LocalDate.now().toString();  
					 String query_commande = "insert into commande values(null,'"+mum_fact+"', '"+date_create+"', 0, "+article.quantiteArticle+", "+article.prixArticle+", "+id_client+", "+article.idArticle+")";
					 //JOptionPane.showMessageDialog(null, ""+query_commande, "Message",JOptionPane.OK_OPTION);
					 PreparedStatement states = (PreparedStatement) db.getConnection().prepareStatement(query_commande);  
					 states.executeUpdate();
					 
					 // valider la sortir de stock
					 String query_sortir = "insert into sortie values(null, "+article.quantiteArticle+", '"+date_create+"', "+article.prixArticle+", "+article.idArticle+", "+user.getIdUser()+")";
					 PreparedStatement statese = (PreparedStatement) db.getConnection().prepareStatement(query_sortir);  
					 statese.executeUpdate();
					 
					 // vider le tableau
					 int row2 = table_model.getRowCount();
					 int j= row2-1;
					 while (j>=0) {
						 table_model.removeRow(j);
						 j--;
					}
					 db.close();
					 statese.close();
					 states.close();
					 st.close();
					 statement.close();
					 resultSet.close();
					 
					 
					  return true;
				 }
				
			} catch (Exception e) {
				// TODO: handle exception
				//return false;
				JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Message",JOptionPane.OK_OPTION);
			}
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Message",JOptionPane.OK_OPTION);
	}
		
		db.close();
		return false;
}
	
	/*---- liste des factures ---*/
	public Object[][] liste_facture() {  
		DataBase db = new DataBase();
		db.connect();
		Object[][] liste = null;
		
		try {
			String date = LocalDate.now().toString();
			String query  = "select distinct num_commande from commande  where dateCommande='"+date+"' order by num_commande desc";
			PreparedStatement state = (PreparedStatement) db.getConnection().prepareStatement(query);
			ResultSet resultSet = state.executeQuery();
			
			String num_commande   = "";
			int count =0;
			while (resultSet.next()) { 
				count++; 
			}
			liste = new Object[count][6];
			resultSet = state.executeQuery();  
			int i = 0;
			
			while (resultSet.next()) { 
				
				num_commande     = resultSet.getString(1);
				String query2 = "select * from commande where num_commande='"+num_commande+"' and dateCommande='"+date+"'";
				PreparedStatement state2 = (PreparedStatement) db.getConnection().prepareStatement(query2);
				ResultSet resultSet2 = state2.executeQuery();
				
				Client cl = new Client();
				resultSet2.next();
				cl.select_client(resultSet2.getInt(7));
				int quantite_commande = 0;
				int prix_commande     = 0;
				String etat = "";
				
				resultSet2 = null;
				resultSet2 = state2.executeQuery();
				
				while (resultSet2.next()) {
					quantite_commande = quantite_commande+resultSet2.getInt(5);
					prix_commande = prix_commande + (resultSet2.getInt(5)*resultSet2.getInt(6));
					if (resultSet2.getInt(4) == 1) {
						etat = "payer";
					}else {
						etat = "impayer";
					}
				}
				
				liste[i][0] = i+1;
				liste[i][1] = num_commande;
				liste[i][2] = cl.nomClient;
				liste[i][3] = etat;
				liste[i][4] = quantite_commande;
				liste[i][5] = prix_commande+" fcfa";
				i++; 
				
				//JOptionPane.showMessageDialog(null, "ok "+quantite_commande, "Message",JOptionPane.OK_OPTION);
				resultSet2.close();
				state2.close();
			}
			state.close();
			resultSet.close();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Message",JOptionPane.OK_OPTION);
		}
		
		db.close();
		return liste;
	}
	
	
	/*---- liste des factures impayées ---*/
	public Object[][] liste_facture_impaye() {  
		DataBase db = new DataBase();
		db.connect();
		Object[][] liste = null;
		
		try {
			String date = LocalDate.now().toString();
			String query  = "select distinct num_commande from commande  where dateCommande='"+date+"' and etatCommande=0 order by num_commande desc";
			PreparedStatement state = (PreparedStatement) db.getConnection().prepareStatement(query);
			ResultSet resultSet = state.executeQuery();
			
			String num_commande   = "";
			int count =0;
			while (resultSet.next()) { 
				count++; 
			}
			liste = new Object[count][6];
			resultSet = state.executeQuery();  
			int i = 0;
			
			while (resultSet.next()) { 
				
				num_commande     = resultSet.getString(1);
				String query2 = "select * from commande where num_commande='"+num_commande+"' and etatCommande=0 and dateCommande='"+date+"'";
				PreparedStatement state2 = (PreparedStatement) db.getConnection().prepareStatement(query2);
				ResultSet resultSet2 = state2.executeQuery();
				
				Client cl = new Client();
				resultSet2.next();
				cl.select_client(resultSet2.getInt(7));
				int quantite_commande = 0;
				int prix_commande     = 0;
				String etat = "";
				
				resultSet2 = null;
				resultSet2 = state2.executeQuery();
				
				while (resultSet2.next()) {
					quantite_commande = quantite_commande+resultSet2.getInt(5);
					prix_commande = prix_commande + (resultSet2.getInt(5)*resultSet2.getInt(6));
					if (resultSet2.getInt(4) == 1) {
						etat = "payer";
					}else {
						etat = "impayer";
					}
				}
				
				liste[i][0] = i+1;
				liste[i][1] = num_commande;
				liste[i][2] = cl.nomClient;
				liste[i][3] = etat;
				liste[i][4] = quantite_commande;
				liste[i][5] = prix_commande+"fcfa";
				i++; 
				
				this.somme_qty = this.somme_qty+ quantite_commande;
				this.somme_money = this.somme_money+prix_commande;
				//JOptionPane.showMessageDialog(null, "ok "+quantite_commande, "Message",JOptionPane.OK_OPTION);
				resultSet2.close();
				state2.close();
			}
			state.close();
			resultSet.close();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Message",JOptionPane.OK_OPTION);
		}
		
		db.close();
		return liste;
	}
	
	/*---- liste des factures impayées ---*/
	public Object[][] liste_facture_paye() {  
		DataBase db = new DataBase();
		db.connect();
		Object[][] liste = null;
		
		try {
			String date = LocalDate.now().toString();
			String query  = "select distinct num_commande from commande  where dateCommande='"+date+"' and etatCommande=1 order by num_commande desc";
			PreparedStatement state = (PreparedStatement) db.getConnection().prepareStatement(query);
			ResultSet resultSet = state.executeQuery();
			
			String num_commande   = "";
			int count =0;
			while (resultSet.next()) { 
				count++; 
			}
			liste = new Object[count][6];
			resultSet = state.executeQuery();  
			int i = 0;
			
			while (resultSet.next()) { 
				
				num_commande     = resultSet.getString(1);
				String query2 = "select * from commande where num_commande='"+num_commande+"' and etatCommande=1 and dateCommande='"+date+"'";
				PreparedStatement state2 = (PreparedStatement) db.getConnection().prepareStatement(query2);
				ResultSet resultSet2 = state2.executeQuery();
				
				Client cl = new Client();
				resultSet2.next();
				cl.select_client(resultSet2.getInt(7));
				int quantite_commande = 0;
				int prix_commande     = 0;
				String etat = "";
				
				resultSet2 = null;
				resultSet2 = state2.executeQuery();
				
				while (resultSet2.next()) {
					quantite_commande = quantite_commande+resultSet2.getInt(5);
					prix_commande = prix_commande + (resultSet2.getInt(5)*resultSet2.getInt(6));
					if (resultSet2.getInt(4) == 1) {
						etat = "payer";
					}else {
						etat = "impayer";
					}
				}
				
				liste[i][0] = i+1;
				liste[i][1] = num_commande;
				liste[i][2] = cl.nomClient;
				liste[i][3] = etat;
				liste[i][4] = quantite_commande;
				liste[i][5] = prix_commande+" fcfa";
				i++; 
				
				this.somme_qty = this.somme_qty+ quantite_commande;
				this.somme_money = this.somme_money+prix_commande;
				//JOptionPane.showMessageDialog(null, "ok "+quantite_commande, "Message",JOptionPane.OK_OPTION);
				resultSet2.close();
				state2.close();
			}
			state.close();
			resultSet.close();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Message",JOptionPane.OK_OPTION);
		}
		
		db.close();
		return liste;
	}
	
	/*------ valider l encaissement d'une facture ---*/
	public boolean encaisser_fact(String num_fact) {
		DataBase db = new DataBase();
		db.connect();
		
		try {
			String query = "update commande set etatCommande=1 where num_commande='"+num_fact+"' ";
			PreparedStatement st = (PreparedStatement) db.getConnection().prepareStatement(query);    
			 st.executeUpdate();
			 
			 st.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Message",JOptionPane.OK_OPTION);
		}
		db.close();
		return false;
	}
	
	public int getIdArticle() {  
		return idArticle;
	}


	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}


	public String getCodeArticle() {
		return codeArticle;
	}


	public void setCodeArticle(String codeArticle) {
		this.codeArticle = codeArticle;
	}


	public String getDesignationArticle() {
		return designationArticle;
	}


	public void setDesignationArticle(String designationArticle) {
		this.designationArticle = designationArticle;
	}


	public int getQuantiteArticle() {
		return quantiteArticle;
	}


	public void setQuantiteArticle(int quantiteArticle) {
		this.quantiteArticle = quantiteArticle;
	}


	public double getPrixArticle() {
		return prixArticle;
	}


	public void setPrixArticle(float prixArticle) {
		this.prixArticle = prixArticle;
	}


	public String getEmplacement() {
		return emplacement;
	}


	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
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


	public int getNbrePaquet() {
		return nbrePaquet;
	}


	public void setNbrePaquet(int nbrePaquet) {
		this.nbrePaquet = nbrePaquet;
	}


	public int getEtatArticle() {
		return etatArticle;
	}


	public void setEtatArticle(int etatArticle) {
		this.etatArticle = etatArticle;
	}


	public int getIdUser() {
		return idUser;
	}


	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	@Override
	public String toString() {
		return "Article [idArticle=" + idArticle + ", codeArticle=" + codeArticle + ", designationArticle="
				+ designationArticle + ", quantiteArticle=" + quantiteArticle + ", prixArticle=" + prixArticle
				+ ", emplacement=" + emplacement + ", createAt=" + createAt + ", updateAt=" + updateAt + ", deleteAt="
				+ deleteAt + ", nbrePaquet=" + nbrePaquet + ", etatArticle=" + etatArticle + ", idUser=" + idUser
				+ ", fournisseur=" + fournisseur + ", nomArticle=" + nomArticle + "]";
	}
	
	
	
}
