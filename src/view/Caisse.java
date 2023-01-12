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

import listener.ListenerServices;
import listener.ListernerCaisse;
import model.Article;

public class Caisse extends JFrame{
	
private JMenu personnel;
	
	public JMenuItem quitter;
	
	/*------size fenetre ----*/
	private static int HAUTEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(); 
	private static int LARGEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	
	/*----- label ----------*/
	public JLabel jtitre, jcode, jquantite, jprix, jdesignation, jfranc, jqtyTotal, jPrixTotal, jsearch;
	public JTextField tprix, tquantite, tclient, tqtyTotal, tPrixTotal, tcode, tsearch;
	
	/*----- panel --------*/
	public JPanel panel, panel_article, panel_liste, panelButton, panel_operation;
	
	/*---- buttons ------*/
	public JButton bannuler, bvalider;
	
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
	Article article;
	
	public Caisse() {
		
		// barre de menu		
		jMenuBar = new JMenuBar();
		this.barreMenu();
				
		/*----------- personnaliser le pannel----*/
		this.panel          = new JPanel();
		this.panel_article  = new JPanel();
		this.panel_liste    = new JPanel();
		this.panel_operation = new JPanel();
		
		this.panel.setLayout(null);
		this.panel_article.setLayout(null);
		this.panel_operation.setLayout(new GridLayout(1,1));
		this.panel_liste.setLayout(new BorderLayout());
		
		/*------ titre de la page---*/
		this.jtitre = new JLabel(" ENCAISSEMENT FACTURE ");
		this.jtitre.setFont(new Font("Arial", Font.BOLD, 30));
		this.jtitre.setForeground(new Color(223,175,44));  
		this.jtitre.setBounds(mgLeft-100,mgTop, width, height);
		this.panel.add(jtitre); 
		
		this.jtitre = new JLabel("OPÉRATIONS DU  "+LocalDate.now().toString());
		this.jtitre.setFont(new Font("Arial", Font.BOLD, 30));
		this.jtitre.setForeground(new Color(223,175,44));  
		this.jtitre.setBounds(mgLeft+660,mgTop, width, height);
		this.panel.add(jtitre); 
		 
		this.jsearch = new JLabel("RECHERCHER UNE FACTURE:");
		this.jsearch.setBounds(mgLeft-200,mgTop+35+(height+150)+450+20, width, height);
		this.jsearch.setFont(new Font("Arial", Font.BOLD, 15));
		this.panel.add(jsearch);
		
		this.tsearch = new JTextField();
		this.tsearch.setBounds(mgLeft-200+250,mgTop+35+(height+150)+450+20+5, width-300, height-15);
		this.tsearch.setFont(new Font("Arial", Font.BOLD, 15));
		this.panel.add(tsearch);
			
		this.bloc_article();
		this.bloc_liste();
		this.liste_commande_jour();
		
		/*----*/
		
		
		// buttons
				 
		       
					
		/*----*/
				this.jqtyTotal = new JLabel("QTY VENDU:");
				this.jqtyTotal.setBounds(mgLeft-200+720 ,mgTop+35+(height+150)+450+18, width, height);
				this.jqtyTotal.setFont(new Font("Arial", Font.BOLD, 15));
				this.panel.add(jqtyTotal);
				
				this.tqtyTotal = new JTextField(""+this.article.somme_qty);
				this.tqtyTotal.setBounds(mgLeft-200+720+40+40+30+20,mgTop+35+(height+150)+450+20, width-500, height-10);
				this.tqtyTotal.setFont(new Font("time", Font.BOLD, 15));
				this.tqtyTotal.disable();
				this.panel.add(tqtyTotal);
				
				this.jPrixTotal = new JLabel("MONTANT TOTAL:");
				this.jPrixTotal.setBounds(mgLeft-200+720+200+100+40 ,mgTop+35+(height+150)+450+18, width, height);
				this.jPrixTotal.setFont(new Font("Arial", Font.BOLD, 15));
				this.panel.add(jPrixTotal);
				
				this.tPrixTotal = new JTextField(""+this.article.somme_money+" FCFA");
				this.tPrixTotal.setBounds(mgLeft-200+720+40+40+30+20+370,mgTop+35+(height+150)+450+20, width-500, height-10);
				this.tPrixTotal.setFont(new Font("time", Font.BOLD, 15));
				this.tPrixTotal.disable();
				this.panel.add(tPrixTotal);
				
				
		/*---- joindre l'ecouteur ----*/
		this.bvalider.addActionListener(new ListernerCaisse(this));
		this.bannuler.addActionListener(new ListernerCaisse(this));
		this.quitter.addActionListener(new ListernerCaisse(this));
		
		/*----------- personnaliser la fenetre ------*/
		this.setTitle("COMPTE CAISSE");  
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
		this.panel_operation.setBounds(mgLeft-200+720 ,mgTop+35, width, height+600);
		this.panel_operation.setBorder(eBorder2);
		
		
		/*---- tableau ----*/
		this.article = new Article();
		Object[][] data = article.liste_facture_paye();
		
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
		this.tcode = new JTextField();
		this.tcode.setBounds(mgLeft-225+80,mgTop+10, width-500, height-10);
		this.tcode.disable();
		this.panel_article.add(tcode); 
		
		this.jquantite = new JLabel(" QTy: ");
		this.jquantite.setBounds(mgLeft-225+80+80+80+60,mgTop+10, width-500, height-10);
		this.jquantite.setFont(new Font("time", Font.BOLD, 15));
		this.panel_article.add(jquantite);
		
		this.tquantite = new JTextField();
		this.tquantite.setBounds(mgLeft-225+80+80+80+60+60,mgTop+10, width-500, height-10);
		this.tquantite.setFont(new Font("time", Font.BOLD, 15));
		this.tquantite.disable();
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
		this.tprix.disable();
		this.panel_article.add(tprix); 
		
		this.tclient = new JTextField();
		this.tclient.setBounds(mgLeft-225+80+80+80+60+60,mgTop+10+60, width-500, height-10);
		this.tclient.setFont(new Font("time", Font.BOLD, 15));
		this.tclient.disable();
		this.panel_article.add(tclient); 
		
		// buttons
		this.bvalider = new JButton("Encaisser");  
		this.bvalider.setBounds(mgLeft-225+500,mgTop+10+60+60, width-550, height-15);
		this.panel_article.add(bvalider);
		
		this.bannuler = new JButton("Annuler");
		this.bannuler.setBounds(mgLeft-225,mgTop+10+60+60, width-550, height-15);
		this.panel_article.add(bannuler);
		
		//this.listepanel.add(recherchePanel, "South");
		this.panel.add(panel_article); 
	}
	
