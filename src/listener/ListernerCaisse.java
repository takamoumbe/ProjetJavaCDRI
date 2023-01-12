package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import model.Article;
import view.Caisse;
import view.Login;

public class ListernerCaisse implements ActionListener{
	
	private Caisse caisse;
	private int keys;
	
	public ListernerCaisse(Caisse caisse) {
		this.caisse = caisse;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		Object ob = ev.getSource();
		
		if (ob == this.caisse.quitter) {
			int response = JOptionPane.showConfirmDialog(null, "Voulez-vous quitter le programme ?", "Message", JOptionPane.CANCEL_OPTION);
			if (response == 0) {
				this.caisse.dispose();
				Login login = null;
				try {
					login = new Login();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				login.setVisible(true);
			}
		}else if (ob == this.caisse.bannuler) {
			this.reset_form_article();
		}else if(ob == this.caisse.bvalider){
			String code     = this.caisse.tcode.getText().toString().trim();
			
			if (code.equals("")) {
				JOptionPane.showMessageDialog(null, "Sélectionner une facture","Message",JOptionPane.YES_OPTION);
			}else {
				Article article = new Article();
				if (article.encaisser_fact(code)) {
					// enlever des impayes 
					Object[][] liste = new Object[1][6];
					int taille = this.caisse.table_modele.getRowCount();
					String quantite = this.caisse.modele.getValueAt(this.caisse.keys, 4).toString().trim();
					String prix     = this.caisse.modele.getValueAt(this.caisse.keys, 5).toString().trim();
					
					String[] qu = quantite.split("|");
					String[] pr = prix.split("f");
					
					
					liste[0][0] = taille+1;
					liste[0][1] = this.caisse.modele.getValueAt(this.caisse.keys, 1);
					liste[0][2] = this.caisse.modele.getValueAt(this.caisse.keys, 2);
					liste[0][3] = "Payé";
					liste[0][4] = this.caisse.modele.getValueAt(this.caisse.keys, 4);
					liste[0][5] = this.caisse.modele.getValueAt(this.caisse.keys, 5);
					
					this.caisse.modele.removeRow(this.caisse.keys); 
					// ajouter sur les payes
					this.caisse.addLigneFact(liste[0]);
					// modifier le montant 
					String quatite_ = this.caisse.tqtyTotal.getText().toString().trim();
					String prix_    = this.caisse.jPrixTotal.getText().toString().trim();
					// convertir en entier
					try {
						
						
					} catch (Exception e) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, ""+e.getMessage(),"Message",JOptionPane.YES_OPTION);
					}
					
					JOptionPane.showMessageDialog(null, "Facture encaissé","Message",JOptionPane.INFORMATION_MESSAGE);
					
				}else {
					JOptionPane.showMessageDialog(null, "Echec de l'encaissement de la facture","Message",JOptionPane.YES_OPTION);
				}
			}
		}
	}
	
	/*--- reset formuaire article ---*/
	public void reset_form_article() {
		this.caisse.tprix.setText("");
		this.caisse.tquantite.setText("");
		this.caisse.tcode.setText("");
		this.caisse.tclient.setText("");
	}

}
