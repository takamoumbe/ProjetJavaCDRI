package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import listener.ListenerGestionnaire;
import listener.ListenerLogin;
import model.Article;
import model.Fournisseur;

public class EntreeStock extends JFrame{
	/*------size fenetre ----*/
	private static int HAUTEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(); 
	private static int LARGEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	
	private JMenu personnel;
	private JMenu edit;
	private JMenu help;
	private JMenu operations;
	
	public JMenuItem new_pers;
	public JMenuItem superviser;
	public JMenuItem acces_droits;
	public JMenuItem quitter;
	public JMenuItem export_stock;
	public JMenuItem apropos;
	
	private JLabel jtitre;
	public JTable jTable;
	
	public ListSelectionModel lisModel;
	
	/*--- transporter les information du JTable ----*/
	public Vector<Object> selectProd = new Vector<Object>();
	public Vector<Integer> listRows  = new Vector<Integer>();
	public Hashtable<Integer, TableModel> table_model = new Hashtable<Integer, TableModel>();
	public int keys = 0;
	/*--------- bounds -----*/
	private static int mgLeft = 560;
	private static int mgTop  = 10; 
	private static int width  = 700;
	private static int height = 40;
	
	/*---- inputs ----*/
	public JLabel jcode_art, jdesignation, junite, jquantite, jprix, jemplacement,jfournisseur, jnbre_paquet, jdate,
	jnomFournisseur, jaddresse, jville, jpays, jtel, jmessageSearch ;
	
	public JTextField tcode_art, tunite, tquantite, tprix, templacement, tnbre_paquet, tdate, tnomFournisseur, taddresse, tville, tpays, ttel, tsearch;
	public JComboBox tdesignation, tfournisseur; 
	public JButton resetArticle, saveArticle, resetFournisseur, saveFoursseur;
	public JButton bmodif, bdelete, baffiche, bimprime, bsearch;
	public JButton bEntreeStock, bOperation, bFournisseur, bCommandes;
	// barre de menu
	JMenuBar jMenuBar;
	
	// barre outils
	 JToolBar jToolBar;
	JButton bEntreer;
		
	private JPanel panel, infospanel, sousinfoPanel, sousquantitepanel, quantiterpanel, menuPanel, pertespanel;
	private JPanel listepanel,sous_panelModif, recherchePanel;
	/*--- panel bloc info---*/
	
	public DefaultTableModel modele;
	
