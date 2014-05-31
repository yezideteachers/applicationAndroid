package android;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;
import android.serveurBD.*;

public class serveurBDTest {
	
	 static final String dbClassName = "com.mysql.jdbc.Driver";
	  static final String CONNECTION =
	                          "jdbc:mysql://127.0.0.1/rally";
	
	 static Connection c;
	 static Properties p =new Properties();;
	 static Statement st; 
    
	/*Verifier que la recuperation des questions de la base de donnees 
	 * a bien etait effectuée
	 */
	@Test
	public void testRecupererQuestions() {
		
		 try {
				Class.forName(dbClassName);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			  String s="";
			  try {
				p.put("user","root");
				p.put("password","");
				Connection c = DriverManager.getConnection(CONNECTION,p);
				
			    st=c.createStatement();
			    ResultSet r = st.executeQuery("SELECT * FROM question");
			    
			    while(r.next()){
			    	s+=r.getString("question")+" # "+r.getString("reponse")+" # "+r.getString("choix1")+" # "+r.getString("choix2")+"__";
			    }
			    st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
		assertNotNull(serveurBD.recupererQuestions());
		assertNotNull(s);
		assertEquals(s,serveurBD.recupererQuestions());
	}

	/** Verifier l'ecriture du score dans la base de donnees
	 * 
	 */
	@Test
	public void testEcrireScore() {
		String time = "0.0"; //tester avec un temps de mille nano-seconde
		
		serveurBD.ecrireScore(time); //ecriture du score dans la base de donnees
		
		 try {
				Class.forName(serveurBD.dbClassName);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			  String s="";
			  try {
				Connection c = DriverManager.getConnection(serveurBD.CONNECTION,serveurBD.p);
				serveurBD.p.put("user","root");
				serveurBD.p.put("password","");
				serveurBD.st=c.createStatement();
			    ResultSet r = serveurBD.st.executeQuery("SELECT * FROM joueur WHERE `pseudo`='joueur1' ");
			    
			    while(r.next()){
			    	s+=r.getString("score");
			    }
			    serveurBD.st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("score :"+s);
			String s2 = time;
			
			
			assertEquals(s, s2); //comparaison du score ecrit dans la BD avec celui qu'on vient de recuperer 
	}

}
