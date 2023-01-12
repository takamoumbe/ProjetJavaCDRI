package view;

import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableModel;

import listener.ListenerEditerArticle;
import model.Article;
import model.Fournisseur;

public class EditerArticle extends JFrame {
	
	private static int HAUTEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2; 
	private static int LARGEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
	
	/*--------- bounds -----*/
	private static int mgLeft = 560;
	private static int mgTop  = 10; 
	private static int width  = 700;
	private static int height = 40;
	
	private JPanel panel, infospanel, sousinfoPanel;
	
	/*---- inputs ----*/
	public JLabel jcode_art, jdesignation, junite, jquantite, jprix, jemplacement,jfournisseur, jnbre_paquet, jdate;
	
	public JTextField tcode_art, tunite, tquantite, tprix, templacement, tnbre_paquet, tdate;
	public JComboBox tdesignation, tfournisseur; 
	public JButton resetArticle, saveArticle;
	
	public Article article;
	public Hashtable<Integer, TableModel> table_model = new Hashtable<Integer, TableModel>();
	public int keys;
	
	public EditerArticle(String code, Hashtable<Integer, TableModel> table_model, int keys) {
		
		this.table_model = table_model;
		this.keys = keys;
		
		this.article = new Article();
		this.article = this.article.getOneArticle(code);
		
		this.panel          = new JPanel(); 
		this.infospanel     = new JPanel();
		this.sousinfoPanel  = new JPanel();
		
		this.panel.setLayout(null);  
		this.infospanel.setLayout(null); 
		this.sousinfoPanel.setLayout(null);
		
		/*---- enregistrer un article --*/
		this.blocEnregistrerArticle();
		
		/*----- joindre ecouteur ----*/
		this.resetArticle.addActionListener(new ListenerEditerArticle(this));
		this.saveArticle.addActionListener(new ListenerEditerArticle(this));
		
		/*----------- personnaliser la fenetre ------*/
		this.setTitle("MODIFIER UN ARTICLE");  
		this.setLocation(new Point(500, 300));
		this.add(panel);
		this.setSize(LARGEUR, HAUTEUR); 
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setContentPane(panel); 
		
	}
	
