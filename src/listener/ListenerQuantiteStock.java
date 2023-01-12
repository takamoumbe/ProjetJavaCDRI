package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Article;
import view.EntreeQuantiteStock;

public class ListenerQuantiteStock implements ActionListener{
	
	private EntreeQuantiteStock entreeQuantiteStock;
	
	public ListenerQuantiteStock(EntreeQuantiteStock entreeQuantiteStock) {
		this.entreeQuantiteStock = entreeQuantiteStock;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		Object ob = ev.getSource();
		if (ob == this.entreeQuantiteStock.breset) {
			int response = JOptionPane.showConfirmDialog(null, "Voulez-vous annuler l'opération ?", "Message", JOptionPane.CANCEL_OPTION);
			if (response == 0) {
				this.entreeQuantiteStock.dispose();
			}
		}else if(ob == this.entreeQuantiteStock.bvalider) {
			 // enregistrer la validation d'entree de stock
			if (this.entreeQuantiteStock.modele.getRowCount() < 1) {
				JOptionPane.showMessageDialog(null, "Ajouter un auticle à la liste","Message",JOptionPane.YES_OPTION);
			}else {
				Article article = new Article();
				article.entrer_stock(this.entreeQuantiteStock.modele);
			}
			
			
			 
		}else if(ob == this.entreeQuantiteStock.baddRows) {
			String code     = this.entreeQuantiteStock.tcode.getSelectedItem().toString().trim();
			String quantite = this.entreeQuantiteStock.tquantite.getText().toString().trim();
			String prix     = this.entreeQuantiteStock.tprix.getText().toString().trim();
			
			if (code.equals("Code Article") || quantite.equals("") || prix.equals("")) {
				JOptionPane.showMessageDialog(null, "Remplir tous les champs","Message",JOptionPane.YES_OPTION);
			}else {
				// compatibilisté des types des champs
				int quantite_ = 0;
				int prix_uni_ = 0;
				
				try {
					quantite_ = Integer.parseInt(quantite);
					prix_uni_ = Integer.parseInt(prix);
					if (quantite_<=0 || (prix_uni_<=0)) {
						JOptionPane.showMessageDialog(null, "Quantité et prix Incorrectes","Message",JOptionPane.YES_OPTION);
					}else {
						//fabriquer un article avec les nouvels modifs
						Article article = new Article();
						article = article.getOneArticle(code);
						// modification du prix et de la quantité
						article.setPrixArticle(prix_uni_);
						article.setQuantiteArticle(quantite_);
						// fabriquer l'objet et ajouter du nouvel article dans le tableau
						Object[] objects = new Object[8];
						int row = this.entreeQuantiteStock.modele.getRowCount(); 
						
						objects[0] = row+1;
						objects[1] = article.getCodeArticle();
						objects[2] = article.getNomArticle();
						objects[3] = article.getDesignationArticle();
						objects[4] = article.getQuantiteArticle()+" | "+((float)article.getQuantiteArticle()/(float)article.getNbrePaquet());  
						objects[5] = article.getPrixArticle()+" fcfa";
						objects[6] = (article.getPrixArticle()*article.getQuantiteArticle())+" fcfa";
						objects[7] = article.getEmplacement();
						
						this.entreeQuantiteStock.addLigne(objects);
						this.reset_form_article();
					}
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, ""+e.getMessage(),"Message",JOptionPane.YES_OPTION);
				}
			}
		}else if(ob == this.entreeQuantiteStock.bdeleteRows) {
			if (this.entreeQuantiteStock.table_model.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Sélectionner un article","Message",JOptionPane.YES_OPTION);
			}else {
				int response = JOptionPane.showConfirmDialog(null, "Attention vous chercher à supprimer cet enregistrement ? ", "Message", JOptionPane.CANCEL_OPTION);
				if (response == 0) {
					this.entreeQuantiteStock.modele.removeRow(this.entreeQuantiteStock.keys);    
				}
			}
		}
	}
	
	/*--- reset formuaire article ---*/
	public void reset_form_article() {
		this.entreeQuantiteStock.tprix.setText("");
		this.entreeQuantiteStock.tquantite.setText("");
		this.entreeQuantiteStock.tcode.setSelectedIndex(0);  
	}

}
