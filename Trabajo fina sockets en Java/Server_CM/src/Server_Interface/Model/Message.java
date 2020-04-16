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
public class Message {
    
    private String id_user_orig;
    private String id_user_dest;
    private String date;
    private String hour;
    private boolean read;
    private boolean sent;
    private String text;

    public Message() {
    }

    public Message(String id_user_orig, String id_user_dest, String date, String hour, boolean read, boolean sent, String text) {
        this.id_user_orig = id_user_orig;
        this.id_user_dest = id_user_dest;
        this.date = date;
        this.hour = hour;
        this.read = read;
        this.sent = sent;
        this.text = text;
    }

    public void setId_user_orig(String id_user_orig) {
        this.id_user_orig = id_user_orig;
    }

    public void setId_user_dest(String id_user_dest) {
        this.id_user_dest = id_user_dest;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId_user_orig() {
        return id_user_orig;
    }

    public String getId_user_dest() {
        return id_user_dest;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isSent() {
        return sent;
    }

    public String getText() {
        return text;
    }
       
}
