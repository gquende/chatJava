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
import java.util.List;
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
    private final Server server;
    private OutputStream outputStream;
     
    ServerWorker(Server server, Socket clientSocket) {
        this.clientSocket=clientSocket;
        this.server=server;
    }
    
    public String getLogin(){
        
        return login;
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
        this.outputStream= clientSocket.getOutputStream();
        BufferedReader reader= new BufferedReader(new InputStreamReader(inputStream));//Para ler linha a linha
        
        String line;
        
        while((line=reader.readLine())!=null){//Faz a leitura dos comandos inseridos na linha de comando
            String [] tokens= StringUtils.split(line); //Separa as palavras
            
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
              String msg= "oks login\n";
              outputStream.write(msg.getBytes()); 
             
              System.out.println("User Logged in succesfuly: "+login);
             
              String onlineMsg= "Online "+login+"\n";
              
              List<ServerWorker> workerList=server.getWorkerList();//Pega a lista das pessoas conectadas ao sevidor
              
              //Para enviar o usuario actual para outros utilizadores online
             for(ServerWorker worker : workerList){
                  if(worker.getLogin()!=null) //Para precaver o envio de online a si mesmo
               {
                //para evitar utilizadores ligado sem seacao iniciada possam receber o estado de online
                   if(!login.equals(worker.getLogin()))
                   {
                        String onlineMsg2= "Online "+worker.getLogin()+"\n";
                 worker.send(onlineMsg2);
                   }
               }
                  
              } 
              
              
              //Para outros usuarios online neste actual
              for(ServerWorker worker : workerList){
                 if(!login.equals(worker.getLogin())) 
                  worker.send(onlineMsg);
                  
              }
              
              
          }else{
                String msg= "error login\n";
              outputStream.write(msg.getBytes()); 
          }
      }
    }

    private void send(String onlineMsg) throws IOException {
       
  if(login!=null)
      outputStream.write(onlineMsg.getBytes());
    }
}
