/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Interface.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author PersonalCastro
 */
public class MessageModel extends MySqlGenericConnector {
    
    private ResultSet rs;
    private Statement stmt;
    private String ip_bd;
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public MessageModel(String ip_bd) {
        super(ip_bd);
        this.ip_bd = ip_bd;
    }
    
    //mensajes
    //Select * From message Where ((id_user_orig='1' And id_user_dest='4') Or (id_user_orig='4' And id_user_dest='1')) And (datetime Between '2020-02-21' And '2020-02-22');
    
    //nºmensages
    //Select count(*) AS rowcount From message Where ((id_user_orig='1' And id_user_dest='4') Or (id_user_orig='4' And id_user_dest='1')) And (datetime Between '2020-02-21' And '2020-02-22');

    
    
    public int getAllRestCountMessages(String loginUser, String loginFriend, String date) throws SQLException{
                
        stmt = this.conn.createStatement();
        
        String auxDate = new String();
        try{
            
            Date dateAux = (Date) sdf.parse(date);
            auxDate = sdf.format(new Timestamp((dateAux.getTime() + 84600000 + 3600000)));

        }catch(Exception e){
            
        }
        
        String query = "Select count(*) AS rowcount From message Where ((id_user_orig='"+loginUser+"' And id_user_dest='"+loginFriend+"') Or (id_user_orig='"+loginFriend+"' And id_user_dest='"+loginUser+"')) And datetime<'"+auxDate+"'";
        int countMessages = 0;
        
        //System.out.println("query1: " + query);
        try {
            this.rs = this.stmt.executeQuery(query);
            
            rs.next();
            countMessages = rs.getInt("rowcount") ;
            
        } catch (SQLException e ) {
            
            System.out.println("Error:" + e);
        } finally {
            if (stmt != null) { 
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Error:" + ex);
                    
                    //Logger.getLogger( "(classname)" .class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return countMessages;
    }
    
    
    public int getDayCountMessages(String loginUser, String loginFriend, String date) throws SQLException{
        
        stmt = this.conn.createStatement();
        String auxDate = new String();
        try{
            Date dateAux = (Date) sdf.parse(date);
            auxDate = sdf.format(new Timestamp((dateAux.getTime() + 84600000 + 3600000)));

            
            
        }catch(Exception e){
            
        }
        
        String query = "Select count(*) AS rowcount From message Where ((id_user_orig='"+loginUser+"' And id_user_dest='"+loginFriend+"') Or (id_user_orig='"+loginFriend+"' And id_user_dest='"+loginUser+"')) And (datetime Between '"+date+"' And '"+auxDate+"')";
        //System.out.println("cosos: "+date+ "-" +auxDate+ "-" + query);
        //System.out.println("query2: " + query);
        int countMessages = 0;
        
        try {
            this.rs = this.stmt.executeQuery(query);
            
            rs.next();
            countMessages = rs.getInt("rowcount") ;
            
        } catch (SQLException e ) {
            
            System.out.println("Error:" + e);
        } finally {
            if (stmt != null) { 
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Error:" + ex);
                    
                    //Logger.getLogger( "(classname)" .class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return countMessages;
    }

    public String getDayMessages(String loginUser, String loginFriend, String date)  throws SQLException{
        
        stmt = this.conn.createStatement();
        String auxDate = new String();
        
        String actualDate = new String();
        
        try{
            Date dateAux = (Date) sdf.parse(date);
            auxDate = sdf.format(new Timestamp((dateAux.getTime() + 84600000 + 3600000)));

            actualDate = sdf.format(new Timestamp(System.currentTimeMillis()));
            
        }catch(Exception e){
            
        }
        
        String query = "Select * From message Where ((id_user_orig='"+loginUser+"' And id_user_dest='"+loginFriend+"') Or (id_user_orig='"+loginFriend+"' And id_user_dest='"+loginUser+"')) And (datetime Between '"+date+"' And '"+auxDate+"') ORDER BY datetime DESC";
        String update = "UPDATE message SET read_msg = '1' WHERE id_user_orig = '" + loginFriend + "' AND id_user_dest = '" + loginUser + "'";       
        String allMessages = new String();

        //System.out.println("query3: " + query);
        //System.out.println("update :" + update);
        
        try {
            this.stmt.executeUpdate(update);
            this.rs = this.stmt.executeQuery(query);
            while (rs.next()) {
                allMessages += "PROTOCOLCRISTOMESSENGER1.0#"+actualDate+"#SERVER#MSGS";
                allMessages += "#" + rs.getString("id_user_orig");
                allMessages += "#" + rs.getString("id_user_dest");
                allMessages += "#" + rs.getString("datetime");
                allMessages += "#" + rs.getString("text");
                allMessages += "|";
            }
            
        } catch (SQLException e ) {
            
            System.out.println("Error:" + e);
        } finally {
            if (stmt != null) { 
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Error:" + ex);
                }
            }
        }
        return allMessages;
    }

    public void addnewMessage(String login, String friendLogin, String newMessage, String actualDate)  throws SQLException{

        stmt = this.conn.createStatement();
       
        String query = "Insert Into message (id_user_orig, id_user_dest, datetime, read_msg, sent, text) Values ('"+login+"','"+friendLogin+"','"+actualDate+"','"+0+"','"+1+"','"+newMessage+"')";
        
        try {
            this.stmt.executeUpdate(query);
            
        } catch (SQLException e ) {
            
            System.out.println("Error:" + e);
        } finally {
            if (stmt != null) { 
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Error:" + ex);
                    
                    //Logger.getLogger( "(classname)" .class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
    
}
