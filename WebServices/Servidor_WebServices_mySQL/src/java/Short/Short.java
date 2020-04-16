/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Short;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author PersonalCastro
 */
@WebService(serviceName = "Short")
public class Short {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "ordenar")
    public String ordenar(@WebParam(name = "n_1") String n_1, @WebParam(name = "n_2") String n_2, @WebParam(name = "n_3") String n_3, @WebParam(name = "n_4") String n_4, @WebParam(name = "n_5") String n_5) {
        String result = new String();
        try{

            Process process = new ProcessBuilder("E:\\Java\\EstanqueWbSrv\\Estanque.exe",n_1,n_2,n_3,n_4,n_5).start();


            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ( (line = reader.readLine()) != null) {
               builder.append(line);
            }
            result = builder.toString();
            
            int waitFlag = process.waitFor(); //wait until proces stop

            
        }catch(Exception e){
            System.out.println(e);
            
        }
        return result;
    }

}
