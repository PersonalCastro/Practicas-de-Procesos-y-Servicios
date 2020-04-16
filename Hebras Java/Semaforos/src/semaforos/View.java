/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semaforos;

import java.util.ArrayList;
import semaforos.Hebras.*;

/**
 *
 * @author PersonalCastro
 */
public class View {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
        Buffer buffer = new Buffer(Integer.valueOf(args[0]));

        boolean termina_p = Boolean.valueOf(args[2]);
        boolean termina_c = Boolean.valueOf(args[4]);
        
        ArrayList<HebraProductora> array_productoras = new ArrayList();
        ArrayList<HebraConsumidora> array_consumidora = new ArrayList();

        
        
        for(int i = 0; i < Integer.valueOf(args[1]); i++){
            
            array_productoras.add(new HebraProductora(i, termina_p, buffer));
            array_productoras.get(i).thr.start();
        
        }
        
        for(int i = 0; i < Integer.valueOf(args[3]); i++){
            
            array_consumidora.add(new HebraConsumidora(i, termina_c, buffer));
            array_consumidora.get(i).thr.start();
        
        }
        
        
        for(int i = 0; i < Integer.valueOf(args[1]); i++){
            
            try{
                array_productoras.get(i).thr.join();
            }catch (Exception ex){
            }
        
        }
        
        for(int i = 0; i < Integer.valueOf(args[3]); i++){
            
            try{
                array_consumidora.get(i).thr.join();
            }catch (Exception ex){
            }

        }
        
    }
    
}
