
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
    private static serveurBD bd = new serveurBD();
 
  public static String renvoyerLesQuestions(){
    	String ques = bd.recupererQuestions();
    	
    	return ques;  	
    }

  public static String connect(String pseudo, String pass){
	  String res = bd.connexion(pseudo, pass);
	  return res;
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
 
        while (true) {
            try {
 
                clientSocket = serverSocket.accept();   //accept the client connection
                inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                message = bufferedReader.readLine();
                String[] m = message.split(";");
                String pseudo = m[0];
                String pass = m[1];
		        bd.inscription(pseudo,pass);
		        String req = connect(pseudo,pass);
		
		
				
				 PrintWriter p = new PrintWriter(clientSocket.getOutputStream());
                 p.write(req);
                 p.flush();
                 p.close();
				 //System.out.println(renvoyerLesQuestions());
                
               // inputStreamReader.close();
                clientSocket.close();
 
            } catch (IOException ex) {
                System.out.println("Problem in message reading");
            }
        }
 
    }
}
