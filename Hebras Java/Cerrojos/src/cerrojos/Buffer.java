/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cerrojos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author PersonalCastro
 */
public class Buffer {
    
    public static final String restaurar  = "\u001B[0m";
    public static final String eliminated    = "\u001B[31m";
    public static final String added  = "\u001B[32m";
    
    public static final String text2   = "\u001B[96m";
    public static final String text3 = "\u001B[93m";

    
    private int buffer[];
    private int size;
    private static int entrada_P;
    private static int entrada_C;
    
    private static int contador;
    
    private Lock mutex;
    private Condition vacio;
    private Condition lleno;
    
    
    public Buffer(int new_size){
        
        setSize(new_size);
        
        mutex = new ReentrantLock();
        vacio = mutex.newCondition();
        lleno = mutex.newCondition();
        
        buffer = new int[getSize()];
    }

    private void setSize(int size) {
        this.size = size;
    } 
    
    private int getSize() {
        return size;
    }
    
    
    public void select_producir(boolean termina, int id_hebra){
        
        if(termina){
            simple_producir(id_hebra); //Just one lap
        }else{
            complex_producir(id_hebra); //All laps it can
        }
        
    }
    
    private void simple_producir(int id_hebra){
        
        mutex.lock();
        while (contador== this.getSize()) {
            try{
                lleno.await();
            }catch(Exception ex){ }
        }

        buffer[entrada_P] = 1;

        System.out.println(text3 + "HebraProductora(" + id_hebra + ") prduce en posición \"" + entrada_P + "\"" + restaurar);
        imprimirBuffer_cambios(entrada_P, true);

        entrada_P++;
        entrada_P = entrada_P % getSize();
        contador ++;

        vacio.signalAll();
        mutex.unlock();
        
    }
    
    
    private void complex_producir(int id_hebra){
        
        while(true){

            mutex.lock();
            while (contador== this.getSize()) {
                try{
                    lleno.await();
                }catch(Exception ex){ }
            }

            buffer[entrada_P] = 1;

            System.out.println(text3 + "HebraProductora(" + id_hebra + ") prduce en posición \"" + entrada_P + "\"" + restaurar);
            imprimirBuffer_cambios(entrada_P, true);
            
            entrada_P++;
            entrada_P = entrada_P % getSize();
            contador ++;

            vacio.signalAll();
            mutex.unlock();
        }
        
    }    
    
    
    
    public void select_consumir(boolean termina, int id_hebra){
        
        if(termina){
            simple_consumir(id_hebra); //Just one lap
        }else{
            complex_consumir(id_hebra); //All laps it can
        }
        
    }
    
    private void simple_consumir(int id_hebra){
        
            mutex.lock();
            while (contador==0) {
                    try{
                        vacio.await();
                    }catch(Exception ex){ }        
            }

            buffer[entrada_C] = 0;

            System.out.println(text3 + "HebraConsumidora(" + id_hebra + ") consume en posición \"" + entrada_P + "\"" + restaurar);
            imprimirBuffer_cambios(entrada_C, false);

            entrada_C++;
            entrada_C = entrada_C % getSize();
            contador --;
            
            lleno.signalAll();
            mutex.unlock();

    }
    
    
    private void complex_consumir(int id_hebra){
        
        while(true){

            mutex.lock();
            while (contador==0) {
                    try{
                        vacio.await();
                    }catch(Exception ex){ }        
            }

            buffer[entrada_C] = 0;

            System.out.println(text3 + "HebraConsumidora(" + id_hebra + ") consume en posición \"" + entrada_P + "\"" + restaurar);
            imprimirBuffer_cambios(entrada_C, false);

            entrada_C++;
            entrada_C = entrada_C % getSize();
            contador --;
            
            lleno.signalAll();
            mutex.unlock();

        }
        
    }
    
    
    void imprimirBuffer_cambios(int cambio, boolean producido){

        if(producido){
            System.out.print("Actual Buffer: ");
                for(int i = 0; i < this.getSize(); i++){

                    if(i == cambio){
                        System.out.print(added + buffer[i] + " " + restaurar);
                    }else{
                        System.out.print(buffer[i] + " ");
                    }
                }
        }else{
            System.out.print("Actual Buffer: ");
                for(int i = 0; i < this.getSize(); i++){

                    if(i == cambio){
                        System.out.print(eliminated + buffer[i] + " " + restaurar);
                    }else{
                        System.out.print(buffer[i] + " ");
                    }
                }
        }
        System.out.print("\n\n");

    }
    
}
