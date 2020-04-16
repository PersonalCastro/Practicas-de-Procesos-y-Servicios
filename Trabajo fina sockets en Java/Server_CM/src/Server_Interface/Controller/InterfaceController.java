/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Interface.Controller;

import Server_Interface.Model.InterfaceModel;
import java.util.ArrayList;

/**
 *
 * @author PersonalCastro
 */
public class InterfaceController {
  
    ArrayList<ServerThread> array_Servers;
    InterfaceModel modeloDeLaInterfaz;

    public InterfaceController(){
        
        this.array_Servers = new ArrayList();
        modeloDeLaInterfaz = new InterfaceModel();
    }
    
    
    
     /********/
     /* Play */
     /********/

    public static boolean notEmptyPort(String auxPort){
        
        boolean emptEmyPort = true;
        
        if(auxPort.isEmpty()){
            emptEmyPort = false;
        }
        
        return emptEmyPort;
    }
    
    public static boolean lessThan6NumbersPort(String auxPort){
        
        boolean correctPortSize = true;
        
        if(auxPort.length() >= 6){
            correctPortSize = false;
        }
        
        return correctPortSize;
    }

    public boolean tryPlayServer_controller(String port, String ip_bd) {

        boolean ServerPlaying = true;
        
        try{
            array_Servers.add(new ServerThread(port, ip_bd));
            modeloDeLaInterfaz.addPort(port);
            array_Servers.get(modeloDeLaInterfaz.searchPortPosition(port)).thr.start();

        }catch(Exception e){
            System.out.println("Errores" + e);
            ServerPlaying = false;
        }
        
        return ServerPlaying;
    }
    
    public boolean portOppen(String port) {

        boolean open = false;
        
        if(modeloDeLaInterfaz.searchPortPosition(port) == -1){
            open = true;
        }
        
        return open;
    }
    
    
     /********/
     /* Stop */
     /********/
    
    public boolean tryStopServer_controller(String port) {

        boolean ServerStoped = true;
        
        try{
            if (modeloDeLaInterfaz.searchPortPosition(port) != -1){
                array_Servers.get(modeloDeLaInterfaz.searchPortPosition(port)).stopThread_controller();
            }
            
        }catch(Exception e){
            System.out.println("Errores" + e);
            ServerStoped = false;
        }        
        return ServerStoped;
    }

    public int portSearch_Controller(String port) {
        int position = modeloDeLaInterfaz.searchPortPosition(port);
        return position;

    }
    
    public void eliminatePort_Controller(String port){
        modeloDeLaInterfaz.eliminatePort(port);
        
    }
    
}
