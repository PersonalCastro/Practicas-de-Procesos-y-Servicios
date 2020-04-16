/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Interface.Controller;

/**
 *
 * @author PersonalCastro
 */
public class ServerThread implements Runnable{

    public Thread thr;
    private String port;
    private String ip_bd;
    private MultiServer server;

    
    public ServerThread(String port, String ip_bd){
                        
        this.setPort(port);
        this.setIp_bd(ip_bd);
        this.server = new MultiServer();
        
        this.thr = new Thread(this);
        
    }
    
    public void setIp_bd(String ip_bd) {
        this.ip_bd = ip_bd;
    }

    public String getIp_bd(){
        return this.ip_bd;
    }
    
   
    public void setPort(String port) {
        this.port = port;
    }

    public String getPort(){
        return this.port;
    }
    
    public void stopThread_controller(){

        
        for(int i = 0;i < server.multiServerThreadsSize(); i++){
            server.stopThread(i);
        }

    }
    
    
    @Override
    public void run() {

        try{
            
            server.startServer(this.getPort(), this.getIp_bd());
        }catch(Exception ex){
            System.out.println("Ocurrio un error al consumir: " + ex);
        }
    }
    
}
