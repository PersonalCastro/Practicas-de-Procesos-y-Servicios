/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.User;
import View.ProgramFrame;

/**
 *
 * @author PersonalCastro
 */
public class FriendState_thread extends Thread {
    
    private ProgramFrame view;
    
    private ClientSocket cliente;
    private UserController_cliente userController;

    public FriendState_thread(ProgramFrame view, ClientSocket cliente, UserController_cliente userController){
        
        this.view = view;
        this.cliente = cliente;
        this.userController = userController;        
    }
    
    public void run() {
        
        while(true){
            try{
                for(User auxFriend: view.friends){
                    this.userController.posibleStateFrienChange(this.cliente.getFriendConnectionInformation(this.view.userLogin.getLogin(),auxFriend.getLogin()), auxFriend );
                }
                
                view.mainUserFriendsListActualization();
                
                Thread.sleep(20000);

            }catch(Exception e){
                System.out.println("Excepcion");
            }
        }        
    }
    
}
