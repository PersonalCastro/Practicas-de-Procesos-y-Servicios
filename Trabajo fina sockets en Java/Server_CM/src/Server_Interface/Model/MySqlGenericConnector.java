/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Interface.Model;

import Server_Interface.View.VistaServidor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author PersonalCastro
 */
public class MySqlGenericConnector {
        
    private String sgbdUserName;
    private String sgbdPassword;
    
    private String dbName;
    private String sgdbIp;
    private String sgbdName;
    private String sgbdPort;
    
    protected Connection conn;
    protected String table;
    
    
    public MySqlGenericConnector(String ip_bd) {
        //System.out.println("Debug: BdConnector(): def.");
        this.setSgbdUserName("clasedam2"); //clasedam2 (Carlos)
        this.setSgbdPassword("root"); //root (Carlos)
        this.setDbName("cristomessenger"); //cristomessenger (Carlos)
        this.setSgdbIp(ip_bd);  //"clasedam2.ddns.net"
        this.setSgbdName("mysql");
        this.setSgbdPort("3306");
        
        this.connectToSgbd();
    }

    public void setSgbdUserName(String sgbdUserName) {
        this.sgbdUserName = sgbdUserName;
    }

    public void setSgbdPassword(String sgbdPassword) {
        this.sgbdPassword = sgbdPassword;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setSgdbIp(String sgdbIp) {
        this.sgdbIp = sgdbIp;
    }

    public void setSgbdName(String sgbdName) {
        this.sgbdName = sgbdName;
    }

    public void setSgbdPort(String sgbdPort) {
        this.sgbdPort = sgbdPort;
    }
    
    public String getSgbdUserName(){
        //System.out.println("Debug: getSgbdUserName()");
        return sgbdUserName;
    }
    
    public String getSgbdPassword(){
        //System.out.println("Debug: getSgbdPassword()");
        return sgbdPassword;
    }
    
    public String getDbName(){
        //System.out.println("Debug: getDbName()");
        return dbName;
    }
    
    public String getSgdbIp() {
        //System.out.println("Debug: getSgdbIp()");
        return sgdbIp;
    }

    public String getSgbdName() {
        //System.out.println("Debug: getSgbdName()");
        return sgbdName;
    }

    public String getSgbdPort() {
        //System.out.println("Debug: getSgbdPort()");
        return sgbdPort;
    }    
    
    public void connectToSgbd(){
        //System.out.println("Debug: connectToSgbd()");
        try{
            this.conn = DriverManager.getConnection("jdbc:" + this.getSgbdName() + "://" + this.getSgdbIp() + ":" + this.getSgbdPort() + "/" + this.getDbName(),this.getSgbdUserName(), this.getSgbdPassword());
            VistaServidor.jTextArea_debug.append("Conexionde la BD establecida sobre la dirección: " + this.getSgdbIp() + "\n");
        } catch(SQLException e){
            System.out.println("Error:" + e);
        }
    }  

    public String getTable() {
        //System.out.println("Debug: getTable()");
        return table;
    }

    public void setTable(String table) {
        //System.out.println("Debug: setTable()");
        this.table = table;
    }
}
