/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ProgramFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author PersonalCastro
 */
public class ClientSocket {
    
    private String hostNm, portNm;
    
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Lock mutex;
    
    private Client_CM_Protocol protocoloCliente;
    //TimeLimiter timeLimiter = new TimeLimiter();
    
    
    private String fromServer;
    
    private boolean login_response;
    private boolean allInformation_response;
    private boolean connectionInformation;
    private boolean imagenRecived_response;
    private ArrayList<String> photo_dataAllLines;
    private boolean getMessages_response;
    private String allMessages;
    private boolean messageSendedSuccesfuly_response;
    private boolean messages_recivedAndProcessed;
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ClientSocket(String hostN, String portN, Client_CM_Protocol protocoloCliente){
        this.hostNm = hostN;
        this.portNm = portN;
        
        this.protocoloCliente = protocoloCliente;
        
        
        login_response = false;
        allInformation_response = false;
        connectionInformation = false;
        imagenRecived_response = false;
        getMessages_response = false;
        messageSendedSuccesfuly_response = false;
        messages_recivedAndProcessed = false;
        
        this.mutex = new ReentrantLock();
    }
    
    public String getActualDate(){
        String auxDate = new String();
        return auxDate = sdf.format(new Timestamp(System.currentTimeMillis()));
    }
    
    
    public void connectWithServerSocket() throws IOException {
        

        String hostName = hostNm;
        int portNumber = Integer.valueOf(portNm);
        
        try {
            clientSocket = new Socket(hostName, portNumber);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
        

    }
    
    public String checkLogin(String login, String password) throws IOException {
        
        
        String auxDate = sdf.format(new Timestamp(System.currentTimeMillis()));
        String fromUser = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#CLIENT#LOGIN";
        fromServer = "";


        fromUser += "#" + login + "#" + password;

        mutex.lock();
        fromServer = "";
        
        out.println(fromUser);


        while (!login_response && (fromServer = in.readLine()) != null){
            if (fromServer.contains("PROTOCOLCRISTOMESSENGER1.0") && fromServer.contains("#SERVER") && (fromServer.contains("#LOGIN_CORRECT") || fromServer.contains("#BAD_LOGIN")))
                login_response = true;
        }
        login_response = false;

        mutex.unlock();
        
        return protocoloCliente.processInputLogin(fromServer);
        
    }
    
    public String getAllInformationUser(String login) throws IOException {
        
        String auxDate = sdf.format(new Timestamp(System.currentTimeMillis()));
        String fromUser = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#CLIENT#ALLDATA_USER";


        fromUser += "#" + login;

        mutex.lock();
        fromServer = "";
        
        out.println(fromUser);
        ProgramFrame.addDebugStringToDebugSection("Mensaje enviado al servidor: " +fromUser+ "\n");
                       
        while (!allInformation_response) {System.out.print("");}
        allInformation_response = false;
       
        mutex.unlock();

        return this.protocoloCliente.processInputAllInformationUser(fromServer);
        
    }

    public String getFriendConnectionInformation(String login, String friendLogin) throws IOException {
        
        String auxDate = sdf.format(new Timestamp(System.currentTimeMillis()));
        String fromUser = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#CLIENT#STATUS";

        fromUser += "#" + login + "#" + friendLogin;

        mutex.lock();
        fromServer = "";

        out.println(fromUser);    
        ProgramFrame.addDebugStringToDebugSection("Mensaje enviado al servidor: " +fromUser+ "\n");
        
        while (!connectionInformation){System.out.print("");}
        connectionInformation = false;
        
        mutex.unlock();

        return this.protocoloCliente.processConnectionUser(fromServer);
        
    }
    
    public String getFriendConversation(String login, String friendLogin) throws IOException {

        allMessages = new String();
        boolean reciveMessages = false;
        
        String auxDate = new String();
        String fromUser = new String();
        fromServer = new String();
        String fromServer_2 = new String();

        Timestamp timestamp = null;
            
        //int previousDay = 0;
        auxDate = sdf.format(new Timestamp(System.currentTimeMillis()));

        fromServer = "";
   
        do{
            reciveMessages = false;

            //previousDay++;
            fromUser = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#CLIENT#MSGS";
            fromUser += "#" + login + "#" + friendLogin;
            
            
            fromUser += "#" + sdf.format(Timestamp.valueOf(auxDate).getTime() - (24*60*60*1000));
            auxDate = sdf.format(Timestamp.valueOf(auxDate).getTime() - (24*60*60*1000));
            
            //System.out.println("previousDay:" + previousDay);
            
            mutex.lock();
            fromServer = "";
            out.println(fromUser);
            ProgramFrame.addDebugStringToDebugSection("Mensaje enviado al servidor: " +fromUser+ "\n");
            
            while (true) {
                System.out.print("");
                if (fromServer.contains("PROTOCOLCRISTOMESSENGER1.0") && fromServer.contains("#SERVER") && fromServer.contains("#BAD_MSGPKG")){
                    allMessages = fromServer;
                    break;
                }else if(fromServer.contains("PROTOCOLCRISTOMESSENGER1.0") && fromServer.contains("#SERVER") && fromServer.contains("#MSGS")){
                    if( this.protocoloCliente.numberOfMessagesToReadThisDay(fromServer) != 0 ){
                        reciveMessages = true;
                        messages_recivedAndProcessed = false;
                    }
                    break;
                }
            }
            mutex.unlock();
            
            if(reciveMessages){
                /*int numberOfMessagesToReadThisDay = this.protocoloCliente.numberOfMessagesToReadThisDay(fromServer);
                System.out.println("numberOfMessagesToReadThisDay ---> " + numberOfMessagesToReadThisDay);*/
                
                mutex.lock();
                out.println("PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#CLIENT#MSGS#OK_SEND!"); 
                ProgramFrame.addDebugStringToDebugSection("Mensaje enviado al servidor: " +"PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#CLIENT#MSGS#OK_SEND!"+ "\n");
                
                while (!getMessages_response) {System.out.print("");}
                
                getMessages_response = false;
                out.println("PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#CLIENT#MSGS#ALL_RECEIVED"); 
                ProgramFrame.addDebugStringToDebugSection("Mensaje enviado al servidor: " +"PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#CLIENT#MSGS#ALL_RECEIVED"+ "\n");
                mutex.unlock();

            }

        }while(!fromServer.contains("BAD_MSGPKG") && !fromServer.contains("#MSGS#"+login+"#"+friendLogin+"#0#0") && !reciveMessages);

        
        
        while(!messages_recivedAndProcessed){System.out.print("");}
        messages_recivedAndProcessed = false;
        
        return allMessages;
    } 
   
    
    public ArrayList<String> getPhotoUser(String login) throws IOException {
    
        photo_dataAllLines = new ArrayList();
        
                
        String auxDate = sdf.format(new Timestamp(System.currentTimeMillis()));
        String fromUser = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#CLIENT#GET_PHOTO";
        String fromServer = "";
        fromUser += "#" + login;
                        
        mutex.lock();
        fromServer = "";
        out.println(fromUser);
        ProgramFrame.addDebugStringToDebugSection("Mensaje enviado al servidor: " +fromUser+ "\n");

        
        while (!imagenRecived_response) {System.out.print("");}
        imagenRecived_response = false;
        
        
        mutex.unlock();
        
        
        return photo_dataAllLines;
    }

    //ULTIMA TARJETA 
    public String getPosibleChat() throws IOException {
 
        

        if((fromServer = in.readLine()) != null){ 
            ProgramFrame.addDebugStringToDebugSection("Mensaje recivido por el servidor: " +fromServer+ "\n");
            
            if (fromServer.contains("PROTOCOLCRISTOMESSENGER1.0") && fromServer.contains("#SERVER") && (fromServer.contains("#ALLDATA_USER")))
                allInformation_response = true;

            if (fromServer.contains("PROTOCOLCRISTOMESSENGER1.0") && fromServer.contains("#SERVER") && (fromServer.contains("#STATUS")))
                connectionInformation = true;
            
            if (fromServer.contains("PROTOCOLCRISTOMESSENGER1.0") && fromServer.contains("#SERVER") && fromServer.contains("#STARTING_MULTIMEDIA_TRANSMISSION_TO")){
                fromServer = "";
                while ((fromServer = in.readLine()) != null) {
                    if (fromServer.contains("PROTOCOLCRISTOMESSENGER1.0") && fromServer.contains("#SERVER") && fromServer.contains("#ENDING_MULTIMEDIA_TRANSMISSION")){
                        break;
                    }
                    photo_dataAllLines.add(this.protocoloCliente.getPhotoFragment(fromServer));

                }
                imagenRecived_response = true;
            }         
            
            if(fromServer.contains("PROTOCOLCRISTOMESSENGER1.0") && fromServer.contains("#SERVER") && fromServer.contains("#MSGS")){
                
                allMessages = "";
                
                int numberOfMessagesToReadThisDay = this.protocoloCliente.numberOfMessagesToReadThisDay(fromServer);
                
                while (numberOfMessagesToReadThisDay != 0 && (fromServer = in.readLine()) != null) {
                    numberOfMessagesToReadThisDay--;
                    allMessages += fromServer;
                    allMessages += "|";
                }
                getMessages_response = true;
                messages_recivedAndProcessed = true;
                                

            }
            
            if (fromServer.contains("PROTOCOLCRISTOMESSENGER1.0") && fromServer.contains("#SERVER") && fromServer.contains("#MESSAGE_SUCCESFULLY_PROCESSED")){}
            
        }
        

        return fromServer;
    }
    
    public void recivedPosibleChat(String loginOrig){
        mutex.lock();
        out.println("PROTOCOLCRISTOMESSENGER1.0#"+this.protocoloCliente.getTimeStamp()+"#CLIENT#CHAT#RECEIVED_MESSAGE#"+loginOrig+"#"+this.protocoloCliente.getTimeStamp());       
        mutex.unlock();
    }
    
    
    public /*String*/void sendMessage(String login, String friendLogin, String newMessage) throws IOException {

        String auxDate = this.getActualDate();
        String fromUser = "PROTOCOLCRISTOMESSENGER1.0#"+auxDate+"#CLIENT#CHAT";
        String fromServer = "";

        fromUser += "#" + login + "#" + friendLogin + "#" + newMessage;
        //System.out.println("getNewMessage: " + newMessage);

        mutex.lock();
        out.println(fromUser);       
        //ProgramFrame.addDebugStringToDebugSection("Mensaje enviado al servidor: " +fromUser+ "\n");
        mutex.unlock();

        //return this.protocoloCliente.processConnectionUser(fromServer);
        
    }

    public String processGetMessage_Chat(String processImput) {

        return this.protocoloCliente.getMessage_Chat(processImput);
    }
    
    public String processGetUserOrig_Chat(String processImput) {

        return this.protocoloCliente.getUserOrig_Chat(processImput);
    }

    String getFriendLoginFromMessageSuccesfullyProcessed(String fromServer) {        
        
        return this.protocoloCliente.getUserOrig_Chat(fromServer);
    }
    
}