	public EntreeStock() {
		
		// barre de menu		
		jMenuBar = new JMenuBar();
		this.barreMenu();
		
				
		/*----------- personnaliser le pannel----*/
		this.panel          = new JPanel();   
		this.infospanel     = new JPanel(); 
		this.quantiterpanel = new JPanel();
		this.menuPanel      = new JPanel();
		this.listepanel     = new JPanel();
		this.pertespanel    = new JPanel();
		this.sous_panelModif = new JPanel();
		this.sousinfoPanel  = new JPanel();
		this.sousquantitepanel  = new JPanel();
		this.recherchePanel = new JPanel();
		
		
		this.panel.setLayout(null);  
		this.infospanel.setLayout(null); 
		this.sousinfoPanel.setLayout(null);
		this.quantiterpanel.setLayout(null);
		this.sousquantitepanel.setLayout(null);
		this.menuPanel.setLayout(new GridLayout(4, 1));
		this.listepanel.setLayout(new BorderLayout());
		this.pertespanel.setLayout(new BorderLayout());
		this.recherchePanel.setLayout(new GridLayout(1, 2));
		this.sous_panelModif.setLayout(new GridLayout(1,3));
		
		/*------ titre de la page---*/
		this.jtitre = new JLabel("GESTIONNAIRE DE STOCK ");
		this.jtitre.setFont(new Font("Arial", Font.BOLD, 30));
		this.jtitre.setForeground(new Color(223,175,44));
		this.jtitre.setBounds(mgLeft,mgTop, width, height);
		this.panel.add(jtitre); 
		
		/*---- enregistrer un article --*/
		this.blocEnregistrerArticle();
		
		/*----- enregistrer les quantités---*/
		this.blocFournisseur();
		
		/*---- bloc menu buttons -----*/
		this.barreOutil(); 
		
		/*---- barre de menue ---*/
		this.menuPanel.setBounds(mgLeft-550+700+700,mgTop+35, width-600, height+715);
		Border eBorder2 = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		this.menuPanel.setBorder(eBorder2); 
		this.panel.add(menuPanel);
		
		/*---- liste des article ---*/
		this.blocArticle();
		
		/*---- pour les modifications ---*/
		this.bloc_modifier();
		
		
		
		/*----------------------- Joindre l'ecouteur -----------------*/
		this.saveFoursseur.addActionListener(new ListenerGestionnaire(this));
		this.resetFournisseur.addActionListener(new ListenerGestionnaire(this));
		this.quitter.addActionListener(new ListenerGestionnaire(this));
		this.bdelete.addActionListener(new ListenerGestionnaire(this));
		this.baffiche.addActionListener(new ListenerGestionnaire(this));
		
		this.saveArticle.addActionListener(new ListenerGestionnaire(this));
		this.resetArticle.addActionListener(new ListenerGestionnaire(this));
		
		this.bmodif.addActionListener(new ListenerGestionnaire(this));
		this.bEntreeStock.addActionListener(new ListenerGestionnaire(this));
		this.bimprime.addActionListener(new ListenerGestionnaire(this));
		
		/*----------- personnaliser la fenetre ------*/
		this.setTitle("MOUVEMENT ENTREE DE STOCK");  
		this.setLocation(new Point(500, 300));
		this.setJMenuBar(jMenuBar); 
		this.add(panel);
		this.setSize(LARGEUR, HAUTEUR); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(panel); 
	}
	
	
	/*--- bloc des modifications ---*/
	public void bloc_modifier() {
		
		Icon update = new ImageIcon("images/edit.png");
		Icon delete = new ImageIcon("images/delete.png");
		Icon affiche= new ImageIcon("images/voir.png");
		Icon imprime= new ImageIcon("images/imprimer.png");
		
		this.bmodif = new JButton("",update);
		this.bdelete= new JButton("",delete); 
		this.baffiche= new JButton("",affiche);
		this.bimprime= new JButton("",imprime); 
		
		this.sous_panelModif.add(this.bmodif);
		this.sous_panelModif.add(this.bdelete);
		this.sous_panelModif.add(this.baffiche);
		this.sous_panelModif.add(this.bimprime); 
		
		this.pertespanel.add(sous_panelModif,"North");
		this.pertespanel.setBounds(mgLeft-550+700+200,mgTop+35+255, width-600+400, height+700-240);
		Border eBorder2 = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		this.pertespanel.setBorder(eBorder2); 
		this.panel.add(pertespanel);  
	} 
	
