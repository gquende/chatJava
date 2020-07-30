/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author za
 */
public class ServerWorker extends Thread {
    
    private final Socket clientSocket;
    
    public ServerWorker(Socket clientSocket){
        
        this.clientSocket=clientSocket;
       
    }
    
    @Override
    public void run(){
        try {  
            handleClientSocket();
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
      private void handleClientSocket() throws IOException, InterruptedException         
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
