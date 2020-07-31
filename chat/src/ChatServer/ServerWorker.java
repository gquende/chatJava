/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author za
 */
public class ServerWorker extends Thread {
    
    private final Socket clientSocket;
    private String login=null;
    
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
        InputStream inputStream = clientSocket.getInputStream();//Serve para escrever dados ou mensagem do cliente
        OutputStream outputStream= clientSocket.getOutputStream();
        BufferedReader reader= new BufferedReader(new InputStreamReader(inputStream));//Para ler linha a linha
        
        String line;
        
        while((line=reader.readLine())!=null){
            String [] tokens= StringUtils.split(line);
            
          if(tokens !=null && tokens.length>0)
          {
              String cmd= tokens[0];
                if("quit".equalsIgnoreCase(cmd))
            {
                break;
                
            }
                else if ("login".equalsIgnoreCase(cmd)){
                    
                    handleLogin(outputStream, tokens);
                    
                }
          
                /*String msg="You typed: "+line+"\n";
            outputStream.write(msg.getBytes());
           */   
          }
            
        }
  
        
        /*for(int i=0; i<10; i++)
   {
   
         outputStream.write(("Hello GQ29 "+new Date()+"\n").getBytes());
         Thread.sleep(1000);
   }
       */       
     clientSocket.close();   
        
    }

      //Funcao que faz o login
    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
      if(tokens.length==3){
          String log=tokens[1];
          String password=tokens[2];
           this.login=log;
          
          if("guest".equalsIgnoreCase(login) && "guest".equalsIgnoreCase(password) || "geraldo".equalsIgnoreCase(login) && "quende".equalsIgnoreCase(password))
          {
              String msg= "ok login\n";
              outputStream.write(msg.getBytes()); 
             
              System.out.println("User Logged in succesfuly: "+login);
          }else{
                String msg= "error login\n";
              outputStream.write(msg.getBytes()); 
          }
      }
    
    
    }
}