	/*--- bloc enregistrer article--*/
	public void blocEnregistrerArticle() {  
		this.jcode_art = new JLabel("Code Artile: "); 
		this.jcode_art.setBounds(mgLeft-530,mgTop+10, width, height);
		this.jcode_art.setFont(new Font("time", Font.BOLD, 15));
		this.infospanel.add(jcode_art); 
		
		this.tcode_art = new JTextField(article.getCodeArticle());
		this.tcode_art.setBounds(mgLeft-530+110,mgTop+17, width-500, height-10);
		this.tcode_art.disable();
		this.infospanel.add(tcode_art);   
		  
		this.jdate = new JLabel("Nom:"); 
		this.jdate.setBounds(mgLeft-530+110+270,mgTop+10, width, height);
		this.infospanel.add(jdate); 
		 
		// afficher la date 
		this.tdate = new JTextField(article.getNomArticle());
		this.tdate.setBounds(mgLeft-530+110+318,mgTop+17, width-500, height-10);
		this.infospanel.add(tdate);   
		
		this.sousinfoPanel.setBounds(mgLeft-530,mgTop+70, width-75+10, height+100);
		Border etBorder_ = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		this.sousinfoPanel.setBorder(etBorder_);    
		   
		
		this.jdesignation = new JLabel("Désignat: "); 
		this.jdesignation.setBounds(mgLeft-530,mgTop-2, width, height);
		this.jdesignation.setFont(new Font("time", Font.BOLD, 15));
		this.sousinfoPanel.add(jdesignation);
		
		String[] items = {"Designation", "Cartons", "Paquets", "Piéces"};
		String designation = article.getDesignationArticle();
		//rechercher la designation
		int indexe = 0;
		for (int f=0; f< items.length; f++) {
			if (items[f].equalsIgnoreCase(designation)) {
				indexe = f;
			}
		}
		
		this.tdesignation = new JComboBox(items);
		this.tdesignation.setBounds(mgLeft-530+80,mgTop-2, width-500, height-10);
		this.tdesignation.setSelectedIndex(indexe);
		this.sousinfoPanel.add(tdesignation);  
		
		this.jprix = new JLabel("Prix.U: "); 
		this.jprix.setBounds(mgLeft-530+110+218,mgTop-2, width, height);
		this.jprix.setFont(new Font("time", Font.BOLD, 15));   
		this.sousinfoPanel.add(jprix);
		
		this.tprix = new JTextField(article.getPrixArticle()+"");  
		this.tprix.setBounds(mgLeft-530+110+288,mgTop-2, width-500, height-10);
		this.tprix.addKeyListener((KeyListener) new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		            e.consume();  // ignorer l'événement
		        }
		     }
		});
		this.sousinfoPanel.add(tprix);   
		
		this.jquantite = new JLabel("Qté: ");  
		this.jquantite.setBounds(mgLeft-530,mgTop+50, width, height);
		this.jquantite.setFont(new Font("time", Font.BOLD, 15));
		this.sousinfoPanel.add(jquantite); 
		
		this.tquantite = new JTextField(article.getQuantiteArticle()+"");
		this.tquantite.setBounds(mgLeft-530+80,mgTop+50, width-500, height-10);
		this.tquantite.addKeyListener((KeyListener) new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		            e.consume();  // ignorer l'événement
		        }
		     }
		});
		this.sousinfoPanel.add(tquantite);
		
		this.jfournisseur = new JLabel("Fournisseur: ");  
		this.jfournisseur.setBounds(mgLeft-530+290,mgTop+50, width, height);
		this.jfournisseur.setFont(new Font("time", Font.BOLD, 15));
		this.sousinfoPanel.add(jfournisseur);
		
		Fournisseur fournisseur = new Fournisseur(); 
		// liste fournisseur
		String[] itemsFournisseur = fournisseur.liste_fournisseur();
		indexe = 0;
		for (int i = 0; i < itemsFournisseur.length; i++) {
			if (itemsFournisseur[i].equalsIgnoreCase(article.getFournisseur())) {
				indexe = i;
			}
		}
				
		this.tfournisseur = new JComboBox(fournisseur.liste_fournisseur());
		this.tfournisseur.setSelectedIndex(indexe);
		this.tfournisseur.setBounds(mgLeft-530+398,mgTop+50, width-500, height-10);
		
        //Rendre le comboBoxe modifiable 
        //this.tfournisseur.setEditable(true);
        
		this.sousinfoPanel.add(tfournisseur);
		
		this.jemplacement = new JLabel("Position: ");  
		this.jemplacement.setBounds(mgLeft-530,mgTop+50+48, width, height);
		this.jemplacement.setFont(new Font("time", Font.BOLD, 15));
		this.sousinfoPanel.add(jemplacement);  
		
		this.templacement = new JTextField(this.article.getEmplacement()); 
		this.templacement.setBounds(mgLeft-530+80,mgTop+50+48, width-180, height-10);
		this.sousinfoPanel.add(templacement);
		
		Icon annuler = new ImageIcon("images/annuler.png");
		this.resetArticle = new JButton("Annuler",annuler);
		this.resetArticle.setBounds(mgLeft-530+195-5,mgTop+212+50	, width-550, height-15);
		this.infospanel.add(resetArticle);
		
		Icon icon = new ImageIcon("images/save.png");
		this.saveArticle = new JButton("Enregistrer", icon);
		this.saveArticle.setBounds(mgLeft-530+200+155-5,mgTop+212+50, width-550, height-15);
		this.infospanel.add(saveArticle);  
		
		this.infospanel.setBounds(mgLeft-550+40,mgTop+35, width, height+210+70);
		Border etBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		this.infospanel.setBorder(etBorder);  
		
		this.infospanel.add(sousinfoPanel);
		
		this.panel.add(infospanel);     
	}
	
}