	/*---- bloc liste des articles ----*/
	public void blocArticle() {
		  
		Border eBorder2 = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		this.listepanel.setBounds(mgLeft-550,mgTop+35+255, width+200, height+210+250);
		this.listepanel.setBorder(eBorder2);
		
		/*---- tableau ----*/
		Article article = new Article();
		
		Object[][] data = article.liste_article();
		
		// titre des colinnes
		String title[] = {"#", "Code", "Article", "Désignation", "Quantité", "Prix.U", "prix.T","Emplacement"};
		//this.jTable = new JTable(data, title);
		this.modele = new DefaultTableModel(title, 0);
		this.jTable =  new JTable(modele) {
	          public Component prepareRenderer(TableCellRenderer renderer, 
	          int row, int column) 
	          {
	             Component c = super.prepareRenderer(renderer, row, column);
	             Color color1 = new Color(220,220,220);
	             Color color2 = Color.WHITE;
	             if(!c.getBackground().equals(getSelectionBackground())) {
	                Color coleur = (row % 2 == 0 ? color1 : color2);
	                c.setBackground(coleur);
	                coleur = null;  
	             }
	             return c;
	          }
	       };
		int taille  = data.length;  
		int i = 0;
		while (i < taille) {
			this.addLigne(data[i]);
			i++;
		}
		
		// selection dans le JTable
		lisModel = jTable.getSelectionModel();
		lisModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionListener listSelectio = new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {  
				// TODO Auto-generated method stub
				int[] sel;
			    Object value;
			    if (!e.getValueIsAdjusting()) 
			    {
			      sel = jTable.getSelectedRows();
			      if (sel.length > 0) 
			      {
			          // récupérer les données de la table
			          TableModel tm = jTable.getModel();
			          int t = sel.length;
			          value = tm.getValueAt(sel[t-1],1);
			          selectProd.add(value);  
			          listRows.add(sel[t-1]);
			          table_model.put(sel[t-1], tm);
			          keys = sel[t-1];
			          System.out.print(value + " ");
			      }
			    }	
			}
		};
		lisModel.addListSelectionListener(listSelectio);
		
		TableRowSorter<TableModel> sort = new TableRowSorter<>(jTable.getModel());
		
        //définir la largeur de la 3éme colonne sur 200 pixels
        TableColumnModel columnModel = jTable.getColumnModel();
        columnModel.getColumn(2).setPreferredWidth(200);
    
        jTable.setRowSorter(sort);
        jTable.setEnabled(true);
		listepanel.add(new JScrollPane(jTable), "North");
		
		  
		// barre de recherche  
		  this.jmessageSearch = new JLabel("Zone de recherche par code Article");
		  this.jmessageSearch.setFont(new Font("Arial", Font.BOLD, 15));
		  //this.jmessageSearch.setBounds(5,5, 2, 5);
		  this.recherchePanel.add(jmessageSearch); 
		  
		  this.tsearch = new JTextField("Taper le code de l'article");
		  this.recherchePanel.add(tsearch);
		  
