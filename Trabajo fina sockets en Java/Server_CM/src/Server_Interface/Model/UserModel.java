/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Interface.Model;

import Server_Interface.View.VistaServidor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author PersonalCastro
 */
public class UserModel extends MySqlGenericConnector {  
    
    private ResultSet rs;
    private Statement stmt;
    private String ip_bd;
    
    private String allTableQuery;
    private String loginAndPasswordQuery;
    private String loginQuery;
    
    private String mainUserDataQueryWhere;
    private String mainFriendListQueryWhere;
    
    
    
    public UserModel(String ip_bd){
        super(ip_bd);
        this.ip_bd = ip_bd;
                
        this.allTableQuery = "Select * From ";
        this.loginAndPasswordQuery = "Select id_user, password From ";
        this.loginQuery = "Select id_user From ";
        
        this.mainUserDataQueryWhere = "Where id_user = \" ";
        this.mainFriendListQueryWhere = "Where id_user_orig = \"";
    }

    public String getAllTableQuery() {
        return allTableQuery;
    }
    
    public String getLoginAndPasswordQuery() {
        return loginAndPasswordQuery;
    }
    
    public String getLoginQuery() {
        return loginQuery;
    }

    public void setAllTableQuery(String table) {
        this.allTableQuery = "Select * From " + this.getTable();
    }

    public void setLoginAndPasswordQuery(String table) {
        this.loginAndPasswordQuery = "Select id_user, password From " + this.getTable();
    }

    public void setLoginQuery(String table) {
        this.loginQuery = "Select id_user From " + this.getTable();
    }
    
        
    public String getMainUserDataQueryWhere() {
        return mainUserDataQueryWhere;
    }    
    
    public void setMainUserDataQueryWhere(String id_user) {
    
           this.mainUserDataQueryWhere = " Where id_user = \"" + id_user + "\""; 
    }
    
    public String getMainFriendListQueryWhere() {
        return mainFriendListQueryWhere;
    }
    
