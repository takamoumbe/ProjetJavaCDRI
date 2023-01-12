package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.util.Hashtable;

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
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import listener.ListenerQuantiteStock;
import listener.ListenerServices;
import model.Article;

public class Services extends JFrame{
	
	private JMenu personnel;
	
	public JMenuItem quitter;
	
	/*------size fenetre ----*/
	private static int HAUTEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(); 
	private static int LARGEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	
	/*----- label ----------*/
	public JLabel jtitre, jcode, jquantite, jprix, jdesignation, jfranc, jtotal;
	public JTextField tprix, tquantite, tclient, ttotal;
	public JComboBox<String> tcode;
	
	/*----- panel --------*/
	public JPanel panel, panel_article, panel_liste, panelButton, panel_operation;
	
	/*---- buttons ------*/
	public JButton baddRows, bdeleteRows, bvalider, breset;
	
	public JTable jTable, jtableListeOperation;
	
	/*--------- bounds -----*/
	private static int mgLeft = 250;
	private static int mgTop  = 10; 
	private static int width  = 700;
	private static int height = 40;
	
	public DefaultTableModel modele, table_modele;
	public ListSelectionModel lisModel;
	
	/*--- transporter les information du JTable ----*/
	public Hashtable<Integer, TableModel> table_model = new Hashtable<Integer, TableModel>();
	public int keys = 0;
	
	// barre de menu
	JMenuBar jMenuBar;
		
	public Services() {
		
		// barre de menu		
		jMenuBar = new JMenuBar();
		this.barreMenu();
				
		/*----------- personnaliser le pannel----*/
		this.panel          = new JPanel();
		this.panel_article  = new JPanel();
		this.panel_liste    = new JPanel();
		this.panel_operation    = new JPanel();
		
		this.panel.setLayout(null);
		this.panel_article.setLayout(null);
		this.panel_operation.setLayout(new GridLayout(1,1));
		this.panel_liste.setLayout(new BorderLayout());
		
		/*------ titre de la page---*/
		this.jtitre = new JLabel("COMMANDE CLIENT ");
		this.jtitre.setFont(new Font("Arial", Font.BOLD, 30));
		this.jtitre.setForeground(new Color(223,175,44));  
		this.jtitre.setBounds(mgLeft,mgTop, width, height);
		this.panel.add(jtitre); 
		
		this.jtitre = new JLabel("OPÉRATIONS DU  "+LocalDate.now().toString());
		this.jtitre.setFont(new Font("Arial", Font.BOLD, 30));
		this.jtitre.setForeground(new Color(223,175,44));  
		this.jtitre.setBounds(mgLeft+660,mgTop, width, height);
		this.panel.add(jtitre); 
		 
		this.bloc_article();
		this.bloc_liste();
		this.liste_commande_jour();
		
		/*----*/
		this.jtotal = new JLabel("Montant TTC:");
		this.jtotal.setFont(new Font("Arial", Font.BOLD, 15));
		this.jtotal.setBounds(mgLeft-200+200,mgTop+35+(height+150)+20, width-550+200, height-15);
		this.panel.add(jtotal);
		
		this.ttotal = new JTextField("0");
		this.ttotal.setFont(new Font("Arial", Font.BOLD, 15));
		this.ttotal.setBounds(mgLeft-200+383,mgTop+35+(height+150)+20, width-500, height-15);
		this.ttotal.disable();
		this.panel.add(ttotal);
		  
		this.jfranc = new JLabel("Fcfa");
		this.jfranc.setFont(new Font("Arial", Font.BOLD, 15));
		this.jfranc.setBounds(mgLeft-200+383+220,mgTop+35+(height+150)+20, width-550, height-15);
		this.panel.add(jfranc);
		
		
		// buttons
				this.breset = new JButton("Annuler");  
				this.breset.setBounds(mgLeft-200,mgTop+35+(height+150)+450+20, width-550, height-15);
				this.panel.add(breset);
				 
				this.bvalider = new JButton("Enregistrer");
				this.bvalider.setBounds(mgLeft-200+540,mgTop+35+(height+150)+450+20, width-550, height-15);
				this.panel.add(bvalider);
		/*----*/
		/*---- joindre l'ecouteur ----*/
		this.baddRows.addActionListener(new ListenerServices(this));
		this.bdeleteRows.addActionListener(new ListenerServices(this));
		this.bvalider.addActionListener(new ListenerServices(this));
		this.breset.addActionListener(new ListenerServices(this));
		this.quitter.addActionListener(new ListenerServices(this));
		
		/*----------- personnaliser la fenetre ------*/
		this.setTitle("COMPTE ACCEUIL");  
		this.setLocation(new Point(500, 400));  
		this.add(panel);
		this.setJMenuBar(jMenuBar); 
		this.setSize(LARGEUR, HAUTEUR); 
		//this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setContentPane(panel); 
		
	}
	
