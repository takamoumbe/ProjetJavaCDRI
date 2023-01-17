package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import listener.ListenerLogin;
import listener.ListenerParametre;
import model.User;

public class Parametre extends JFrame{
	
	/*------size fenetre ----*/
	private static int HAUTEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(); 
	private static int LARGEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	/*-----------------------*/
	public static JLabel jpassword, jlogin, jlastPass;
	public static JTextField  tlogin;
	public static JButton bvalider;
	public static JPasswordField tpassword, lastpass;
	/*--------- bounds -----*/
	private static int mgLeft = 560;
	private static int mgTop  = 250; 
	private static int width  = 300;
	private static int height = 40;
	private JLabel pic;
	
	private JPanel panel; 
	 
	public Parametre() throws IOException {
		/*----------- personnaliser le pannel----*/
		this.panel = new JPanel(); 
		this.panel.setLayout(null); 
		
		/*------logo-----*/ 
		//BufferedImage img = ImageIO.read(new File("components/img/logo.png/"));
		this.setIconImage(new ImageIcon("images/logo.png").getImage());  
		Icon img = new ImageIcon("images/logo.png"); 
		pic  = new JLabel(img);
		this.pic.setBounds(mgLeft,mgTop-200, width, height+100);
		this.panel.add(pic);
		
		this.jlogin     = new JLabel("Login");
		this.jlogin.setFont(new Font("Arial",Font.BOLD,17));
		this.jlogin.setBounds(mgLeft,mgTop, width, height);
		this.panel.add(jlogin); 
		
		/*------- recuperation de l'user ----*/
		User user = new User();
		
		this.tlogin = new JTextField(""+user.getLogin());
		this.tlogin.setBounds(mgLeft,mgTop+35, width, height);
		this.tlogin.setFont(new Font("Serif", Font.BOLD, 15));
		this.tlogin.disable();
		this.panel.add(tlogin);  
		
		this.jpassword = new JLabel("Ancien Mot de passe");
		this.jpassword.setFont(new Font("Arial",Font.BOLD,17));
		this.jpassword.setBounds(mgLeft,mgTop+35+50, width, height);
		this.panel.add(jpassword);
		
		this.tpassword = new JPasswordField();
		this.tpassword.setBounds(mgLeft,mgTop+35+50+35, width, height);
		this.tpassword.setFont(new Font("Serif", Font.BOLD, 15));
		this.panel.add(tpassword); 
		
		this.jlastPass = new JLabel("Nouveau Mot de passe");
		this.jlastPass.setFont(new Font("Arial",Font.BOLD,17));
		this.jlastPass.setBounds(mgLeft,mgTop+35+50+35+40, width, height);
		this.panel.add(jlastPass);  
		  
		this.lastpass = new JPasswordField();
		this.lastpass.setBounds(mgLeft,mgTop+35+50+35+35+40, width, height);
		this.lastpass.setFont(new Font("Serif", Font.BOLD, 15));
		this.panel.add(lastpass);   
		
		this.bvalider = new JButton("Enregistrer");
		this.bvalider.setBackground(Color.BLACK); 
		this.bvalider.setForeground(Color.WHITE);
		this.bvalider.setBounds(mgLeft,mgTop+35+50+35+100+35, width, height);
		this.panel.add(bvalider); 
		
		/*----------------------- Joindre l'ecouteur -----------------*/
		this.bvalider.addActionListener(new ListenerParametre(this));
		
		/*----------- personnaliser la fenetre ------*/
		this.setTitle("PARAMETRE DE COMPTE"); 
		this.setLocation(new Point(500, 300)); 
		this.add(panel);
		this.setSize(LARGEUR, HAUTEUR);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
