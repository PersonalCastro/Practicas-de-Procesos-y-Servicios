/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Message;
import Model.User;
import View.ProgramFrame;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author PersonalCastro
 */
public class ClickFriend_thread extends Thread {
    
    private String userLogin;
    private String loginFriend;
    private ProgramFrame view;
    private UserController_cliente userController;
    private MessageController_cliente messageController;
    private ClientSocket cliente;
    
    public ClickFriend_thread(String userLogin, String loginFriend, ProgramFrame view, UserController_cliente userController, MessageController_cliente messageController, ClientSocket cliente){
        
        this.userLogin = userLogin;
        this.loginFriend = loginFriend;
        this.view = view;
        this.userController = userController;
        this.messageController = messageController;
        this.cliente = cliente;
        
    }


    public void run() {

        try{
            //vista
            User friendAuxUser = new User();
            this.userController.createAllInformationUser( this.cliente.getAllInformationUser(loginFriend), friendAuxUser);
            view.jLabelFriendName.setText(friendAuxUser.getName() + " - " + friendAuxUser.getSurname1() +  " - " + friendAuxUser.getSurname2());
            
            //conexión
            this.userController.posibleStateFrienChange( this.cliente.getFriendConnectionInformation(this.userLogin, loginFriend) , view.friends.get(view.jListFriends.getSelectedIndex()) );
            
            view.mainUserFriendsListActualization();

            //mensajes
            view.conversation.clear();
            this.messageController.getMessagesToconversationArray(this.cliente.getFriendConversation(this.userLogin, loginFriend), view.conversation);

            //System.out.println("Mensages: " + view.cliente.getFriendConversation(userLogin.getLogin(), loginFriend));

            ArrayList<Integer> auxNewMensages = new ArrayList();
            for(int i = 0; i < view.friends.size(); i++){

                if(view.friends.get(i).getLogin().endsWith(view.loginFriend)){
                    auxNewMensages.add(0);
                }else{
                    auxNewMensages.add(view.newMessages.get(i));
                }
            }

            view.newMessages = auxNewMensages;
            view.mainUserFriendsListActualization();
            
            if(!view.conversation.isEmpty()){
                Collections.reverse(view.conversation);
            }
            view.jTextArea_chat.setText("");
            for(Message aux : view.conversation){
                String message = new String();

                if(aux.isRead()){
                    message = aux.getId_user_orig() + " (" + aux.getDatetime() + ") _ \t" + aux.getText() + "\t☑";
                }else{
                    message = aux.getId_user_orig() + " (" + aux.getDatetime() + ") _ \t" + aux.getText() + "\t☐";
                }

                view.jTextArea_chat.append(message + "\n");
            }
            view.jTextArea_newMessage.setEditable(true);

            
            File aux = new File("photo_login/Friend.jpg");
            if(aux.exists()){
                System.out.println("(Debug) Eliminando imagen no necesaria, amigo");
                aux.delete();
            }
            
            
            User friendAux = new User();
            this.userController.generatePhotoFriend(cliente.getPhotoUser(loginFriend), friendAux);

                    
            File tryFile = new File("photo_login/Friend.jpg");

            if(tryFile.exists()){
                rsscalelabel.RSScaleLabel.setScaleLabel(view.jLabelFriendPicture, "photo_login/Friend.jpg");
            }else{
                rsscalelabel.RSScaleLabel.setScaleLabel(view.jLabelFriendPicture, "img/Logo_Messenger_Mal.png");
            }


        }catch(Exception e){
            System.out.println("Excepcion : " + e);
        }  

    }
    
}
