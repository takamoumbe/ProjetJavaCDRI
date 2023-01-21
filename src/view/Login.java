package view;
import javax.imageio.ImageIO;
import javax.swing.*;

import listener.ListenerLogin;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Login extends JFrame{
	/*------size fenetre ----*/
	private static int HAUTEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(); 
	private static int LARGEUR    = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	/*-----------------------*/
	public static JLabel jpassword, jlogin;
	public static JTextField  tlogin;
	public static JButton bconnexion;
	public static JPasswordField tpassword;
	/*--------- bounds -----*/
	private static int mgLeft = 560;
	private static int mgTop  = 250; 
	private static int width  = 300;
	private static int height = 40;
	private JLabel pic;
	
	private JPanel panel; 
	 
	public Login() throws IOException {
		/*----------- personnaliser le pannel----*/
		this.panel = new JPanel(); 
		this.panel.setLayout(null); 
		
		/*------logo-----*/ 
		//BufferedImage img = ImageIO.read(new File("components/img/logo.png/"));
		this.setIconImage(new ImageIcon("images/logo.png").getImage());
		Icon img = new ImageIcon("images/logo.png"); 
		pic  = new JLabel(img);
		this.pic.setBounds(mgLeft,mgTop-155, width, height+145);
		this.panel.add(pic);
		
		this.jlogin     = new JLabel("Login");
		this.jlogin.setFont(new Font("Arial",Font.BOLD,17));
		this.jlogin.setBounds(mgLeft,mgTop, width, height);
		this.panel.add(jlogin); 
		
		this.tlogin = new JTextField();
		this.tlogin.setBounds(mgLeft,mgTop+35, width, height);
		this.tlogin.setFont(new Font("Serif", Font.BOLD, 15)); 
		this.tlogin.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		this.panel.add(tlogin); 
		
		this.jpassword = new JLabel("Mot de passe");
		this.jpassword.setFont(new Font("Arial",Font.BOLD,17));
		this.jpassword.setBounds(mgLeft,mgTop+35+50, width, height);
		this.panel.add(jpassword);
		
		this.tpassword = new JPasswordField();
		this.tpassword.setBounds(mgLeft,mgTop+35+50+35, width, height);
		this.tpassword.setFont(new Font("Serif", Font.BOLD, 15));
		this.tpassword.setHorizontalAlignment((int) CENTER_ALIGNMENT);  
		this.panel.add(tpassword);   
		
		this.bconnexion = new JButton("Connexion");
		this.bconnexion.setBackground(new Color(15, 5, 107));   
		this.bconnexion.setForeground(Color.WHITE);
		this.bconnexion.setBounds(mgLeft,mgTop+35+50+35+100, width, height);
		this.panel.add(bconnexion); 
		
		/*----------------------- Joindre l'ecouteur -----------------*/
		this.bconnexion.addActionListener(new ListenerLogin(this));
		
		/*----------- personnaliser la fenetre ------*/
		this.setTitle("BIENVENUE CHER ADMINISTRATEUR"); 
		this.setLocation(new Point(500, 300)); 
		this.add(panel);
		this.setSize(LARGEUR, HAUTEUR);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
