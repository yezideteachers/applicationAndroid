package android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
 
public class Serveur {
 
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;
    private static String message;
    static serveurBD sb = new serveurBD();
 
   public static String renvoyerLesQuestions(){
    	String ques = serveurBD.recupererQuestions();
    	
    	return ques;  	
    }
   
   public static void ecrireSc(){
	   final boolean []cond={true};
   /*    Thread lecture= new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub*/
				while (true) {
					try {
						 
				    	   clientSocket = serverSocket.accept(); 
				    	   inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
				    	   bufferedReader = new BufferedReader(inputStreamReader); //get the client message 
				    	   System.out.println("connexion \n");
					       message = bufferedReader.readLine();
					       
					       if(message.length()>0){
					    	   System.out.println(message +"\n");
					    	   sb.ecrireScore(message);
					    	  
					    	//   inputStreamReader.close();
					    	   //cond[0]=false;
					       }
					       clientSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
				
				
		/*	}
		});
       lecture.start();
   */
   }
   
   
   public static void afficheQuestion(){
	/*  final boolean  [] valreturn = null;
	  valreturn[0] = true;*/
	/*   Thread ecriture = new Thread(new Runnable() {
			
			@Override
			public void run() {*/
				// TODO Auto-generated method stub
				 while (true) {
			            try {
			            	
			                clientSocket = serverSocket.accept(); 
			                System.out.print("connexion etablie \n");
							PrintWriter p = new PrintWriter(clientSocket.getOutputStream());
			                p.write(renvoyerLesQuestions());
			                p.flush();
			                p.close();
							System.out.println(renvoyerLesQuestions());
							
							clientSocket.close();	
			               
			            } catch (IOException ex) {
			                System.out.println("Problem in message reading");
			              // valreturn[0]= false;
			            }
			        }
			}
	/*	});
      ecriture.start();
	//return valreturn[0];
	} * */
	 
   
    /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

 
        try {
            serverSocket = new ServerSocket(3003);  //Server socket
            
   
        } catch (IOException e) {
            System.out.println("Could not listen on port: "+serverSocket.getLocalPort());
        }
  
        System.out.println("serveur demarer et ecoute sur le port: "+serverSocket.getLocalPort());
       
        while(true){
       
            try {		 
					clientSocket = serverSocket.accept(); 
		    	   final PrintWriter p = new PrintWriter(clientSocket.getOutputStream());
		    	 
	            	   
	            	   Thread ecriture = new Thread(new Runnable() {
	           			
	           			@Override
	           			public void run() {   
	           			 while(true){
	  	            	  
	  	            	   try {            		 
	  	            		 inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
	  	            		  bufferedReader = new BufferedReader(inputStreamReader); //get the client message
	  	            		  
	  	            		  
	  	            		  message = bufferedReader.readLine();
	  	                    String[] m = message.split(";");
	  	                    String ordre = m[0];
	  	                   /* String pseudo = m[1];
	  	                    String pass = m[2];*/
	  	                    System.out.println(ordre);
	  	                    
	  	                    if(ordre.equalsIgnoreCase("Inscription")){
	  	                     String resultat = sb.inscription(m[1],m[2]);
	  	                     System.out.println(resultat);
	  	                     p.println(resultat);
	  	                     
	  	                    }
	  	                    if(ordre.equalsIgnoreCase("Connexion")){
	  	                    	String resultat = sb.connexion(m[1],m[2]);
	  	                    	System.out.println(resultat);
	  	                    	p.println(resultat);
	  	                    }
	  	                    
	  	                  if(ordre.equalsIgnoreCase("Score")){
	  	                    	sb.ecrireScore(m[1]);
	  	                    	System.out.println("score : "+ m[1] +"\n");
	  	                    }
	  	                  
	  	                if(ordre.equalsIgnoreCase("delete")){
  	                    	sb.deleteQuestion(m[1]);
  	                    	System.out.println("question supprimée : "+ m[1] +"\n");
  	                    }
	  	            			

						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println(	"inputStreamReadere vide ligne 131 ");
						}   
	  	            	 
	               }
	           			}});
	            	   ecriture.start();
			      
	               
					System.out.println("liste des questions :"+renvoyerLesQuestions()+"\n");
					
	            	   p.write(renvoyerLesQuestions());
		               p.flush();
		              
					p.close();
					bufferedReader.close(); 
					 clientSocket.close();
			       
			      
				
				
					
            } catch (IOException ex) {
                System.out.println("Problem in message reading");
              // valreturn[0]= false;
            }
        }
        

        
}
}
        
        



