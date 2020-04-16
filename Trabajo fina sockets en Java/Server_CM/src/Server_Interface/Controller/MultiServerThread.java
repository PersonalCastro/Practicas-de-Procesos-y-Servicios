/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Interface.Controller;

import Server_Interface.View.VistaServidor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author PersonalCastro
 */
public class MultiServerThread extends Thread {
    private Socket socket = null;
    public boolean stopThread;
    private PrintWriter out;
    private BufferedReader in;
    
    private Server_CM_Protocol protocol;
    private UserController controladorDeUsuarios;
    private MessageController controladorDeLosMensajes;
    private MultiServer server;
    
    public String loginThread = "";
   
    private Lock mutex;

    
    public MultiServerThread() {
        super("KKMultiServerThread");
        this.mutex = new ReentrantLock();

    }
    
    public MultiServerThread(MultiServer server,Socket socket, String ip_bd) {
        super("KKMultiServerThread");
        this.socket = socket;
        this.server = server;
        controladorDeUsuarios = new UserController(ip_bd, this.server);
        controladorDeLosMensajes = new MessageController(ip_bd);
        this.protocol = new Server_CM_Protocol(controladorDeUsuarios, controladorDeLosMensajes);
        this.mutex = new ReentrantLock();

        stopThread = false;

    }
    
    public String getLoginThread(){
        return loginThread;
    }
    
