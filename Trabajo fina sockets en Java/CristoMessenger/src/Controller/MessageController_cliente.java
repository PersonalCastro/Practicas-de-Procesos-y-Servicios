/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Message;
import java.util.ArrayList;

/**
 *
 * @author PersonalCastro
 */
public class MessageController_cliente {

    public void getMessagesToconversationArray(String friendConversation, ArrayList<Message> conversation) {

        conversation.clear();
        System.out.println("friend:" + friendConversation);
        while (friendConversation != "" && friendConversation != null && friendConversation.length() > 1) {
            friendConversation = friendConversation.substring(friendConversation.indexOf("#") + 1, friendConversation.length());
            friendConversation = friendConversation.substring(friendConversation.indexOf("#") + 1, friendConversation.length());
            friendConversation = friendConversation.substring(friendConversation.indexOf("#") + 1, friendConversation.length());
            friendConversation = friendConversation.substring(friendConversation.indexOf("#") + 1, friendConversation.length());
            
            String aux_idOrig = friendConversation.substring(0,friendConversation.indexOf("#"));
            friendConversation = friendConversation.substring(friendConversation.indexOf("#") + 1, friendConversation.length());
            //System.out.println("aux_idOrig: " + aux_idOrig);
                    
            String aux_idDest = friendConversation.substring(0,friendConversation.indexOf("#"));
            friendConversation = friendConversation.substring(friendConversation.indexOf("#") + 1, friendConversation.length());
            //System.out.println("aux_idDest: " + aux_idDest);
            
            String datetime = friendConversation.substring(0,friendConversation.indexOf("#"));
            friendConversation = friendConversation.substring(friendConversation.indexOf("#") + 1, friendConversation.length());
            //System.out.println("datetime: " + datetime);            
            
            String text = friendConversation.substring(0,friendConversation.indexOf("|"));
            if(friendConversation.substring(friendConversation.indexOf("|"), friendConversation.length()).length() <= 2){
                friendConversation = "";
            }else{
                friendConversation = friendConversation.substring(friendConversation.indexOf("|") + 1, friendConversation.length());   
            }
            //System.out.println("text: " + text);            
                        
            conversation.add(new Message(aux_idOrig, aux_idDest, datetime, true, true, text));
            
            //System.out.println("au" + friendConversation);

        }
        
    }
    
    
    
}