	/*---- bloc tableau liste facture impayesr----*/
	public void bloc_liste() { 
		
		Border eBorder2 = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		this.panel_liste.setBounds(mgLeft-200,mgTop+35+(height+150)+30+30, width, height+150+200);
		this.panel_liste.setBorder(eBorder2);
		
		/*---- tableau ----*/
		this.article = new Article();
		Object[][] data = article.liste_facture_impaye();
		
		// titre des colinnes
		String title[] = {"#", "Num Fact", "Client", "Etat", "Quantité", "prix.T"};
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
				          keys = sel[t-1];
				          tcode.setText(""+tm.getValueAt(sel[t-1],1));
				          tquantite.setText(""+tm.getValueAt(sel[t-1],4));
				          tprix.setText(""+tm.getValueAt(sel[t-1],5));
				          tclient.setText(""+tm.getValueAt(sel[t-1],2));
				          System.out.print(tm.getValueAt(sel[t-1],1) + " ");
				      }
				    }	  
				}
			};
			lisModel.addListSelectionListener(listSelectio);
		
	       
	       jTable.setEnabled(true);
	       panel_liste.add(new JScrollPane(jTable), "North");
			
	       int taille  = data.length;
			int i = 0;
			
			while (i < taille) {
				this.addLigne(data[i]);
				i++;
			}
			
			TableRowSorter<TableModel> sort = new TableRowSorter<>(jTable.getModel());
			
	        //définir la largeur de la 3éme colonne sur 200 pixels
	        TableColumnModel columnModel = jTable.getColumnModel();
	        columnModel.getColumn(2).setPreferredWidth(200);
	    
	        jTable.setRowSorter(sort);
	        
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
