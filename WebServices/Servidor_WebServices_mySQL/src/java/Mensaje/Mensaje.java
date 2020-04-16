/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mensaje;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author PersonalCastro
 */
@WebService(serviceName = "Mensaje")
public class Mensaje {


    /**
     * Web service operation
     */
    @WebMethod(operationName = "mensaje")
    public String mensaje(@WebParam(name = "correo") String correo, @WebParam(name = "asunto") String asunto, @WebParam(name = "mensaje") String mensaje) {
        SendEmailTLS sendMensajeObject = new SendEmailTLS();
        return sendMensajeObject.sendEmail(correo, asunto, mensaje);
    }
}
