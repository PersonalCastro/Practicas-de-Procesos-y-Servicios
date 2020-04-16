/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Interface.Controller;

import Server_Interface.Controller.UserController;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author PersonalCastro
 */
public class Server_CM_Protocol {

    private UserController controladorDelUsuario;
    private MessageController controladorDeLosMensajes;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String chatLogin;
    private String chatLoginFriend;
    private String chatDate;
    
    private ArrayList<String> photoLines;
    private String login_photo;
        
    public Server_CM_Protocol(UserController controladorDelUsuariod, MessageController controladorDeLosMensajes) {
        
        this.controladorDelUsuario = controladorDelUsuariod;
        this.controladorDeLosMensajes = controladorDeLosMensajes;
        
        this.photoLines = new ArrayList();
        this.login_photo = new String();

    }
    
    

    public String processInput(String theInput) {
        String theOutput = new String();
        

        if (theInput != null){
            if(checkCM_message(theInput)){
                
                String auxDate = sdf.format(new Timestamp(System.currentTimeMillis()));

                
                theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
//FECHA Y HORA
                
                theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                if(checkCM_client(theInput)){
//CLIENTE
                    theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                    

                    if(checkCM_Login(theInput)){
                    //LOGIN
                        
                        theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                        String login = theInput.substring(0, theInput.indexOf("#"));
                        
                        theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                        String password = theInput;

                        if(!this.controladorDelUsuario.comprobarConexionBD_socket(login) && controladorDelUsuario.logingCristoMessenger(login, password)){
                            
                            theOutput = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#SERVER#LOGIN_CORRECT" + "#" + login +  controladorDelUsuario.getFriendList(login);
                            controladorDelUsuario.connectUserInDB(login);
                            
                        }else{
                            theOutput = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#SERVER#ERROR#BAD_LOGIN";
                        }
                        
                    }else if(checkCM_ConnectionUser(theInput)){
                    //ConnectedFriend
                    
                        theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                        String login = theInput.substring(0, theInput.indexOf("#"));
                        
                        theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                        String friendLogin = theInput;
                                                
                        theOutput = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#SERVER#STATUS#"+friendLogin+"#";
                        
                        if(this.controladorDelUsuario.comprobarConexionBD_socket(friendLogin)){
                            theOutput += "CONNECTED";
                        }else{
                            theOutput += "NOT_CONNECTED";
                        }
                        
                    }else if (checkCM_SendMsgToFriend(theInput)){
                    //SEND_MSG
                    
                        theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                        String login = theInput.substring(0, theInput.indexOf("#"));
                        
                        theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                        String friendLogin = theInput.substring(0, theInput.indexOf("#"));
                        
                        theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                        String newMessage = theInput;
                    
                        String getFriends = this.controladorDelUsuario.getFriendList(login);
                        
                        if(getFriends.contains(friendLogin)){
                            if(!newMessage.contains("_admin_")){
                                this.controladorDeLosMensajes.addnewMessage(login,friendLogin,newMessage, auxDate);
                                theOutput = "CHAT_TO#"+login+"#"+friendLogin+"#"+auxDate+"#"+newMessage;
                            }else{
                                theOutput = "CHAT_TO#"+login+"#"+friendLogin+"#"+auxDate+"#"+newMessage;
                            }

                        }else{
                            theOutput = friendLogin+"PROTOCOLCRISTOMESSENGER1.0#FECHA/HORA#SERVER#FORBIDDEN_CHAT";
                        }
                        
                    
                    }else if(checkCM_SearchConversationFriend(theInput)){
                    //MESSAGES
                    
                        if(theInput.contains("#OK_SEND!")){
                            
                            theOutput += this.controladorDeLosMensajes.getDayMessages(this.chatLogin,this.chatLoginFriend, this.chatDate);
                            theOutput += "#@#OK_SEND!";

                        }else if (!theInput.contains("#ALL_RECEIVED")){
                            theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                            this.chatLogin = theInput.substring(0, theInput.indexOf("#"));
                            theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                            this.chatLoginFriend = theInput.substring(0, theInput.indexOf("#"));
                            theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                            this.chatDate = theInput;

                            if(true){
                                theOutput = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#SERVER#MSGS#"+this.chatLogin+"#"+this.chatLoginFriend+"#"+this.controladorDeLosMensajes.getAllRestCountMessages(this.chatLogin, this.chatLoginFriend, this.chatDate)+"#" + this.controladorDeLosMensajes.getDayCountMessages(this.chatLogin,this.chatLoginFriend, this.chatDate);

                            }else{
                                theOutput = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#SERVER#BAD_MSGPKG";
                            }
                        }
                        
                    }else if(checkCM_AllInformationUser(theInput)){
                    //INFO
                    
                        theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                        String login = theInput;   
                        
                        theOutput = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#SERVER#ALLDATA_USER" + "#" + login + controladorDelUsuario.getUserInformation(login);
                        
                    }else if(checkCM_photoUser(theInput)){
                    //PHOTO
                    
                        theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                        login_photo = theInput;        
                        
                        this.photoLines.clear();
                        photoLines = controladorDelUsuario.getUserUrlPhoto(login_photo);
                        theOutput = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#SERVER#STARTING_MULTIMEDIA_TRANSMISSION_TO" + "#" + login_photo;

                    }
                    
                    
                    
                    
                    
                    
                    
                    


                    
                    
                }else{
//SERVIDOR?¿?¿?¿?¿
                }
            }
        }
        
        return theOutput;
    }    
    
