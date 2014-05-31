package android;


import java.io.Reader;
import java.lang.reflect.Array;
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
	                          "jdbc:mysql://127.0.0.1/rally";
	
	 static Connection c;
	 static Properties p =new Properties();;
	 static Statement st; 
	   /*
	   cette fonction nous permet de r√©cuperer les questions de la base de donn√©es
	   en cas de succ√©s elle renvoie la liste des  quetions en string
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
		 
		  return "questions__"+s;
	    	
	  }
	   
	   /*ecrire le score dans la BDD*/
	   
	   public static void ecrireScore(String time){
		  double sc = Double.parseDouble(time);
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
			    st.executeUpdate("UPDATE `joueur` SET `score`="+sc+" WHERE `pseudo`='joueur1'");
			    //st.executeUpdate("INSERT INTO `joueur`(`sc`) where id=1 VALUES ("+time+")");
			    System.out.println("ecriture reussite "+sc+"\n");
			    
			   
			   // st.close();
			  
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	
	}
	   
	   /*
	    * Cette fonction permet de supprimer une question de la base de donÈe,
	    * renvoie true en cas de succes false sinon
	    * 
	    * @return boolean
	    */
	   public static boolean deleteQuestion(String s){
		  

		   try {
				Class.forName(dbClassName);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
			  try {
				
				p.put("user","root");
			    p.put("password","");
			    Connection c = DriverManager.getConnection(CONNECTION,p);
			    st=c.createStatement();
			    st.executeUpdate("DELETE FROM `question` WHERE `question`='"+s+"'");
			    //st.executeUpdate("INSERT INTO `joueur`(`sc`) where id=1 VALUES ("+time+")");
			    System.out.println("suppression reussite \n");
			    return true;
			    
			   
			   // st.close();
			  
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	   }
	   
	   
	   
	   
	   public static String inscription(String pseudo , String pass){
	       String s ="";
	    try {
	   p = new Properties();
	   
	   p.put("user","root");
	      p.put("password","");
	      Connection c = DriverManager.getConnection(CONNECTION,p);
	      st = c.createStatement();
	      try{
	       st.executeUpdate("INSERT INTO joueur (pseudo,passeword) VALUES('"+pseudo+"','"+pass+"')");
	       s="inscription reussie";
	      }
	     catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    s ="inscription echouÈe";
	   }
	      
	         
	         
	       st.close();
	      st.close();
	  } catch (SQLException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	   return s;
	            }
	            
	            
	            
	            public static String connexion(String pseudo , String pass) {
	           String s=""; 
	          try {
	         p = new Properties();
	         
	         p.put("user","root");
	            p.put("password","");
	             
	            Connection c = DriverManager.getConnection(CONNECTION,p);
	            
	            st = c.createStatement();
	          ResultSet r = st.executeQuery("SELECT * FROM joueur where pseudo = '"+pseudo+"' and passeword ='"+pass+"'");
	           boolean isEmpty = true; 
	           System.out.println(r.first());
	         while(r.next()){
	          isEmpty = false;
	          
	         }
	         if (!r.first()){
	          s="pseudo ou mot de passe incorrect";
	         }
	         else{
	          s = "connexion reussi";
	         }
	            st.close();
	        } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	        }
	          
	         return s;
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
	   // System.out.println("It works !"+recupererQuestions()+"\n");
	   
	  deleteQuestion("rrrrr");
	    
       // st.close();	

	    }
	}