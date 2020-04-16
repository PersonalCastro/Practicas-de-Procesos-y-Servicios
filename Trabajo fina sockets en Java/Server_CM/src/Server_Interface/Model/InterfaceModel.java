/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Interface.Model;

import java.util.ArrayList;

/**
 *
 * @author PersonalCastro
 */
public class InterfaceModel {
    
    ArrayList<String> ports;
    
    public InterfaceModel(){
        this.ports = new ArrayList();
    }
    
    public void addPort(String port){
        ports.add(port);
    }
    
    public int searchPortPosition(String portName){
        int posicionAux = -1;
                
        for(int i = 0; i < ports.size(); i++){
            if(ports.get(i).equals(portName)){
                
                posicionAux = i; 
            }
        }

        return posicionAux;
    }
    
    public void eliminatePort(String port){
        
        ports.remove(searchPortPosition(port));
        
    }
    
    
    
}
