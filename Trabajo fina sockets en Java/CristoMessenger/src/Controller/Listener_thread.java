/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ProgramFrame;
import java.util.ArrayList;

/**
 *
 * @author PersonalCastro
 */
public class Listener_thread  extends Thread {
    
    private ProgramFrame view;
    private MessageController_cliente messageController;
    private ClientSocket cliente;
    
    public Listener_thread(ProgramFrame view, MessageController_cliente messageController, ClientSocket cliente){
        
        this.view = view;
        this.messageController = messageController;
        this.cliente = cliente;
        
    }
    

    public void run() {
    
        String fromServer = new String();
        
        while(true){
            try{
                fromServer = cliente.getPosibleChat();
                
            }catch(Exception e){
                System.out.println(e);
            }
            
            if (fromServer.contains("PROTOCOLCRISTOMESSENGER1.0") && fromServer.contains("#SERVER") && fromServer.contains("#CHAT") && !fromServer.contains("#MESSAGE_SUCCESFULLY_PROCESSED")){
                
                String loginFriendAux = this.cliente.getFriendLoginFromMessageSuccesfullyProcessed(fromServer);
                
                String loginFriend_View = new String();

                
                if((loginFriend_View = view.loginFriend) != null && loginFriend_View.equals(loginFriendAux)){
                    view.jTextArea_chat.append(cliente.processGetMessage_Chat(fromServer));
                    cliente.recivedPosibleChat(cliente.processGetUserOrig_Chat(fromServer));
                    fromServer = "";
                }else{
                    ArrayList<Integer> auxNewMensages = new ArrayList();
                    for(int i = 0; i < view.friends.size(); i++){
                        
                        if(view.friends.get(i).getLogin().endsWith(loginFriendAux)){
                            auxNewMensages.add(view.newMessages.get(i) + 1);
                        }else{
                            auxNewMensages.add(view.newMessages.get(i));
                        }
                        
                    }

                    view.newMessages = auxNewMensages;
                    view.mainUserFriendsListActualization();
                }
                
            }

        }
        
    }
    
    
}
