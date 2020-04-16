/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Interface.Model;

/**
 *
 * @author PersonalCastro
 */
public class User {
    
    private String login;
    private String password;
    private String name;
    private String surname1;
    private String surname2;
    private boolean state;
    private String photo;

    public User(){
        this.setLogin("");
        this.setPassword("");
        this.setName("");
        this.setSurname1("");
        this.setSurname2("");
        this.setState(false);
        this.setPhoto("");
    
    }
    
    public User(String login, String password) {
        this.setLogin(login);
        this.setPassword(password);
    }
    
    public User (String login){
        this.setLogin(login);
    }

    public User(String name, String surname1, String surname2, boolean state) {
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.state = state;
    }
    

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname1() {
        return surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public boolean isState() {
        return state;
    }

    public String getPhoto() {
        return photo;
    }
        
    
    
}
