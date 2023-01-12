package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import model.Article;
import view.Login;
import view.Services;

public class ListenerServices implements ActionListener{

	Services services;
	
	public ListenerServices(Services services) {
		this.services = services;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		Object ob = ev.getSource();
		
		if (ob == this.services.quitter) {
			int response = JOptionPane.showConfirmDialog(null, "Voulez-vous quitter le programme ?", "Message", JOptionPane.CANCEL_OPTION);
			if (response == 0) {
				this.services.dispose();
				Login login = null;
				try {
					login = new Login();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				login.setVisible(true);
			}
		}else if (ob == this.services.breset) {
			int response = JOptionPane.showConfirmDialog(null, "Voulez-vous annuler l'opération ?", "Message", JOptionPane.CANCEL_OPTION);
			if (response == 0) {
				// vider les inputs et le tableau
				this.reset_form_article();  
				this.services.tclient.setText("");
				this.services.ttotal.setText("0");
				// vider le tableau
				 int row2 = this.services.modele.getRowCount();
				 int j= row2-1;
				 while (j>=0) {
					 this.services.modele.removeRow(j);
					 j--;
				}
			}
		}else if(ob == this.services.baddRows) {
			
			String code     = this.services.tcode.getSelectedItem().toString().trim();
			String quantite = this.services.tquantite.getText().toString().trim();
			String prix     = this.services.tprix.getText().toString().trim();
			String nom_client = this.services.tclient.getText().toString().trim();
			String total = this.services.ttotal.getText().toString().trim();
			
			if (code.equals("Code Article") || quantite.equals("") || prix.equals("") || nom_client.equals("")) {
				JOptionPane.showMessageDialog(null, "Remplir tous les champs","Message",JOptionPane.YES_OPTION);
			}else {
				// compatibilisté des types des champs
				int quantite_ = 0;
				int prix_uni_ = 0;
				int total_    = 0;
				
				try {
					quantite_ = Integer.parseInt(quantite);
					prix_uni_ = Integer.parseInt(prix);
					total_    = Integer.parseInt(total);
							
					if (quantite_<=0 || (prix_uni_<=0)) {
						JOptionPane.showMessageDialog(null, "Quantité et prix Incorrectes","Message",JOptionPane.YES_OPTION);
					}else {
						//fabriquer un article avec les nouvels modifs
						Article article = new Article();
						article = article.getOneArticle(code);
						if (quantite_>article.getQuantiteArticle()) {
							JOptionPane.showMessageDialog(null, "Stock disponible "+article.getQuantiteArticle(),"Message",JOptionPane.YES_OPTION);
						}else {
							// modification du prix et de la quantité
							article.setPrixArticle(prix_uni_);
							article.setQuantiteArticle(quantite_);
							// fabriquer l'objet et ajouter du nouvel article dans le tableau
							Object[] objects = new Object[8];
							int row = this.services.modele.getRowCount(); 
							
							objects[0] = row+1;   
							objects[1] = article.getCodeArticle();  
							objects[2] = article.getNomArticle();
							objects[3] = article.getDesignationArticle();
							objects[4] = article.getQuantiteArticle()+" | "+((float)article.getQuantiteArticle()/(float)article.getNbrePaquet());  
							objects[5] = article.getPrixArticle()+" fcfa";
							objects[6] = (article.getPrixArticle()*article.getQuantiteArticle())+" fcfa";
							objects[7] = article.getEmplacement();
							
							this.services.addLigne(objects);
							this.reset_form_article();
							// actualiser le total
							int total_mul = quantite_*prix_uni_;
							this.services.ttotal.setText(""+(total_+total_mul));
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, ""+e.getMessage(),"Message",JOptionPane.YES_OPTION);
				}
			}
			
		}else if(ob == this.services.bdeleteRows) {
			if (this.services.table_model.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Sélectionner un article","Message",JOptionPane.YES_OPTION);
			}else {
				int response = JOptionPane.showConfirmDialog(null, "Attention vous chercher à supprimer cet enregistrement ? ", "Message", JOptionPane.CANCEL_OPTION);
				if (response == 0) {
					this.services.modele.removeRow(this.services.keys);  
				}
			}
		}else if(ob == this.services.bvalider) {
			String nom_client = this.services.tclient.getText().toString().trim();
			 // enregistrer la validation de la commande client
			if ((this.services.modele.getRowCount() < 1) || nom_client.equals("")) {
				JOptionPane.showMessageDialog(null, "Ajouter un article à la liste","Message",JOptionPane.YES_OPTION);
			}else {
				Article article = new Article();
				if (article.commander_article(this.services.modele, nom_client)) {
					
					 JOptionPane.showMessageDialog(null, "Commande Effectuée", "Message",JOptionPane.INFORMATION_MESSAGE);
					 this.services.ttotal.setText("0");

					 // ajouter la facture dans la liste des operations
					 Object[][] liste_fact = article.liste_facture();
					 int t = this.services.table_modele.getRowCount();
					 liste_fact[0][0] = t+1;
					 this.services.addLigneFact(liste_fact[0]);
				} else {
					JOptionPane.showMessageDialog(null, "Echec de l'opération", "Message",JOptionPane.OK_OPTION);  
				}
			}
		}
	}
	
	
	/*--- reset formuaire article ---*/
	public void reset_form_article() {
		this.services.tprix.setText("");
		this.services.tquantite.setText("");
		this.services.tcode.setSelectedIndex(0);
	}

	
}
