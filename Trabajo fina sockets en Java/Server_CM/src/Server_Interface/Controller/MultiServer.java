/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Interface.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 * @author PersonalCastro
 */
public class MultiServer {
    
    private boolean stopThread;
    
    private MultiServerThread kkMultiServerThread;
    
    private ArrayList<MultiServerThread> multiServerThreads;
    
    public MultiServer(){
        stopThread = false;
        multiServerThreads = new ArrayList();
        kkMultiServerThread = new MultiServerThread();
    }
    
    public ArrayList<MultiServerThread> getMultiServerThreads(){
        return multiServerThreads;
    }
    
    
    public void stopThread(int posThread){
        //stopThread = true;
        //multiServerThreads.get(posThread).stopThread = true;
    }    
    
    public int multiServerThreadsSize(){
        
        return multiServerThreads.size();
    }
    
    
    
    public void startServer(String port, String ip_bd) throws IOException {
    
        int portNumber = Integer.parseInt(port);
        boolean listening = true;

        
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (listening) {
                multiServerThreads.add(new MultiServerThread(this, serverSocket.accept(), ip_bd));
                if(multiServerThreads.get(multiServerThreads.size() -1) != null){
                    multiServerThreads.get(multiServerThreads.size() -1).start();
                }
	    }
	} catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber + " : " + e);

        }
    }
    
    public void finishThisConnexion(String login){
        
        for(int i = 0; i < this.multiServerThreads.size(); i++){
            if(this.multiServerThreads.get(i).loginThread.equals(login)){
                this.multiServerThreads.remove(i);
            }
        }
    }
    
    public void debugActualConnections(){
        for(int i = 0; i < this.multiServerThreads.size(); i++){
            System.out.println("Hebra n-" + i + ": " + this.multiServerThreads.get(i).loginThread);
        }
    }

}
