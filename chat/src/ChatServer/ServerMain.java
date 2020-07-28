
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
           
          //Criando Threads para multiplas conexoes separadas; 
           Thread t;
           t = new Thread(){
               
               @Override
               public void run(){
                
                   try{
                     
                         handleClientSocket(clientSocket);  
                   }catch(IOException erro){
                            erro.printStackTrace();
                        }
                   catch(InterruptedException erro){
                        erro.printStackTrace();
                         }
               }
           };
           
           t.start();//Inicia a Thread
           
   
           
           
           
       }
       
       
       
   }  catch(IOException erro){
       
       erro.printStackTrace();
   }   
    }
    
    //Metodo responsavel por criar varias conexoes
    private static void handleClientSocket(Socket clientSocket) throws IOException, InterruptedException         
    {
        OutputStream outputStream= clientSocket.getOutputStream();
   for(int i=0; i<10; i++)
   {
   
         outputStream.write(("Hello GQ29 "+new Date()+"\n").getBytes());
         Thread.sleep(1000);
   }
              
     clientSocket.close();   
        
    }
            
}
