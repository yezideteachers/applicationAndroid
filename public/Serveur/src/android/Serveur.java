//package android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	  try {
		clientSocket = serverSocket.accept();
		System.out.print("connexion etablie 2\n");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}   //accept the client connection
     
       try {
    	   System.out.println("debog 1 \n");
		inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
		bufferedReader = new BufferedReader(inputStreamReader); //get the client message
		 System.out.println("debog 2 \n");
	       message = bufferedReader.readLine();
	       System.out.println(message + "lalalalalalalala \n");
	       serveurBD.ecrireScore(Long.parseLong(message)/100);
	       System.out.println(message + "lalalalalalalala \n");
	       inputStreamReader.close();
	       clientSocket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   
   }
   
   
    /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

 
        try {
            serverSocket = new ServerSocket(8099);  //Server socket
   
        } catch (IOException e) {
            System.out.println("Could not listen on port: 54446");
        }
  
        System.out.println("serveur demarer et ecoute sur le port 4443");
 
        while (true) {
            try {
 
                clientSocket = serverSocket.accept();   //accept the client connection
              /*  inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                message = bufferedReader.readLine();
				*/
				 PrintWriter p = new PrintWriter(clientSocket.getOutputStream());
                p.write(renvoyerLesQuestions());
                p.flush();
                p.close();
				System.out.println(renvoyerLesQuestions());
                
               // inputStreamReader.close();
                clientSocket.close();
 
            } catch (IOException ex) {
                System.out.println("Problem in message reading");
            }
        }
 
    }
}


