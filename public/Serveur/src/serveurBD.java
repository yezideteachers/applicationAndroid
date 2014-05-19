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
	 private static final String dbClassName = "com.mysql.jdbc.Driver";
	 private static final String CONNECTION =
	                          "jdbc:mysql://127.0.0.1/L3IE";
	
	 static Connection c;
	 static Properties p ;
	 static Statement st; 
	   /*
	   cette fonction nous permet de récuperer les questions de la base de données
	   en cas de succés elle renvoie la liste des  quetions en string
	   sinon elle renvoie une exception.
	   
	   @return string 
	   */	   
	   public static String recupererQuestions(){
		  String s="";
		  try {
			Connection c = DriverManager.getConnection(CONNECTION,p);
			p.put("user","root");
		    p.put("password","");
		    ResultSet r = st.executeQuery("SELECT * FROM Question");
		    
		    while(r.next()){
		    	s+=r.getString("question")+" # "+r.getString("reponse")+" # "+r.getString("choix1")+"\n";
		    }
		    st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return s;
	    	
	    }

            public static void inscription(String pseudo , String pass){
		     // String s ="";
		  try {
			p = new Properties();
			
			p.put("user","root");
		    p.put("password","diallo");
		    Connection c = DriverManager.getConnection(CONNECTION,p);
		    st = c.createStatement();
        	st.executeUpdate("INSERT INTO joueur (pseudo,passeword) VALUES('"+pseudo+"','"+pass+"')");
        	
 		    st.close();
		    st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
            }
            
            
            
            public static String connexion(String pseudo , String pass){
        	  String s="";	
      		  try {
      			p = new Properties();
      			
      			p.put("user","root");
      		    p.put("password","diallo");
      		    Connection c = DriverManager.getConnection(CONNECTION,p);
      		    st = c.createStatement();
      		  ResultSet r = st.executeQuery("SELECT * FROM joueur where pseudo = '"+pseudo+"' and passeword ='"+pass+"'");
          	boolean isEmpty = true; 
   		    while(r.next()){
   		    	isEmpty = false;
   		    	
   		    }
   		    if (isEmpty){
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
 
           
	    
	    }


	

		