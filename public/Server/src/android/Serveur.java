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
 
   public static String renvoyerLesQuestions(){
    	String ques = serveurBD.recupererQuestions();
    	
    	return ques;  	
    }
   
   public static void ecrireSc(){
	   final boolean []cond={true};
       Thread lecture= new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub*/
				while (cond[0]) {
					try {
						 System.out.println("1 \n");
				    	   clientSocket = serverSocket.accept(); 
				    	   inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
				    	   bufferedReader = new BufferedReader(inputStreamReader); //get the client message 
				    	   
					       message = bufferedReader.readLine();
					       System.out.println(message + "3243242\n");
					       if(message.length()>0){
					    	   serveurBD.ecrireScore(Long.parseLong(message)/100);
					    	//   inputStreamReader.close();
					    	   //cond[0]=false;
					       }
					       clientSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
		});
       lecture.start();
   
   }
   
   
   public static boolean afficheQuestion(){
	  final boolean  [] valreturn = null;
	  valreturn[0] = true;
	   Thread ecriture = new Thread(new Runnable() {
			
			@Override
			public void run() {
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
			               valreturn[0]= false;
			            }
			        }
			}
		});
      ecriture.start();
	return valreturn[0];
   }
    /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

 
        try {
            serverSocket = new ServerSocket(3003);  //Server socket
   
        } catch (IOException e) {
            System.out.println("Could not listen on port: 54446");
        }
  
        System.out.println("serveur demarer et ecoute sur le port 4443");
       
        /* while (true) {
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
            }
        }*/
        ecrireSc();
        
     afficheQuestion();
     
     
       
        
 
    }
}