    public void setMainFriendListQueryWhere(String id_user_orig) {
    
           this.mainFriendListQueryWhere = " Where id_user_orig = \"" + id_user_orig + "\""; 
    }
    
    
    public ArrayList<User> getLoginAndPassword() throws SQLException{
    
        this.setTable("User");
        this.setLoginAndPasswordQuery(this.getTable());
        stmt = this.conn.createStatement();
        ArrayList<User> auxLogin = new ArrayList();
        
        try {
            this.rs = this.stmt.executeQuery(this.getLoginAndPasswordQuery());
            
            while (rs.next()) {
                auxLogin.add(new User(this.rs.getString("id_user"), this.rs.getString("password")));
            }
            
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
        return auxLogin;
    }
    
    /*public ArrayList<User> getLogin() throws SQLException{
    
        this.setTable("User");
        this.setLoginQuery(this.getTable());
        
        stmt = this.conn.createStatement();
        ArrayList<User> auxLogin = new ArrayList();
        
        try {
            this.rs = this.stmt.executeQuery(this.getLoginQuery());
            
            while (rs.next()) {
                auxLogin.add(new User(this.rs.getString("id_user")));
            }
            
        } catch (SQLException ex ) {
            
            ProgramFrame.addDebugStringToDebugSection(String.valueOf(ex));
            
        } finally {
            ProgramFrame.addDebugStringToDebugSection(String.valueOf("Statement successfully Generated\n"));

            if (stmt != null) { 
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ProgramFrame.addDebugStringToDebugSection(String.valueOf(ex));
                    
                    //Logger.getLogger( "(classname)" .class.getName()).log(Level.SEVERE, null, ex);
                } finally{
                    ProgramFrame.addDebugStringToDebugSection(String.valueOf("Statement successfully colsed \n"));
                }
            }
        }
        return auxLogin;
    } */

    /*void insertNewUserInDB(String username, String password, String name, String fSName, String sSName) throws SQLException {
        
        this.setTable("User");
        this.setAllTableQuery(this.getTable());
        stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet insert = stmt.executeQuery(this.getAllTableQuery());

        try {

            insert.moveToInsertRow();
            insert.updateString("id_user", username);
            insert.updateString("password", password);
            insert.updateString("name", name);
            insert.updateString("surname1", fSName);
            insert.updateString("surname2", sSName);
            insert.updateString("photo", null);
            insert.updateInt("state", 0);


            insert.insertRow();
            insert.beforeFirst();
        } catch (SQLException ex){
                ProgramFrame.addDebugStringToDebugSection(String.valueOf(ex));
        } finally{
                ProgramFrame.addDebugStringToDebugSection(String.valueOf("Statement successfully Generated \n"));
            if (stmt != null) { 

                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ProgramFrame.addDebugStringToDebugSection(String.valueOf(ex));

                    //Logger.getLogger( "(classname)" .class.getName()).log(Level.SEVERE, null, ex);
                } finally{
                    ProgramFrame.addDebugStringToDebugSection(String.valueOf("Statement successfully colsed \n"));
                }
            }
        }
    }*/

    public void getAllMainUserData_lessPassword(User userAux, String login) throws SQLException{

        this.setTable("User");
        this.setAllTableQuery(this.getTable());
        this.setMainUserDataQueryWhere(login);
        String auxQuery = this.getAllTableQuery() + this.getMainUserDataQueryWhere();
        
        stmt = this.conn.createStatement();
        
        try {
            this.rs = this.stmt.executeQuery(auxQuery);
            
            while (rs.next()) {
                
                userAux.setLogin(this.rs.getString("id_user"));
                userAux.setName(this.rs.getString("name"));
                userAux.setSurname1(this.rs.getString("surname1"));
                userAux.setSurname2(this.rs.getString("surname2"));
                userAux.setState(this.rs.getBoolean("state"));
                //System.out.println("this.rs.getBoolean(\"state\")-------: " + this.rs.getBoolean("state"));
            }
            
        } catch (SQLException ex ) {
            VistaServidor.jTextArea_debug.append("Fail on get Friends info on: " + this.ip_bd + "\n");
            
        } finally {
            VistaServidor.jTextArea_debug.append("Friends info correctly search on login: " + login + "\n");
            if (stmt != null) { 
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Exception on closeStatement \"getAllMainUserData_lessPassword()\"");                    
                    
                    //Logger.getLogger( "(classname)" .class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    //System.out.println("Statement successfully closed \"getAllMainUserData_lessPassword()\"");                    
                }
            }
        }        
    }

    public void getAllfriends(ArrayList<User> friends, String login) throws SQLException {

        this.setTable("friend");
        this.setAllTableQuery(this.getTable());
        this.setMainFriendListQueryWhere(login);
        
        String auxQuery = this.getAllTableQuery() + this.getMainFriendListQueryWhere();
            
        stmt = this.conn.createStatement();
        try {

            this.rs = this.stmt.executeQuery(auxQuery);
            while (rs.next()) {
                
                if(this.rs.getInt("accept_request") == 1){
                    friends.add(new User(this.rs.getString("id_user_dest")));

                }
                
            }
            
        } catch (SQLException ex ) {
            VistaServidor.jTextArea_debug.append("Fail on search Friends on: " + this.ip_bd + "\n");
            
        }finally {
            VistaServidor.jTextArea_debug.append("Friends correctly search on login: " + login + "\n");
            if (stmt != null) { 
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Exception on closeStatement \"getAllfriends()\"");                    
                    //Logger.getLogger( "(classname)" .class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    //System.out.println("Statement successfully closed \"getAllfriends()\"");                    
                }
            }
        }
    }

    public String getMainUserInformation(String login) throws SQLException {
        
        this.setTable("User");
        this.setAllTableQuery(this.getTable());
        this.setMainUserDataQueryWhere(login);
        String auxQuery = this.getAllTableQuery() + this.getMainUserDataQueryWhere();
        String outputDB = new String();
        
        stmt = this.conn.createStatement();
        
        try {
            this.rs = this.stmt.executeQuery(auxQuery);
            
            while (rs.next()) {
                
                outputDB += "#" + this.rs.getString("name");
                outputDB += "#" + this.rs.getString("surname1");
                outputDB += "#" + this.rs.getString("surname2");

            }
            
        } catch (SQLException ex ) {
            VistaServidor.jTextArea_debug.append("Fail on get Friends info on: " + this.ip_bd + "\n");
            
        } finally {
            VistaServidor.jTextArea_debug.append("Friends info correctly search on login: " + login + "\n");
            if (stmt != null) { 
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Exception on closeStatement \"getAllMainUserData_lessPassword()\"");                    
                    
                    //Logger.getLogger( "(classname)" .class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    //System.out.println("Statement successfully closed \"getAllMainUserData_lessPassword()\"");                    
                }
            }
        }           

        return outputDB;

    }

    public void turnConnectedLonginStateIntance(String login){

        String update = "UPDATE User SET state = '1' WHERE id_user = '" + login + "'";
        //System.out.println("update : " + update);
        try {
            stmt = this.conn.createStatement();
            this.stmt.executeUpdate(update);
            
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
    }
    
    public void turnDisconnectedLonginStateIntance(String login){

        String update = "UPDATE User SET state = '0' WHERE id_user = '" + login + "'";
        //System.out.println("update : " + update);
        try {
            stmt = this.conn.createStatement();
            this.stmt.executeUpdate(update);
            
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
    }

    public boolean getUserState(String friendLogin) {
        boolean conectionOpen = false;
        String query = "Select state From user WHERE id_user = '" + friendLogin + "'";
        //System.out.println("update : " + query);
        try {
            stmt = this.conn.createStatement();
            rs = this.stmt.executeQuery(query);
            
            while (rs.next()) {
                
                conectionOpen = this.rs.getBoolean("state");
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
        return conectionOpen;
    }

    public String getUserPhoto(String login) throws SQLException {

        String query = "Select photo From user WHERE id_user = '" + login + "'";
        String outputDB = new String();
        
        stmt = this.conn.createStatement();
        
        try {
            this.rs = this.stmt.executeQuery(query);
            
            rs.next();
            outputDB = this.rs.getString("photo");
            
        } catch (SQLException ex ) {
            VistaServidor.jTextArea_debug.append("Fail on get User Photo on: " + this.ip_bd + "\n");
            
        } finally {
            VistaServidor.jTextArea_debug.append("Friends Photo correctly search on this login: " + login + "\n");
            if (stmt != null) { 
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Exception on closeStatement \"getUserPhoto()\"");                    
                    
                    //Logger.getLogger( "(classname)" .class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    //System.out.println("Statement successfully closed \"getAllMainUserData_lessPassword()\"");                    
                }
            }
        }           

        return outputDB;
    }
    
    
    
    
    
    
    
}
