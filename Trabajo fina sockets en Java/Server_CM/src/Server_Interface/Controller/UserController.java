/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Interface.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import Server_Interface.Model.UserModel;
import Server_Interface.Model.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

/**
 *
 * @author PersonalCastro
 */
public class UserController {
    
    private UserModel userModel;
    private MultiServer server;

    
    public UserController(String ip_bd, MultiServer server){
    
        this.userModel = new UserModel(ip_bd);
        this.server = server;
    }
    
    
    public boolean logingCristoMessenger(String login, String password) {
    
        boolean loginAccepted = false;
        ArrayList<User> auxLogin = new ArrayList();
        
        try {
            auxLogin = userModel.getLoginAndPassword();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        for(User user : auxLogin){
        
            if(user.getLogin().equals(login) && user.getPassword().equals(password)){
                loginAccepted = true;
            }
        }
        
        return loginAccepted;
    }
    


    public void allMainUserData(User userAux, String login) {
        
        try {
            userModel.getAllMainUserData_lessPassword(userAux, login);
        } catch (SQLException ex) {
            System.out.println("Exception on \"allMainUserData() _ Controller\"");                    
        } finally {
            //System.out.println("Successfully completed \"allMainUserData() _ Controller\"");                    
        }
    }
    

    public String getFriendList(String login) {

        ArrayList<User> auxLogins = new ArrayList();
        String friends = "#FRIENDS";
        
        try {
            userModel.getAllfriends(auxLogins, login);
            
            if(!auxLogins.isEmpty()){
                
                friends += "#" + auxLogins.size();
            
                for(User auxLogin: auxLogins){
                    User friend = new User();
                    this.allMainUserData(friend , auxLogin.getLogin());
                    
                    friends += "#" + friend.getLogin();
                    
                    if(friend.isState()){
                        friends += "#CONNECTED";
                    }else{
                        friends += "#NOT_CONNECTED";
                    }
                    
                }
            }
        } catch (SQLException ex) {
            System.out.println("Exception on \"getFriendList() _ Controller\"");                    

        } finally {
                        
            //System.out.println("Successfully completed \"getFriendList() _ Controller\"");                    

        }      
        
        return friends;
    }

    public String getUserInformation(String login) {
        String outputDB = new String();
        try {
            outputDB = userModel.getMainUserInformation(login);
        } catch (SQLException ex) {
            System.out.println("Exception on \"allMainUserData() _ Controller\"");                    
        } finally {
            //System.out.println("Successfully completed \"allMainUserData() _ Controller\"");                    
        }
        
        return outputDB;
    }

    public void connectUserInDB(String login) {
        this.userModel.turnConnectedLonginStateIntance(login);
    }
    
    public void disconnectUserInDB(String login) {
        this.userModel.turnDisconnectedLonginStateIntance(login);
    }

    public boolean comprobarConexionBD_socket(String friendLogin) {

        boolean socketOpen = false;
        boolean conectionOpen = false;
        
        for(MultiServerThread auxThread :this.server.getMultiServerThreads()){
            if(friendLogin.equals(auxThread.getLoginThread())){
                socketOpen = true;
            }
        }
        
        conectionOpen = this.userModel.getUserState(friendLogin);

        if(socketOpen != conectionOpen){
            if(socketOpen){
                this.userModel.turnConnectedLonginStateIntance(friendLogin);
            }else{
                this.userModel.turnDisconnectedLonginStateIntance(friendLogin);
            }
        }else{

        }
        
        return socketOpen;
    }

    public ArrayList<String> getUserUrlPhoto(String login){


        String line = "";
        ArrayList<String> arrayLines = new ArrayList<String>();
        ArrayList<String> encodeLines = new ArrayList<String>();
        ArrayList<String> decodeLines = new ArrayList<String>();

        //Lectura de fichero a ArrayList de String len 512chars
        try(FileInputStream input = new FileInputStream(/*"C:\\Users\\PersonalCastro\\Desktop\\helix.jpg"*/ this.userModel.getUserPhoto(login))){

            int valor_numerico_char = 0;
            int i = 0;

            while((valor_numerico_char = input.read()) != -1){

                if (i == 512) {
                    arrayLines.add(line);
                    line = "";
                    i = 0;
                }

                line += (char)valor_numerico_char;
                i++;
            }

            if (i > 0) {
                arrayLines.add(line);
                i = 0;
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } catch (SQLException ex) {
        }

        for (String s : arrayLines) {
            encodeLines.add(Base64.getEncoder().encodeToString(s.getBytes()));
        }            
        
        return encodeLines;
    }
    
    
    public float countPhotoBytes(ArrayList<String> photoLines){
        
        float photoBytes = 0;
        
        for(int i = 0; i < photoLines.size(); i++){
            if(i == photoLines.size()-1){
                photoBytes += photoLines.get(i).length();
            }else{
                photoBytes += 512;
            }
        }
        
        return photoBytes;
    }
    
}
