package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

import db.DataBase;
import model.Article;
import model.Fournisseur;
import view.EditerArticle;
import view.EntreeQuantiteStock;
import view.EntreeStock;
import view.Login;
import view.ShowArticle;

public class ListenerGestionnaire implements ActionListener {
	public EntreeStock gestionnaire;
	
	public ListenerGestionnaire(EntreeStock gestionnaire) {
		this.gestionnaire = gestionnaire;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		Object ob = ev.getSource();
					
		if (ob == this.gestionnaire.saveFoursseur) { 
			String nomFour = this.gestionnaire.tnomFournisseur.getText().trim();
			String ville   = this.gestionnaire.tville.getText().trim();
			String pays    = this.gestionnaire.tpays.getText().trim();
			String addresse= this.gestionnaire.taddresse.getText().trim();
			String tel     = this.gestionnaire.ttel.getText().trim();
			
			if (nomFour.equals("") || ville.equals("") || pays.equals("") || addresse.equals("") || tel.equals("")) {
				JOptionPane.showMessageDialog(null, "Remplir tous les champs","Message",JOptionPane.YES_OPTION);
			}else {   
				 Fournisseur fournisseur = new Fournisseur(nomFour, ville, pays, addresse, tel);
				 fournisseur.insertFournisseur(fournisseur);  
				 this.reset_form_fournisseur();
				 this.gestionnaire.tfournisseur.addItem(fournisseur.getNomFour());
				 /*Fournisseur fournisseur2 = new Fournisseur();
				 this.gestionnaire.tfournisseur = new JComboBox(fournisseur2.liste_fournisseur());*/
			}
			
		}else if (ob == this.gestionnaire.resetFournisseur) {  
			int response = JOptionPane.showConfirmDialog(null, "Voulez-vous annuler l'opération ?", "Message", JOptionPane.CANCEL_OPTION);
			if (response == 0) {
				this.reset_form_fournisseur();
			}
		}else if (ob == this.gestionnaire.saveArticle) {
			
			String codeArt 		= this.gestionnaire.tcode_art.getText().trim();
			String designa   	= this.gestionnaire.tdesignation.getSelectedItem().toString().trim();
			String prix    		= this.gestionnaire.tprix.getText().trim();
			String qty			= this.gestionnaire.tquantite.getText().trim();
			String fournisseur  = this.gestionnaire.tfournisseur.getSelectedItem().toString().trim();
			String emplacement  = this.gestionnaire.templacement.getText().trim();
			String nomArticle   = this.gestionnaire.tdate.getText().trim();
			
			if (codeArt.equals("") || designa.equals("Designation") || prix.equals("") || qty.equals("") || fournisseur.equals("Founisseur") || emplacement.equals("") || nomArticle.equals("")) {
				JOptionPane.showMessageDialog(null, "Remplir tous les champs","Message",JOptionPane.YES_OPTION);
			}else {
				// compatibilisté des types des champs
				int quantite = 0;  
				int prix_uni = 0;
				try {
					quantite = Integer.parseInt(qty);
					prix_uni = Integer.parseInt(prix);
					Article article = new Article(codeArt, designa, 0, prix_uni, emplacement, quantite, fournisseur, nomArticle);
					article.insert_article(article); 
					this.reset_form_article();  
					// actualiser le tableau
					Object[] model_article = new Object[8];
					int row = this.gestionnaire.modele.getRowCount();
					model_article[0] = row+1;
					model_article[1] = codeArt;
					model_article[2] = nomArticle;
					model_article[3] = designa;  
					model_article[4] = 0;
					model_article[5] = prix_uni+" fcfa";
					model_article[6] = (0*prix_uni)+" fcfa";
					model_article[7] = emplacement;
					
					this.gestionnaire.addLigne(model_article);
				} catch (NumberFormatException e) { 
					JOptionPane.showMessageDialog(null, ""+e.getMessage(),"Message",JOptionPane.YES_OPTION);
				}
				
			}
		}else if (ob == this.gestionnaire.resetArticle) {
			int response = JOptionPane.showConfirmDialog(null, "Voulez-vous annuler l'opération ?", "Message", JOptionPane.CANCEL_OPTION);
			if (response == 0) {
				this.reset_form_article();   
			}
		}else if(ob == this.gestionnaire.quitter){
			int response = JOptionPane.showConfirmDialog(null, "Voulez-vous quitter le programme ?", "Message", JOptionPane.CANCEL_OPTION);
			if (response == 0) {
				this.gestionnaire.dispose();
				Login login = null;
				try {
					login = new Login();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				login.setVisible(true);
			}
		}else if(ob == this.gestionnaire.bdelete) {
			//ListSelectionEvent e = this.gestionnaire.lisModel;
			if (this.gestionnaire.selectProd.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Sélectionner un article","Message",JOptionPane.YES_OPTION);
			}else {
				int taille = this.gestionnaire.selectProd.size();
				String code_prod = this.gestionnaire.selectProd.get(taille-1).toString();
				 
				int response = JOptionPane.showConfirmDialog(null, "Attention vous chercher à supprimer l'Article "+code_prod+" ? ", "Message", JOptionPane.CANCEL_OPTION);
				if (response == 0) {
					Article article = new Article();
					article.delete_article(code_prod);
					this.gestionnaire.modele.removeRow(this.gestionnaire.listRows.get(0));  
				}
			}  
		}else if(ob == this.gestionnaire.baffiche) {
			if (this.gestionnaire.selectProd.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Sélectionner un article","Message",JOptionPane.YES_OPTION);
			}else {
				int taille = this.gestionnaire.selectProd.size();
				String code_prod = this.gestionnaire.selectProd.get(taille-1).toString();
				
				ShowArticle showArticle = new ShowArticle(code_prod);
				showArticle.setVisible(true);
			}
		} else if(ob == this.gestionnaire.bmodif) { 
			if (this.gestionnaire.selectProd.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Sélectionner un article","Message",JOptionPane.YES_OPTION);
			}else {
				int taille = this.gestionnaire.selectProd.size();
				String code_prod = this.gestionnaire.selectProd.get(taille-1).toString();
				
				EditerArticle editerArticle = new EditerArticle(code_prod, this.gestionnaire.table_model, this.gestionnaire.keys);
				editerArticle.setVisible(true);  
			}	
		}else if(ob == this.gestionnaire.bEntreeStock) {
			EntreeQuantiteStock entreeQuantiteStock = new EntreeQuantiteStock();
			entreeQuantiteStock.setVisible(true);
		}
	}
	
	/*--- reset formuaire article ---*/
	public void reset_form_article() {
		this.gestionnaire.tcode_art.setText("");
		this.gestionnaire.tdesignation.setSelectedIndex(0);
		this.gestionnaire.tprix.setText("");
		this.gestionnaire.tquantite.setText("");
		this.gestionnaire.tdate.setText("");
		this.gestionnaire.tfournisseur.setSelectedIndex(0);
		this.gestionnaire.templacement.setText("");
	}
	
	/*--- reset formulaire fournisseur---*/
	public void reset_form_fournisseur() {
		this.gestionnaire.tnomFournisseur.setText("");
		this.gestionnaire.tville.setText("");
		this.gestionnaire.tpays.setText("");
		this.gestionnaire.taddresse.setText("");
		this.gestionnaire.ttel.setText("");
	}
	
}
