package android;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import android.serveurBD.*;

public class serveurBDTest {
    
	@Test
	public void testRecupererQuestions() {
		
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
			    ResultSet r = serveurBD.st.executeQuery("SELECT * FROM quiz");
			    
			    while(r.next()){
			    	s+=r.getString("quest")+" # "+r.getString("reponse")+" # "+r.getString("choix1")+" # "+r.getString("choix2")+"__";
			    }
			    serveurBD.st.close();
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
		long time = 1000; //tester avec un temps de mille nano-seconde
		
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
			    ResultSet r = serveurBD.st.executeQuery("SELECT * FROM score WHERE 2");
			    
			    while(r.next()){
			    	s+=r.getString("sc");
			    }
			    serveurBD.st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String s2 = ""+time;
			
			
			assertEquals(s, s2); //comparaison du score ecrit dans la BD avec celui qu'on vient de recuperer 
	}

}