	/*---- bloc liste des commandes effectuee de la journee ----*/
	public void liste_commande_jour() {
		
		Border eBorder2 = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		this.panel_operation.setBounds(mgLeft-200+720 ,mgTop+35, width, height+700);
		this.panel_operation.setBorder(eBorder2);
		
		
		/*---- tableau ----*/
		Article article = new Article();
		Object[][] data = article.liste_facture();
		
		// titre des colinnes
		String title[] = {"#", "Num Fact", "Client", "Etat", "Quantité", "prix.T"};
		//this.jTable = new JTable(data, title);
		this.table_modele = new DefaultTableModel(title, 0);
		this.jtableListeOperation =  new JTable(table_modele) {
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
	       
	       jtableListeOperation.setEnabled(true);
	       panel_operation.add(new JScrollPane(jtableListeOperation));
	       
	       int taille  = data.length;
			int i = 0;
			while (i < taille) {
				this.addLigneFact(data[i]);
				i++;
			}
			
		this.panel.add(panel_operation); 
	}
	
	/*---- bloc article ----*/
	public void bloc_article() {
		
		Border eBorder2 = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		this.panel_article.setBounds(mgLeft-200 ,mgTop+35, width, height+150);
		this.panel_article.setBorder(eBorder2);
		
		this.jcode = new JLabel(" Code: ");
		this.jcode.setBounds(mgLeft-225,mgTop+10, width, height);
		this.jcode.setFont(new Font("time", Font.BOLD, 15));
		this.panel_article.add(jcode); 
		
		Article article = new Article();
		this.tcode = new JComboBox(article.liste_code_article());
		this.tcode.setBounds(mgLeft-225+80,mgTop+10, width-500, height-10);
		this.panel_article.add(tcode); 
		
		this.jquantite = new JLabel(" QTy: ");
		this.jquantite.setBounds(mgLeft-225+80+80+80+60,mgTop+10, width-500, height-10);
		this.jquantite.setFont(new Font("time", Font.BOLD, 15));
		this.panel_article.add(jquantite);
		
		this.tquantite = new JTextField();
		this.tquantite.setBounds(mgLeft-225+80+80+80+60+60,mgTop+10, width-500, height-10);
		this.tquantite.setFont(new Font("time", Font.BOLD, 15));
		this.tquantite.addKeyListener((KeyListener) new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		            e.consume();  // ignorer l'événement
		        }
		     }
		});
		this.panel_article.add(tquantite); 
		
		this.jdesignation = new JLabel("/ Désignation");
		this.jdesignation.setBounds(mgLeft-225+80+80+80+60+60+200,mgTop+10, width-500, height-10);
		this.jdesignation.setFont(new Font("time", Font.BOLD, 15));
		this.panel_article.add(jdesignation);
		
		this.jprix = new JLabel(" Prix.U: ");
		this.jprix.setBounds(mgLeft-225,mgTop+10+60, width, height);
		this.jprix.setFont(new Font("time", Font.BOLD, 15));
		this.panel_article.add(jprix);
		
		this.jfranc = new JLabel(" Client ");
		this.jfranc.setBounds(mgLeft-225+300,mgTop+10+60, width, height);
		this.jfranc.setFont(new Font("time", Font.BOLD, 15));
		this.panel_article.add(jfranc);
		
		this.tprix = new JTextField();
		this.tprix.setBounds(mgLeft-225+80,mgTop+10+60, width-500, height-10);
		this.tprix.setFont(new Font("time", Font.BOLD, 15));
		this.tprix.addKeyListener((KeyListener) new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {  
		        char c = e.getKeyChar();
		        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		            e.consume();  // ignorer l'événement
		        }
		     }
		});
		this.panel_article.add(tprix); 
		
		this.tclient = new JTextField();
		this.tclient.setBounds(mgLeft-225+80+80+80+60+60,mgTop+10+60, width-500, height-10);
		this.tclient.setFont(new Font("time", Font.BOLD, 15));
		this.panel_article.add(tclient); 
		
		// buttons
		this.baddRows = new JButton("Ajouter ligne");  
		this.baddRows.setBounds(mgLeft-225+500,mgTop+10+60+60, width-550, height-15);
		this.panel_article.add(baddRows);
		
		this.bdeleteRows = new JButton("Supprimer ligne");
		this.bdeleteRows.setBounds(mgLeft-225,mgTop+10+60+60, width-550, height-15);
		this.panel_article.add(bdeleteRows);
		
		//this.listepanel.add(recherchePanel, "South");
		this.panel.add(panel_article); 
	}
	
	/*---- bloc tableau ----*/
	public void bloc_liste() { 
		
		Border eBorder2 = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		this.panel_liste.setBounds(mgLeft-200,mgTop+35+(height+150)+30+30, width, height+150+200);
		this.panel_liste.setBorder(eBorder2);
		
		/*---- tableau ----*/
		Article article = new Article();
		
		
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
				          table_model.put(sel[t-1], tm);
				          keys = sel[t-1];
				          System.out.print(value + " ");
				      }
				    }	
				}
			};
			lisModel.addListSelectionListener(listSelectio);
		
	       
	       jTable.setEnabled(true);
	       panel_liste.add(new JScrollPane(jTable), "North");
			
		//this.listepanel.add(recherchePanel, "South");
		this.panel.add(panel_liste);
		
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
	
	public void addLigneFact(Object[] ob) {
		this.table_modele.addRow(
	            new Object[]{
            		ob[0], 
            		ob[1], 
            		ob[2],
            		ob[3], 
            		ob[4], 
            		ob[5], 
	            }
	       );
	}
	
	
	/*----- barre de menue -----*/ 
	// fonction pour la barre de menu
		public void barreMenu() { 
			
			Icon annuler = new ImageIcon("images/annuler.png"); 
			
			this.personnel  = new JMenu("Acceuil");
			/*----------------------------------------------------------*/
			this.quitter       = new JMenuItem("Quitter", annuler);
			
			this.personnel.add(this.quitter);
			
			
			this.jMenuBar.add(this.personnel);
			
		}
	
}