    private boolean checkCM_message(String theInput){
        
        boolean correctMessage = false;
        String subStringInput = new String();
        
        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));

            if(subStringInput.equals("PROTOCOLCRISTOMESSENGER1.0")){
                correctMessage = true;
            }
        }

        return correctMessage;
    }

    private boolean checkCM_client(String theInput) {
        boolean clientMessage = false;
        String subStringInput = new String();


        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));

            if(subStringInput.equals("CLIENT")){
                clientMessage = true;
            }
        }
        
        return clientMessage;
    }

    private boolean checkCM_Login(String theInput) {
        boolean loginProtocol = false;
        String subStringInput = new String();


        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));

            if(subStringInput.equals("LOGIN")){
                loginProtocol = true;
            }
        }
        
        return loginProtocol;    
    }
    
    private boolean checkCM_AllInformationUser(String theInput) {
        boolean allDataProtocol = false;
        String subStringInput = new String();


        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));

            if(subStringInput.equals("ALLDATA_USER")){
                allDataProtocol = true;
            }
        }
        
        return allDataProtocol;    
    }

    private boolean checkCM_ConnectionUser(String theInput) {

        boolean frindConnectedProtocol = false;
        String subStringInput = new String();


        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));

            if(subStringInput.equals("STATUS")){
                frindConnectedProtocol = true;
            }
        }
        return frindConnectedProtocol;
    }
    
    private boolean checkCM_SearchConversationFriend(String theInput) {

        boolean frindConnectedProtocol = false;
        String subStringInput = new String();


        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));

            if(subStringInput.equals("MSGS")){
                frindConnectedProtocol = true;
            }
        }
        return frindConnectedProtocol;
    }
    
    private boolean checkCM_photoUser(String theInput) {

        boolean frindConnectedProtocol = false;
        String subStringInput = new String();


        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));

            if(subStringInput.equals("GET_PHOTO")){
                frindConnectedProtocol = true;
            }
        }
        return frindConnectedProtocol;
    }
    
    private boolean checkCM_SendMsgToFriend(String theInput){
        
        boolean frindConnectedProtocol = false;
        String subStringInput = new String();

        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));

            if(subStringInput.equals("CHAT")){
                frindConnectedProtocol = true;
            }
        }
        return frindConnectedProtocol;
    }
    
    public ArrayList<String> getPhotoLines(){
        return this.photoLines;
    }
    
    public String toCadenaPhotoProtocol(String cadenaPhoto, float totalBytes, float sizeBytes){
        String auxDate = sdf.format(new Timestamp(System.currentTimeMillis()));
        return "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#SERVER#RESPONSE_MULTIMEDIA#"+this.login_photo+"#"+(int)totalBytes+"#"+(int)sizeBytes+"#"+cadenaPhoto;
    }
 
    public String toCadenaPhotoProtocol_finish(){
        String auxDate = sdf.format(new Timestamp(System.currentTimeMillis()));
        return "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#SERVER#ENDING_MULTIMEDIA_TRANSMISSION#"+this.login_photo;
    }
    
    public String getTimeStamp(){
        return sdf.format(new Timestamp(System.currentTimeMillis()));
    }
    
}
