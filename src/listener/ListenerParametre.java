package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.User;
import view.Parametre;

public class ListenerParametre implements ActionListener{
	
	private Parametre parametre;
	
	public ListenerParametre(Parametre parametre) {
		this.parametre = parametre;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		Object ob = ev.getSource();
		
		if(ob == this.parametre.bvalider) {
			String login_clavier    = this.parametre.tlogin.getText().trim();
			String ancienPass       = this.parametre.lastpass.getText().trim();     
			String password         = this.parametre.tpassword.getText().trim();  
			
			if (login_clavier.equals("") || password.equals("") || ancienPass.equals("")) { 
				// afficher le message sur la page
				JOptionPane.showMessageDialog(null, "Svp Remplisser tous les champs", "Message", JOptionPane.OK_OPTION);
			}else {
				User user = new User();
				user = user.select_user(login_clavier, ancienPass);
				if (user == null) {
					
					JOptionPane.showMessageDialog(null, "Mot de passe Incorrect", "Message", JOptionPane.OK_OPTION);
					
				}else {
					
					user.setPassword(password);
					
					boolean result = user.update_user(user);
					if (result == false) {
						JOptionPane.showMessageDialog(null, "L'opération a échouer", "Message", JOptionPane.OK_OPTION);
					}else {
						JOptionPane.showMessageDialog(null, "L'opération a reussir", "Message", JOptionPane.INFORMATION_MESSAGE);
						parametre.dispose();
					}
				}
			}
		}
		
	}
	
	

}
