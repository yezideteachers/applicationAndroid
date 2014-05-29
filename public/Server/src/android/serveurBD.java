package android;


import java.io.Reader;
import java.sql.*;
//import com.mysql.jdbc.Driver; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class serveurBD {

	/**
	 * @param args
	 */
	 static final String dbClassName = "com.mysql.jdbc.Driver";
	  static final String CONNECTION =
	                          "jdbc:mysql://127.0.0.1/test";
	
	 static Connection c;
	 static Properties p =new Properties();;
	 static Statement st; 
	   /*
	   cette fonction nous permet de récuperer les questions de la base de données
	   en cas de succés elle renvoie la liste des  quetions en string
	   sinon elle renvoie une exception.
	   
	   @return string 
	   */	   
	   public static String recupererQuestions(){
		   try {
			Class.forName(dbClassName);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  String s="";
		  try {
			Connection c = DriverManager.getConnection(CONNECTION,p);
			p.put("user","root");
		    p.put("password","");
		    st=c.createStatement();
		    ResultSet r = st.executeQuery("SELECT * FROM quiz");
		    
		    while(r.next()){
		    	s+=r.getString("quest")+" # "+r.getString("reponse")+" # "+r.getString("choix1")+" # "+r.getString("choix2")+"__";
		    }
		    st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		  return s;
	    	
	  }
	   
	   /*ecrire le score dans la BDD*/
	   
	   public static void ecrireScore(long time){
		   try {
				Class.forName(dbClassName);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			  String s="";
			  try {
				Connection c = DriverManager.getConnection(CONNECTION,p);
				p.put("user","root");
			    p.put("password","");
			    st=c.createStatement();
			    st.executeUpdate("INSERT INTO `score`(`sc`) VALUES ("+time+")");
			    System.out.println("ecriture reussite \n");
			    
			   
			   // st.close();
			  
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	
	}
	  
		
		
	  public static void main(String[] args) throws
	                             ClassNotFoundException,SQLException
	  {
	    System.out.println(dbClassName);
	    // Class.forName(xxx) loads the jdbc classes and
	    // creates a drivermanager class factory
	 

	    // Properties for user and password. Here the user and password are both 'paulr'
	   // p = new Properties();
	 /*   p.put("user","root");
	    p.put("password","");
*/
	    // Now try to connect
	   // c = DriverManager.getConnection(CONNECTION,p);
	    
//	    st = c.createStatement();
        //st.execute("INSERT into joueur VALUES('test','tuple')");
       // ResultSet  r = st.executeQuery("SELECT * FROM joueur  ");
       /* while(r.next()){
			//System.out.println("nom : " + r.getString("pseudo") + "  password : " + r.getString("passeword"));
			//System.out.println("jour : " + leResultat.getString("jour")+ "heure : " + leResultat.getInt("heure"));
		}
	*/
	    System.out.println("It works !"+recupererQuestions()+"\n");
	   
	    
	   
       // st.close();	

	    }
	}