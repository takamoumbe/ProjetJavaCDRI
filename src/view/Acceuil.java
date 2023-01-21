package view;

import java.io.IOException;

import javax.swing.JOptionPane;

import db.DataBase;
import model.PdfItext;

public class Acceuil {
	
	
	public static void main(String[] args) throws IOException{
		
		DataBase db = new DataBase();
		db.connect();
		db.close();
		  
		/*Login login = new Login();
		login.setVisible(true);*/
		
		/*Services service = new Services();
		service.setVisible(true);*/
		
		/*Caisse caisse = new Caisse();
		caisse.setVisible(true);*/
		
		/*EntreeStock entreeStock = new EntreeStock();
		entreeStock.setVisible(true);*/
		
		/*Parametre parametre = new Parametre();
		
		parametre.setVisible(true);*/
		
		PdfItext pdfItext = new PdfItext();
		
		String file = "liste_article.pdf";
		try
		{
			pdfItext.listeArticlePDF(file);  
		}
		catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, " "+e.getMessage(),"Message",JOptionPane.YES_OPTION);
		}
		
		
	}
	
}
