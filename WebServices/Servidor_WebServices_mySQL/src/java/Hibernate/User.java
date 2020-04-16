package Hibernate;
// Generated 05-mar-2020 13:53:03 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * User generated by hbm2java
 */
public class User  implements java.io.Serializable {


     private String idUser;
     private String name;
     private String password;
     private String surname1;
     private String surname2;
     private String photo;
     private Integer state;
     private Set messagesForIdUserOrig = new HashSet(0);
     private Set friendsForIdUserOrig = new HashSet(0);
     private Set messagesForIdUserDest = new HashSet(0);
     private Set friendsForIdUserDest = new HashSet(0);

    public User() {
    }

	
    public User(String idUser) {
        this.idUser = idUser;
    }
    public User(String idUser, String name, String password, String surname1, String surname2, String photo, Integer state, Set messagesForIdUserOrig, Set friendsForIdUserOrig, Set messagesForIdUserDest, Set friendsForIdUserDest) {
       this.idUser = idUser;
       this.name = name;
       this.password = password;
       this.surname1 = surname1;
       this.surname2 = surname2;
       this.photo = photo;
       this.state = state;
       this.messagesForIdUserOrig = messagesForIdUserOrig;
       this.friendsForIdUserOrig = friendsForIdUserOrig;
       this.messagesForIdUserDest = messagesForIdUserDest;
       this.friendsForIdUserDest = friendsForIdUserDest;
    }
   
    public String getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSurname1() {
        return this.surname1;
    }
    
    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }
    public String getSurname2() {
        return this.surname2;
    }
    
    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }
    public String getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public Integer getState() {
        return this.state;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }
    public Set getMessagesForIdUserOrig() {
        return this.messagesForIdUserOrig;
    }
    
    public void setMessagesForIdUserOrig(Set messagesForIdUserOrig) {
        this.messagesForIdUserOrig = messagesForIdUserOrig;
    }
    public Set getFriendsForIdUserOrig() {
        return this.friendsForIdUserOrig;
    }
    
    public void setFriendsForIdUserOrig(Set friendsForIdUserOrig) {
        this.friendsForIdUserOrig = friendsForIdUserOrig;
    }
    public Set getMessagesForIdUserDest() {
        return this.messagesForIdUserDest;
    }
    
    public void setMessagesForIdUserDest(Set messagesForIdUserDest) {
        this.messagesForIdUserDest = messagesForIdUserDest;
    }
    public Set getFriendsForIdUserDest() {
        return this.friendsForIdUserDest;
    }
    
    public void setFriendsForIdUserDest(Set friendsForIdUserDest) {
        this.friendsForIdUserDest = friendsForIdUserDest;
    }




}


