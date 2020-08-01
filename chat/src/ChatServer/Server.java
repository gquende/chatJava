/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author za
 */
public class Server extends Thread {

    private final int serverPort;
    
    private ArrayList<ServerWorker> workerList=new ArrayList<>();
    
    Server(int serverPort) {
   
     this.serverPort=serverPort;   
    }
    
    
    public List<ServerWorker> getWorkerList(){
    
    return workerList;
    
    }
        
    
    @Override
    public void run(){
        
          try{
       
       ServerSocket serverSocket = new ServerSocket(serverPort);   
       
       while(true){
           
           Socket clientSocket=serverSocket.accept();
           System.out.println("Accepted connection from: "+clientSocket);
           ServerWorker worker= new ServerWorker(this, clientSocket);
           workerList.add(worker);//Adiciona novo userLigado
            worker.start();
           
       } 
       
   }  catch(IOException erro){
       
       erro.printStackTrace();
   }   
    }
        
    }
    

