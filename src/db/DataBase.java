package db;
import java.awt.Toolkit;
import java.sql.*;

public class DataBase {
	
	private static final String bDpath = "components/bdStock.sqlite3";
	private Connection connection = null;
	private Statement statement   = null;
	
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:"+bDpath);
			this.statement  = connection.createStatement();
			System.out.println("Connexion a "+ bDpath+" reussir");
		} catch (ClassNotFoundException notFoundException) { 
			notFoundException.printStackTrace();
			System.out.println("Erreur de connexion");
		}catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println("Erreur de connexion");   
		}
	}
	
	public void close() {
		try {
			this.connection.close();
			this.statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public static String getBdpath() {
		return bDpath;
	}
	
	
}