    public void run() {


        VistaServidor.jTextArea_debug.append("(" + socket.getLocalPort() +  ") Connection from: " + socket.getInetAddress().getHostAddress() + "\n");

        
        try (
            PrintWriter auxOut = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inAux = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        ) {
            out = auxOut;
            in = inAux;
            String inputLine, outputLine;
            
            inputLine = new String();
            outputLine = new String();
            
            //out.println("");

            while ((inputLine = in.readLine()) != null && !stopThread) {

                this.mutex.lock();

                VistaServidor.jTextArea_debug.append("(" + socket.getLocalPort() +  ") Message from: " + socket.getInetAddress().getHostAddress() + " : " + inputLine + "\n");
                outputLine = protocol.processInput(inputLine);

                                
                if(outputLine.contains("OK_SEND!")){

                    outputLine = outputLine.substring(0,outputLine.indexOf("#@#OK_SEND!"));
                    
                    VistaServidor.jTextArea_debug.append("(" + socket.getLocalPort() +  ") Sending all Messages to: " + socket.getInetAddress().getHostAddress() + "\n");
                    
                    boolean noMoreMessages = false;
                    do{
                        
                        out.println(outputLine.substring(0,outputLine.indexOf("|")));

                        VistaServidor.jTextArea_debug.append("\t Message: " + outputLine.substring(0,outputLine.indexOf("|")) + "\n");

                        outputLine = outputLine.substring(outputLine.indexOf("|") + 1, outputLine.length());
                        //System.out.println("outputLine - " +  outputLine);
                        
                        if(!outputLine.contains("|")){
                            noMoreMessages = true;
                        }
                        
                    }while(!noMoreMessages);
                    
                }else if(outputLine.contains("CHAT_TO")){

                    outputLine = outputLine.substring(outputLine.indexOf("#") + 1,outputLine.length());
                    String login = outputLine.substring(0, outputLine.indexOf("#"));
                    outputLine = outputLine.substring(outputLine.indexOf("#") + 1,outputLine.length());
                    String friendLogin = outputLine.substring(0, outputLine.indexOf("#"));
                    outputLine = outputLine.substring(outputLine.indexOf("#") + 1,outputLine.length());
                    String date = outputLine.substring(0, outputLine.indexOf("#"));
                    outputLine = outputLine.substring(outputLine.indexOf("#") + 1,outputLine.length());
                    String newMessage = outputLine;
                    
                    for(MultiServerThread auxThread: server.getMultiServerThreads()){
                        if(auxThread.loginThread.equals(friendLogin)){
                            auxThread.reciveChat(login, friendLogin, date, newMessage);
                        }
                    }
                                        
                    outputLine = "PROTOCOLCRISTOMESSENGER1.0#"+this.protocol.getTimeStamp()+"#SERVER#CHAT#"+login+"#"+friendLogin+"#MESSAGE_SUCCESFULLY_PROCESSED#"+date;
                    out.println(outputLine);
                    VistaServidor.jTextArea_debug.append("(" + socket.getLocalPort() +  ") Friend Message from: " + socket.getInetAddress().getHostAddress() + " : " + outputLine + "\n");
                        
                }else if(outputLine.contains("#STARTING_MULTIMEDIA_TRANSMISSION_TO")){
                
                    out.println(outputLine);
                    VistaServidor.jTextArea_debug.append("(" + socket.getLocalPort() +  ") Message to: " + socket.getInetAddress().getHostAddress() + " : " + outputLine + "\n");
                    
                    float photoBytes = this.controladorDeUsuarios.countPhotoBytes(this.protocol.getPhotoLines());
                    
                    ArrayList<String> auxLines = this.protocol.getPhotoLines();
                    
                    for(int i = 0; i < auxLines.size(); i++){
                        if(i == auxLines.size()-1){
                            outputLine = this.protocol.toCadenaPhotoProtocol(auxLines.get(i), photoBytes, auxLines.get(i).length());
                            out.println(outputLine);
                            
                            VistaServidor.jTextArea_debug.append("(" + socket.getLocalPort() +  ") Message to: " + socket.getInetAddress().getHostAddress() + " : " + outputLine + "\n");
                            outputLine = this.protocol.toCadenaPhotoProtocol_finish();
                            out.println(outputLine);
                            VistaServidor.jTextArea_debug.append("(" + socket.getLocalPort() +  ") Message to: " + socket.getInetAddress().getHostAddress() + " : " + outputLine + "\n");
                        }else{
                            outputLine = this.protocol.toCadenaPhotoProtocol(auxLines.get(i), photoBytes, 512);
                            out.println(outputLine);
                            VistaServidor.jTextArea_debug.append("(" + socket.getLocalPort() +  ") Message to: " + socket.getInetAddress().getHostAddress() + " : " + outputLine + "\n");
                        }
                    }
                    
                }else if (!inputLine.contains("#ALL_RECEIVED") && !inputLine.contains("#PHOTO_RECEIVED")){
                    out.println(outputLine);
                    VistaServidor.jTextArea_debug.append("(" + socket.getLocalPort() +  ") -Message to: " + socket.getInetAddress().getHostAddress() + " : " + outputLine + "\n");

                }
                
                
                
                if(outputLine.contains("PROTOCOLCRISTOMESSENGER1.0") && outputLine.contains("SERVER") && outputLine.contains("LOGIN_CORRECT")){
                    outputLine = outputLine.substring(outputLine.indexOf("#") + 1,outputLine.length());
                    outputLine = outputLine.substring(outputLine.indexOf("#") + 1,outputLine.length());
                    outputLine = outputLine.substring(outputLine.indexOf("#") + 1,outputLine.length());
                    outputLine = outputLine.substring(outputLine.indexOf("#") + 1,outputLine.length());

                    loginThread = outputLine.substring(0, outputLine.indexOf("#"));
                    VistaServidor.jTextArea_debug.append("(" + socket.getLocalPort() +  ") - " + socket.getInetAddress().getHostAddress() + " Thread Login: " + this.loginThread + "\n");

                }
                
                if(outputLine.contains("PROTOCOLCRISTOMESSENGER1.0") && outputLine.contains("SERVER") && outputLine.contains("ERROR") && outputLine.contains("BAD_LOGIN")){
                    break;
                }
                
                this.mutex.unlock();
                
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                //System.out.println("");
                socket.close();
                server.finishThisConnexion(this.loginThread);
                controladorDeUsuarios.disconnectUserInDB(loginThread);
                server.debugActualConnections();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void reciveChat(String login, String loginFriend, String date, String newMessage){

        this.mutex.lock();
        String fromClient = new String();
        String outputLine = "PROTOCOLCRISTOMESSENGER1.0#"+this.protocol.getTimeStamp()+"#SERVER#CHAT#"+login+"#"+loginFriend+"#"+newMessage+"#"+date;
        VistaServidor.jTextArea_debug.append("(" + socket.getLocalPort() +  ") Message_CHAT to: " + socket.getInetAddress().getHostAddress() + " : " + outputLine + "\n");
        out.println(outputLine);

        try{
            /*while ((fromClient = in.readLine()) != null) {
                System.out.println("(Debug) get from client: " + fromClient);
                if (fromClient.contains("PROTOCOLCRISTOMESSENGER1.0") && fromClient.contains("#CLIENT") && fromClient.contains("#RECEIVED_MESSAGE"))
                    break;
            }*/
        }catch(Exception e){
            System.out.println(e);
        }

        this.mutex.unlock();
    }

    
}
