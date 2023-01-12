package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import model.Article;
import view.EditerArticle;

public class ListenerEditerArticle implements ActionListener{
	
	private EditerArticle editerArticle;
	
	public ListenerEditerArticle(EditerArticle editerArticle) {
		this.editerArticle = editerArticle;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		Object ob = ev.getSource();
		if (ob == this.editerArticle.resetArticle) {
			int response = JOptionPane.showConfirmDialog(null, "Voulez-vous annuler l'opération ?", "Message", JOptionPane.CANCEL_OPTION);
			if (response == 0) {
				this.reset_form_article();
				this.editerArticle.dispose();
			}
		}else if( ob == this.editerArticle.saveArticle) {
			String codeArt 		= this.editerArticle.tcode_art.getText().trim();
			String designa   	= this.editerArticle.tdesignation.getSelectedItem().toString().trim();
			String prix    		= this.editerArticle.tprix.getText().trim();
			String qty			= this.editerArticle.tquantite.getText().trim();
			String fournisseur  = this.editerArticle.tfournisseur.getSelectedItem().toString().trim();
			String emplacement  = this.editerArticle.templacement.getText().trim();
			String nomArticle   = this.editerArticle.tdate.getText().trim();
			
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
					
					if (this.editerArticle.table_model.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Echec de l'opération","Message",JOptionPane.YES_OPTION);
					}else {
						int cle = this.editerArticle.keys;
						TableModel tm = this.editerArticle.table_model.get(cle);
						// modification de l articicle
						if (article.update_article(article)) {
							// update jTable la modification effectuer
							tm.setValueAt(article.getCodeArticle(), cle, 1);
							tm.setValueAt(article.getNomArticle(), cle, 2);
							tm.setValueAt(article.getDesignationArticle(), cle, 3);
							tm.setValueAt(article.getQuantiteArticle(), cle, 4);  
							tm.setValueAt(article.getPrixArticle()+" fcfa", cle, 5);
							tm.setValueAt((article.getQuantiteArticle()*article.getPrixArticle())+" fcfa", cle, 6);
							tm.setValueAt(article.getEmplacement(), cle, 7);
							
							this.reset_form_article();
							this.editerArticle.dispose();
							  
							JOptionPane.showMessageDialog(null, "Modification réussir", "Message",JOptionPane.INFORMATION_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(null, "Erreur lors de l'opération", "Message",JOptionPane.OK_OPTION);
						}
					}
				} catch (NumberFormatException e) { 
					JOptionPane.showMessageDialog(null, ""+e.getMessage(),"Message",JOptionPane.YES_OPTION);
				}
			}
		}
	}

	/*--- reset formuaire article ---*/
	public void reset_form_article() {
		this.editerArticle.tcode_art.setText("");
		this.editerArticle.tdesignation.setSelectedIndex(0);
		this.editerArticle.tprix.setText("");
		this.editerArticle.tquantite.setText("");
		this.editerArticle.tdate.setText("");
		this.editerArticle.tfournisseur.setSelectedIndex(0);
		this.editerArticle.templacement.setText("");
	}
	
	
}