		  this.tsearch.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				String str = tsearch.getText();
                if (str.trim().length() == 0) {
                    sort.setRowFilter(null);
                } else {
                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + str)); 
                }
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
                String str = tsearch.getText();
                if (str.trim().length() == 0) {
                    sort.setRowFilter(null);
                } else {
                    //(?i) recherche insensible à la casse
                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
                }
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		  
		  
		  
		  this.listepanel.add(recherchePanel, "South");
		  this.panel.add(listepanel); 
		  
	}
	
	/*---- bloc enregistrer fournisseur ---*/
	public void blocFournisseur() {
		this.quantiterpanel.setBounds(mgLeft-550+700,mgTop+35, width, height+210);
		Border etBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		this.quantiterpanel.setBorder(etBorder); 
		
		this.jnomFournisseur = new JLabel("Nom Four: "); 
		this.jnomFournisseur.setBounds(mgLeft-530,mgTop+10, width, height);
		this.jnomFournisseur.setFont(new Font("time", Font.BOLD, 15));
		this.quantiterpanel.add(jnomFournisseur);  
		
		this.tnomFournisseur = new JTextField();
		this.tnomFournisseur.setBounds(mgLeft-530+110,mgTop+17, width-180, height-10);
		this.quantiterpanel.add(tnomFournisseur);
		
		this.sousquantitepanel.setBounds(mgLeft-530,mgTop+70, width-75+10, height+100);
		Border etBorder_ = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		this.sousquantitepanel.setBorder(etBorder_);    
		   
		
		this.jville = new JLabel("Ville: "); 
		this.jville.setBounds(mgLeft-530,mgTop-2, width, height);
		this.jville.setFont(new Font("time", Font.BOLD, 15));
		this.sousquantitepanel.add(jville);
		
		this.tville = new JTextField();
		this.tville.setBounds(mgLeft-530+80,mgTop-2, width-500, height-10);
		this.sousquantitepanel.add(tville);
		
		this.jpays = new JLabel("Pays: "); 
		this.jpays.setBounds(mgLeft-530+110+218,mgTop-2, width, height);
		this.jpays.setFont(new Font("time", Font.BOLD, 15));   
		this.sousquantitepanel.add(jpays);
		
		this.tpays = new JTextField();
		this.tpays.setBounds(mgLeft-530+110+288,mgTop-2, width-500, height-10); 
		this.sousquantitepanel.add(tpays);    
		
		this.jaddresse = new JLabel("Addresse: ");  
		this.jaddresse.setBounds(mgLeft-530,mgTop+50, width, height);
		this.jaddresse.setFont(new Font("time", Font.BOLD, 15));
		this.sousquantitepanel.add(jaddresse); 
		
		this.taddresse = new JTextField();
		this.taddresse.setBounds(mgLeft-530+80,mgTop+50, width-500, height-10);
		this.sousquantitepanel.add(taddresse); 
		
		this.jtel = new JLabel("Téléphone: ");  
		this.jtel.setBounds(mgLeft-530+290,mgTop+50, width, height);
		this.jtel.setFont(new Font("time", Font.BOLD, 15));
		this.sousquantitepanel.add(jtel);
		
		this.ttel = new JTextField(); 
		this.ttel.setBounds(mgLeft-530+398,mgTop+50, width-500, height-10);
		
		ttel.addKeyListener((KeyListener) new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		            e.consume();  // ignorer l'événement
		        }
		     }
		});
		this.sousquantitepanel.add(ttel);
		
		Icon annuler = new ImageIcon("images/annuler.png");
		this.resetFournisseur = new JButton("Annuler", annuler);
		this.resetFournisseur.setBounds(mgLeft-530+195,mgTop+212, width-550, height-15);
		this.quantiterpanel.add(resetFournisseur);
		
		Icon icon = new ImageIcon("images/save.png"); 
		this.saveFoursseur = new JButton("Enregistrer",icon);
		this.saveFoursseur.setBounds(mgLeft-530+200+155,mgTop+212, width-550, height-15);
		this.quantiterpanel.add(saveFoursseur);  
		
		this.infospanel.setBounds(mgLeft-550,mgTop+35, width, height+210);
		this.infospanel.setBorder(etBorder);    
		this.panel.add(infospanel);  
		
		this.quantiterpanel.add(this.sousquantitepanel);
		this.panel.add(quantiterpanel);   
	}
	
	/*----- barre de menue -----*/ 
	// fonction pour la barre de menu
		public void barreMenu() { 
			
			Icon img = new ImageIcon("images/item.png");
			Icon annuler = new ImageIcon("images/annuler.png"); 
			
			this.personnel  = new JMenu("Acceuil");
			//this.edit       = new JMenu("Mon Compte");
			//this.operations = new JMenu("Opérations");
			//this.help       = new JMenu("Aide");
			/*----------------------------------------------------------*/
			//this.new_pers   = new JMenuItem("Nouveau",img);
			//this.superviser = new JMenuItem("Superviser",img);
			//this.acces_droits  = new JMenuItem("Droits d'accès",img);
			this.quitter       = new JMenuItem("Quitter", annuler);
			//this.export_stock  = new JMenuItem("Exporter le stock",img);
			//this.apropos       = new JMenuItem("A Propos",img);
			
			/*this.personnel.add(this.new_pers);
			this.personnel.addSeparator();
			this.personnel.add(this.superviser);
			this.personnel.addSeparator();*/
			this.personnel.add(this.quitter);
			
			/*this.operations.add(this.acces_droits);
			this.operations.addSeparator();
			this.operations.add(this.export_stock);
			
			this.help.add(this.apropos);*/
			
			this.jMenuBar.add(this.personnel);
			/*this.jMenuBar.add(this.edit);
			this.jMenuBar.add(this.operations);
			this.jMenuBar.add(this.help);*/
			
		}
		
		/*--- bloc enregistrer article--*/
		public void blocEnregistrerArticle() {
			this.jcode_art = new JLabel("Code Artile: "); 
			this.jcode_art.setBounds(mgLeft-530,mgTop+10, width, height);
			this.jcode_art.setFont(new Font("time", Font.BOLD, 15));
			this.infospanel.add(jcode_art); 
			
			this.tcode_art = new JTextField();
			this.tcode_art.setBounds(mgLeft-530+110,mgTop+17, width-500, height-10);
			this.infospanel.add(tcode_art);   
			  
			this.jdate = new JLabel("Nom:"); 
			this.jdate.setBounds(mgLeft-530+110+270,mgTop+10, width, height);
			this.infospanel.add(jdate); 
			 
			// afficher la date 
			this.tdate = new JTextField();
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
			this.tdesignation = new JComboBox(items);
			this.tdesignation.setBounds(mgLeft-530+80,mgTop-2, width-500, height-10);
			this.sousinfoPanel.add(tdesignation);  
			
			this.jprix = new JLabel("Prix.U: "); 
			this.jprix.setBounds(mgLeft-530+110+218,mgTop-2, width, height);
			this.jprix.setFont(new Font("time", Font.BOLD, 15));   
			this.sousinfoPanel.add(jprix);
			
			this.tprix = new JTextField();
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
			
			this.tquantite = new JTextField();
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
			this.tfournisseur = new JComboBox(fournisseur.liste_fournisseur());
			this.tfournisseur.setBounds(mgLeft-530+398,mgTop+50, width-500, height-10);
			
	        //Rendre le comboBoxe modifiable 
	        //this.tfournisseur.setEditable(true);
	        
			this.sousinfoPanel.add(tfournisseur);
			
			this.jemplacement = new JLabel("Position: ");  
			this.jemplacement.setBounds(mgLeft-530,mgTop+50+48, width, height);
			this.jemplacement.setFont(new Font("time", Font.BOLD, 15));
			this.sousinfoPanel.add(jemplacement);  
			
			this.templacement = new JTextField(); 
			this.templacement.setBounds(mgLeft-530+80,mgTop+50+48, width-180, height-10);
			this.sousinfoPanel.add(templacement);
			
			Icon annuler = new ImageIcon("images/annuler.png");
			this.resetArticle = new JButton("Annuler",annuler);
			this.resetArticle.setBounds(mgLeft-530+195,mgTop+212, width-550, height-15);
			this.infospanel.add(resetArticle);
			  
			Icon icon = new ImageIcon("images/save.png");
			this.saveArticle = new JButton("Enregistrer", icon);
			this.saveArticle.setBounds(mgLeft-530+200+155,mgTop+212, width-550, height-15);
			this.infospanel.add(saveArticle);  
			
			this.infospanel.setBounds(mgLeft-550,mgTop+35, width, height+210);
			Border etBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
			this.infospanel.setBorder(etBorder);   
			this.panel.add(infospanel);    
			
			this.infospanel.add(sousinfoPanel);  
		}
		
		// fonction pour la barre d'outil
		public void barreOutil() {
			
			this.bEntreeStock = new JButton(" Entrée Stock ");
			this.bOperation   = new JButton(" Opérations ");
			this.bFournisseur = new JButton(" Fournisseurs ");
			this.bCommandes   = new JButton(" Commandes Fournisseur ");
			
			this.menuPanel.add(this.bEntreeStock); 
			this.menuPanel.add(this.bOperation); 
			this.menuPanel.add(this.bFournisseur); 
			this.menuPanel.add(this.bCommandes);  
			
		}
		
		public void addLigne(Object[] ob) {
			this.modele.addRow(
		            new Object[]{
	            		ob[0], 
	            		ob[1], 
	            		ob[2],
	            		ob[3],  
	            		ob[4], 
	            		ob[5], 
	            		ob[6], 
	            		ob[7], 
		            }
		       );
		}
		
}
