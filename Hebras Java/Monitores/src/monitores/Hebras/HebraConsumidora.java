/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitores.Hebras;

import monitores.Buffer;

/**
 *
 * @author PersonalCastro
 */
public class HebraConsumidora implements Runnable{
    
public Thread thr;
    private int id;
    private boolean terminado;
    private static Buffer bf;
    
    public HebraConsumidora(int id, boolean terminado, Buffer bf){
                        
        this.setId(id);
        this.setTerminado(terminado);
        this.bf = bf;
        
        this.thr = new Thread(this);
        
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public int getId() {
        return id;
    }

    public boolean isTerminado() {
        return terminado;
    }

    @Override
    public void run() {

        try{
            
            bf.select_consumir(this.isTerminado() ,this.getId());    
            
        }catch(Exception ex){
            System.out.println("Ocurrio un error al consumir: " + ex);
        }
    }
}
