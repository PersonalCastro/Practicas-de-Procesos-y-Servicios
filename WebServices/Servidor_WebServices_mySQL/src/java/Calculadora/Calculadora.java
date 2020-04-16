/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculadora;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author PersonalCastro
 */
@WebService(serviceName = "Calculadora")
public class Calculadora {


    /**
     * Web service operation
     */
    @WebMethod(operationName = "Sumar")
    public String Sumar(@WebParam(name = "firstNumber") int firstNumber, @WebParam(name = "secondNumber") int secondNumber) {

        return String.valueOf(firstNumber + secondNumber);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Restar")
    public String Restar(@WebParam(name = "firstNumber") int firstNumber, @WebParam(name = "secondNumber") int secondNumber) {

        return String.valueOf(firstNumber - secondNumber);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Dividir")
    public String Dividir(@WebParam(name = "firstNumber") int firstNumber, @WebParam(name = "secondNumber") int secondNumber) {

        return String.valueOf(firstNumber / secondNumber);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Multiplicar")
    public String Multiplicar(@WebParam(name = "firstNumber") int firstNumber, @WebParam(name = "secondNumber") int secondNumber) {

        return String.valueOf(firstNumber * secondNumber);
    }

}
