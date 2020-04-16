/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.User;
import View.LoginFrame;
import View.ProgramFrame;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author PersonalCastro
 */
public class Login_thread extends Thread {
    
    private ClientSocket cliente;
    private UserController_cliente userController;
    private LoginFrame acceso_Vista;
    private ArrayList<User> posibleFriends;
    
    private String login;
    private String password;

    
    public Login_thread(ClientSocket cliente_aux, UserController_cliente userController, LoginFrame acceso_Vista, String login, String password){
        
        this.cliente = cliente_aux;
        this.userController = userController;
        this.acceso_Vista = acceso_Vista;
        this.posibleFriends = new ArrayList();
        
        this.login = login;
        this.password = password;
    }
    
    public void run() {
        
        acceso_Vista.loginAccepting = true;
        
        if(acceso_Vista.userNameTxtField.getForeground().equals(Color.LIGHT_GRAY)){
            //error
        }else if (acceso_Vista.passwordTxtField.getForeground().equals(Color.LIGHT_GRAY)){
            //error
        }else{
             boolean loginAccepted = false;

            try{
                cliente.connectWithServerSocket();
                loginAccepted = userController.logingCristoMessenger(cliente.checkLogin(login, password), this.posibleFriends);
            }catch(Exception e){
                System.out.println("fail: " + e);
            }

            if(loginAccepted == true) {
                
                ProgramFrame programFrame = new ProgramFrame(login, cliente, userController, this.posibleFriends);
                programFrame.setVisible(true);

                acceso_Vista.closeWindow();
            }
        }
        acceso_Vista.passwordTxtField.setText("");
        acceso_Vista.passwordTxtField.setText("Password");
        acceso_Vista.passwordTxtField.setForeground(Color.LIGHT_GRAY);
        
        acceso_Vista.loginAccepting = false;
    }    
}
