
package ChatServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author Adolfo Quende
 */
public class ServerMain {

 
    public static void main(String[] args) {
 
        
        int port=8818;
   try{
       
       ServerSocket serverSocket = new ServerSocket(port);   
       
       while(true){
           
           Socket clientSocket=serverSocket.accept();
           System.out.println("Accepted connection from: "+clientSocket);
           ServerWorker worker= new ServerWorker(clientSocket);
            worker.start();
           
       } 
       
   }  catch(IOException erro){
       
       erro.printStackTrace();
   }   
    }
    
    //Metodo responsavel por criar varias conexoes
  
            
}
