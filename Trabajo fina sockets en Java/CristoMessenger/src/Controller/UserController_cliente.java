/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.User;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

/**
 *
 * @author PersonalCastro
 */
public class UserController_cliente {
        
    public UserController_cliente(){
    
    }
    
    
    public boolean logingCristoMessenger(String serverReply, ArrayList<User> posibleFriends) {
    
        boolean loginAccepted = false;
        ArrayList<User> auxLogin = new ArrayList();
        
        if(serverReply.contains("LOGIN_CORRECT")){
            loginAccepted = true;
        }
        
        
        if(serverReply.contains("FRIENDS#")){
            getFriendList(posibleFriends, serverReply);
        }
        
        return loginAccepted;
    }
    
    public boolean checkFullDataInSingUp(String username, Color cUsername, String password, Color cPassword, String rPassword, Color cRPassword, String name, Color cName, String fSName, Color cFSName, String sSName, Color cSSName){
    
        boolean allDataNeededwrited = true;
        
        if(username.equals("") || cUsername.equals(Color.red)){
            allDataNeededwrited = false;
            
        }else if (password.equals("") || cPassword.equals(Color.red)){
            allDataNeededwrited = false;
            
        }else if (rPassword.equals("") || cRPassword.equals(Color.red)){
            allDataNeededwrited = false;

        }else if (name.equals("") || cName.equals(Color.red)){
            allDataNeededwrited = false;
            
        }else if (fSName.equals("") || cFSName.equals(Color.red)){
            allDataNeededwrited = false;
            
        }else if (sSName.equals("") || cSSName.equals(Color.red)){
            allDataNeededwrited = false;
            
        }
        
        return allDataNeededwrited;
    }
    
    public boolean checkRepeatedPassword(String password, String repeatPassword) {
        
        boolean rightRepeatedPassword = false;
        
        if(password.equals(repeatPassword)){
            rightRepeatedPassword = true;
        }
        return rightRepeatedPassword;
    }

    /*public boolean checkUniqueLogin(String newLogin) {

        boolean uniqueLogin = true;
        
        ArrayList<User> auxUniqueLogin = new ArrayList();
        
        try {
            auxUniqueLogin = userModel.getLogin();
        } catch (SQLException ex) {
            ProgramFrame.addDebugStringToDebugSection(String.valueOf(ex));
        } finally {
            ProgramFrame.addDebugStringToDebugSection(String.valueOf("UserModel.getLoginAndPassword(); finished \n"));
        }
        
        for(User user: auxUniqueLogin) {
        
            if (user.getLogin().equals(newLogin)){
                uniqueLogin = false;
            }
        }
        
        return uniqueLogin;
    }*/
    
    /*public void insertNewUser(String username, String password, String name, String fSName, String sSName) {
    
        try {
            userModel.insertNewUserInDB(username, password, name, fSName, sSName);
        } catch (SQLException ex) {
            ProgramFrame.addDebugStringToDebugSection(String.valueOf(ex));
        } finally {
            ProgramFrame.addDebugStringToDebugSection(String.valueOf("UserModel.insertNewUserInDB(); finished \n"));
        }
    
    }*/

    public void allMainUserData(User userAux, String login) {
        
        userAux.setLogin(login);        
        /*try {
            userModel.getAllMainUserData_lessPassword(userAux, login);
        } catch (SQLException ex) {
            ProgramFrame.addDebugStringToDebugSection(String.valueOf(ex));
        } finally {
            ProgramFrame.addDebugStringToDebugSection(String.valueOf("UserModel.allMainUserData(); finished \n"));
        }*/
    }

    private void getFriendList(ArrayList<User> friends, String serverReply) {
        
        
        serverReply = serverReply.substring(serverReply.indexOf("FRIENDS") + 8, serverReply.length());

        int cantidadAmigos = Integer.valueOf(serverReply.substring(0, serverReply.indexOf("#")));
        
        String [] auxFriends = serverReply.split("#", (cantidadAmigos * 2) + 1);
        
        int iterator = 1;
        for (int i = 0; i < cantidadAmigos; i++){
            boolean auxState = false;
            if(auxFriends[iterator+1].equals("CONNECTED")){
                auxState = true;
            }else if(auxFriends[iterator+1].equals("NOT_CONNECTED")){
                auxState = false;
            }
            
            friends.add(new User(auxFriends[iterator], auxState));
            iterator = iterator + 2;
      
        }
    }
    
    
    
    public void createAllInformationUser(String Server_loginReply, User aux){
    
        
        aux.setLogin(Server_loginReply.substring(0, Server_loginReply.indexOf("#")));
        Server_loginReply = Server_loginReply.substring(Server_loginReply.indexOf("#") + 1,Server_loginReply.length());
                
        aux.setName(Server_loginReply.substring(0, Server_loginReply.indexOf("#")));
        Server_loginReply = Server_loginReply.substring(Server_loginReply.indexOf("#") + 1,Server_loginReply.length());
        
        aux.setSurname1(Server_loginReply.substring(0, Server_loginReply.indexOf("#")));
        Server_loginReply = Server_loginReply.substring(Server_loginReply.indexOf("#") + 1,Server_loginReply.length());
        
        aux.setSurname2(Server_loginReply);
        
    }

    public void posibleStateFrienChange(String processConnectionUser, User friend) {

        //System.out.println("processConnectionUser: " + processConnectionUser);
        if(processConnectionUser.contains("NOT_CONNECTED")){
            friend.setState(false);
        }else if(processConnectionUser.contains("CONNECTED")){
            friend.setState(true);
        }
    }

    public void generatePhotoUser(ArrayList<String> photoUser, User aux_loginUser) {
        ArrayList<String> decodeLines = new ArrayList<String>();
        
        for (String s : photoUser) {
            decodeLines.add(new String(Base64.getDecoder().decode(s)));
        }
      
        try{
            File file = new File("photo_login/Login.jpg");
            
            if(file.exists()){
                file.delete();
            }
            
            file.createNewFile();
            try (FileOutputStream fos = new FileOutputStream(file)) {
                for (String s : decodeLines) {
                    for (char c : s.toCharArray()) {
                        fos.write(c);
                    }
                }
                fos.flush();
                fos.close();
            }
            
            aux_loginUser.setPhoto(file);
        } catch (IOException ex) {
            //Logger.getLogger(ReadEncodeFile_DecodeWriteFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void generatePhotoFriend(ArrayList<String> photoUser, User aux_loginUser) {
        ArrayList<String> decodeLines = new ArrayList<String>();
        
        for (String s : photoUser) {
            decodeLines.add(new String(Base64.getDecoder().decode(s)));
        }
      
        try{
            File file = new File("photo_login/Friend.jpg");
            
            if(file.exists()){
                file.delete();
            }
            
            file.createNewFile();
            try (FileOutputStream fos = new FileOutputStream(file)) {
                for (String s : decodeLines) {
                    for (char c : s.toCharArray()) {
                        fos.write(c);
                    }
                }
                fos.flush();
                fos.close();
            }
            
            aux_loginUser.setPhoto(file);
        } catch (IOException ex) {
            //Logger.getLogger(ReadEncodeFile_DecodeWriteFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
