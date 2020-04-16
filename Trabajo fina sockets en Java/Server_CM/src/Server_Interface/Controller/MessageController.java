/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Interface.Controller;

import Server_Interface.Model.MessageModel;


/**
 *
 * @author PersonalCastro
 */
public class MessageController {
    
    private MessageModel messageModel;
        
    public MessageController(String ip_bd){
    
        this.messageModel = new MessageModel(ip_bd);
    }
    
    public int getAllRestCountMessages(String loginUser, String loginFriend, String chatDate){
        try{
            return this.messageModel.getAllRestCountMessages(loginUser, loginFriend, chatDate);
        }catch( Exception e){
            System.out.println("Excepcion: " + e);
            return 0;
        }
    }
    
    public int getDayCountMessages(String loginUser, String loginFriend, String date){
        try{
            return this.messageModel.getDayCountMessages(loginUser, loginFriend, date);
        }catch( Exception e){
            System.out.println("Excepcion: " + e);
            return 0;
        }
    }

    public String getDayMessages(String loginUser, String loginFriend, String date) {
        try{
            return this.messageModel.getDayMessages(loginUser, loginFriend, date);
        }catch( Exception e){
            System.out.println("Excepcion: " + e);
            return "";
        }
    }

    public void addnewMessage(String login, String friendLogin, String newMessage, String actualDate) {
        try{
            this.messageModel.addnewMessage(login, friendLogin, newMessage, actualDate);
        }catch( Exception e){
            System.out.println("Excepcion: " + e);
        }
    }
    
}
