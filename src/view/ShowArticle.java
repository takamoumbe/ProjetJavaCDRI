package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import listener.ListenerGestionnaire;
import listener.ListenerShowArticle;
import model.Article;

public class ShowArticle extends JFrame{
	
	/*------size fenetre ----*/
	private static int HAUTEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2; 
	private static int LARGEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4;
	
	public JLabel lNomFour, lcode, lnom, ldesign, lquant, lprix, lemplacement, lnbrePaquet;
	public JLabel tNomFour, tcode, tnom, tdesign, tquant, tprix, templacement, tnbrePaquet;
	public JPanel panel, pfournisseur, particle;
	
	public JButton button;
	
	/*--------- bounds -----*/
	private static int mgLeft = 20;
	private static int mgTop  = 10; 
	private static int width  = 180;
	private static int height = 40;
	
	public ShowArticle(String code) {
		
		// recuperer l article selectionner 
		Article article = new Article();
		article = article.getOneArticle(code);
		System.out.println(article);
		
		this.pfournisseur = new JPanel();
		this.particle = new JPanel();
		this.panel = new JPanel();
		
		this.panel.setLayout(new BorderLayout());
		this.particle.setLayout(null);
		this.pfournisseur.setLayout(null);
		
		this.lNomFour = new JLabel("Fournisseur: ");
		this.lNomFour.setFont(new Font("Arial", Font.BOLD, 17));
		this.lNomFour.setForeground(new Color(223,175,44));
		this.lNomFour.setBounds(mgLeft,mgTop, width, height);
		this.pfournisseur.add(lNomFour);
		this.tNomFour = new JLabel(article.getFournisseur()+"");
		this.tNomFour.setFont(new Font("Arial", Font.BOLD, 17));
		this.tNomFour.setForeground(new Color(223,175,44));  
		this.tNomFour.setBounds(mgLeft+width,mgTop, width+100, height);
		this.pfournisseur.add(tNomFour);  
		Border eBorder2 = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		this.pfournisseur.setBorder(eBorder2);
		
		this.lcode = new JLabel("Code Article: ");  
		this.lcode.setFont(new Font("Arial", Font.BOLD, 17));
		this.lcode.setBounds(mgLeft,mgTop+40, width, height);
		this.lcode.setForeground(new Color(223,175,44));
		this.pfournisseur.add(lcode);
		
		this.tcode = new JLabel(article.getCodeArticle());
		this.tcode.setFont(new Font("Arial", Font.BOLD, 17));
		this.tcode.setForeground(new Color(223,175,44));
		this.tcode.setBounds(mgLeft+width,mgTop+40, width, height);
		this.pfournisseur.add(tcode);
		
		this.lnom = new JLabel("Nom Article: ");  
		this.lnom.setFont(new Font("Arial", Font.BOLD, 17));
		this.lnom.setBounds(mgLeft,mgTop+40+40, width, height);
		this.lnom.setForeground(new Color(223,175,44));
		this.pfournisseur.add(lnom);
		
		this.tnom = new JLabel(article.getNomArticle());
		this.tnom.setFont(new Font("Arial", Font.BOLD, 17));
		this.tnom.setForeground(new Color(223,175,44));
		this.tnom.setBounds(mgLeft+width,mgTop+40+40, width, height);
		this.pfournisseur.add(tnom);
		
		this.ldesign = new JLabel("Désignation: ");  
		this.ldesign.setFont(new Font("Arial", Font.BOLD, 17));
		this.ldesign.setBounds(mgLeft,mgTop+40+40+40, width, height);
		this.ldesign.setForeground(new Color(223,175,44));
		this.pfournisseur.add(ldesign);
		
		this.tdesign = new JLabel(article.getDesignationArticle());
		this.tdesign.setFont(new Font("Arial", Font.BOLD, 17));
		this.tdesign.setForeground(new Color(223,175,44));  
		this.tdesign.setBounds(mgLeft+width,mgTop+40+40+40, width, height);
		this.pfournisseur.add(tdesign);
		
		this.lquant = new JLabel("Quantité: ");  
		this.lquant.setFont(new Font("Arial", Font.BOLD, 17));
		this.lquant.setBounds(mgLeft,mgTop+40+40+40+40, width, height);
		this.lquant.setForeground(new Color(223,175,44));
		this.pfournisseur.add(lquant);
		
		this.tquant = new JLabel(article.getQuantiteArticle()+"");
		this.tquant.setFont(new Font("Arial", Font.BOLD, 17));
		this.tquant.setForeground(new Color(223,175,44));  
		this.tquant.setBounds(mgLeft+width,mgTop+40+40+40+40, width, height);
		this.pfournisseur.add(tquant);
		
		this.lprix = new JLabel("Prix.U: ");  
		this.lprix.setFont(new Font("Arial", Font.BOLD, 17));
		this.lprix.setBounds(mgLeft,mgTop+40+40+40+40+40, width, height);
		this.lprix.setForeground(new Color(223,175,44));
		this.pfournisseur.add(lprix);
		
		this.tprix = new JLabel(article.getPrixArticle()+" fcfa");
		this.tprix.setFont(new Font("Arial", Font.BOLD, 17));
		this.tprix.setForeground(new Color(223,175,44));  
		this.tprix.setBounds(mgLeft+width,mgTop+40+40+40+40+40, width, height);
		this.pfournisseur.add(tprix);
		
		this.lemplacement = new JLabel("Emplacement: ");  
		this.lemplacement.setFont(new Font("Arial", Font.BOLD, 17));
		this.lemplacement.setBounds(mgLeft,mgTop+40+40+40+40+40+40, width, height);
		this.lemplacement.setForeground(new Color(223,175,44));
		this.pfournisseur.add(lemplacement);
		
		this.templacement = new JLabel(article.getEmplacement()+"");
		this.templacement.setFont(new Font("Arial", Font.BOLD, 17));
		this.templacement.setForeground(new Color(223,175,44));  
		this.templacement.setBounds(mgLeft+width,mgTop+40+40+40+40+40+40, width, height);
		this.pfournisseur.add(templacement);
		
		this.lnbrePaquet = new JLabel("Article par conditio.: ");  
		this.lnbrePaquet.setFont(new Font("Arial", Font.BOLD, 17));
		this.lnbrePaquet.setBounds(mgLeft,mgTop+40+40+40+40+40+40+40, width, height);
		this.lnbrePaquet.setForeground(new Color(223,175,44));
		this.pfournisseur.add(lnbrePaquet);
		
		this.tnbrePaquet = new JLabel(article.getNbrePaquet()+"");
		this.tnbrePaquet.setFont(new Font("Arial", Font.BOLD, 17));
		this.tnbrePaquet.setForeground(new Color(223,175,44));  
		this.tnbrePaquet.setBounds(mgLeft+width+10,mgTop+40+40+40+40+40+40+40, width, height);
		this.pfournisseur.add(tnbrePaquet);
		
		this.button = new JButton("Fermer");
		this.button.setBounds(mgLeft+width-100,mgTop+40+40+40+40+40+40+40+70, width, height);
		this.pfournisseur.add(button);
		/*----- joindre ecouteur -----*/
		this.button.addActionListener(new ListenerShowArticle(this));
		
		/*----------- personnaliser la fenetre ------*/
		this.setTitle("INFORMATION SUR ARTICLE");  
		this.setLocation(new Point(500, 300));
		this.panel.add(pfournisseur, "Center"); 
		this.add(panel);
		this.setSize(LARGEUR, HAUTEUR); 
		//this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setContentPane(panel);
		
	}
}
