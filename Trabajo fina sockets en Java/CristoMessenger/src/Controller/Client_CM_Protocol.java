/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;



/**
 *
 * @author PersonalCastro
 */
public class Client_CM_Protocol {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public Client_CM_Protocol() {
        
        
    }
    
    public String processInputLogin(String theInput) {
        String theOutput = new String();

        if (theInput != null){
            if(checkCM_message(theInput)){
                
                theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                
                //System.out.println("Datetime (processInputLogin): " + theInput.substring(0, theInput.indexOf("#")));
                theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());

                if(checkCM_server(theInput)){
                    //SERVIDOR

                    theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());


                    if(checkCM_Login(theInput)){
                        //LOGIN

                        if(theInput.substring(0,theInput.indexOf("#") + 1).equals("ERROR#")){
                            theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                        }
                        
                        theOutput = theInput;

                    }
                }
            }
        }
        
        return theOutput;
    }  
    
    
    public String processInputAllInformationUser(String theInput) {
        String theOutput = new String();

        if (theInput != null){
            if(checkCM_message(theInput)){
                
                theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                
                //System.out.println("Datetime (processInputAllInformationUser): " + theInput.substring(0, theInput.indexOf("#")));
                theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());

                if(checkCM_server(theInput)){
                    //SERVIDOR

                    theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());


                    if(checkCM_AllInformationUser(theInput)){
                        //LOGIN

                        theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());

                        theOutput = theInput;

                    }
                }
            }
        }
        
        return theOutput;
    }
    
    public String processConnectionUser(String theInput) {
        String theOutput = new String();

        if (theInput != null){
            if(checkCM_message(theInput)){
                
                theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());
                
                //System.out.println("Datetime (processConnectionUser): " + theInput.substring(0, theInput.indexOf("#")));
                theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());

                if(checkCM_server(theInput)){
                    //SERVIDOR

                    theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());


                    if(checkCM_ConnectionInformationUser(theInput)){
                        //LOGIN

                        theInput = theInput.substring(theInput.indexOf("#") + 1,theInput.length());

                        theOutput = theInput;

                    }
                }
            }
        }
        
        return theOutput;
    } 
    
    
    
    private boolean checkCM_message(String theInput){
        
        boolean correctMessage = false;
        String subStringInput = new String();
        
        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));

            if(subStringInput.equals("PROTOCOLCRISTOMESSENGER1.0")){
                correctMessage = true;
            }
        }

        return correctMessage;
    }

    private boolean checkCM_server(String theInput) {
        boolean serverMessage = false;
        String subStringInput = new String();


        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));

            if(subStringInput.equals("SERVER")){
            serverMessage = true;
            }
        }
        
        return serverMessage;
    }

    private boolean checkCM_Login(String theInput) {
        boolean loginProtocol = false;
        String subStringInput = new String();


        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));
        }

        if(theInput.equals("LOGIN_CORRECT") || subStringInput.equals("LOGIN_CORRECT") || subStringInput.equals("ERROR")){
            loginProtocol = true;
        }
        
        return loginProtocol;    
    }
    
    private boolean checkCM_AllInformationUser(String theInput) {
        boolean allDataInformationUserProtocol = false;
        String subStringInput = new String();


        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));
        }

        if(theInput.equals("ALLDATA_USER") || subStringInput.equals("ALLDATA_USER")){
            allDataInformationUserProtocol = true;
        }
        
        return allDataInformationUserProtocol;    
    } 
    
    private boolean checkCM_ConnectionInformationUser(String theInput) {
        boolean allDataInformationUserProtocol = false;
        String subStringInput = new String();


        if(theInput.contains("#")){
            subStringInput = theInput.substring(0, theInput.indexOf("#"));
        }

        if(theInput.equals("STATUS") || subStringInput.equals("STATUS")){
            allDataInformationUserProtocol = true;
        }
        
        return allDataInformationUserProtocol;    
    } 
    
    public boolean checkCM_FriendLoginList(String processInput){
        
        boolean hasFriends = true;
        
        processInput = processInput.substring(processInput.indexOf("FRIENDS") + 7, processInput.length());

        if(processInput.length() == 0){
            hasFriends = false;
        }
        
        return hasFriends;
    }

    public int numberOfMessagesToReadThisDay(String fromServer) {
        
        String processInput = fromServer;
        processInput = processInput.substring(processInput.lastIndexOf("#") + 1,processInput.length());     
        
        return Integer.parseInt(processInput);
    }
    
    public String getPhotoFragment(String fromServer){
        
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        //System.out.println("cadena: " + fromServer);
        return fromServer;
    }
    
    public String getTimeStamp(){
        return sdf.format(new Timestamp(System.currentTimeMillis()));
    }
    
    public String getMessage_Chat(String fromServer){
        String result = new String();
        
        String login = "";
        String date = "";
        String newMessage = "";
        
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        login = fromServer.substring(0,fromServer.indexOf("#"));
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        newMessage = fromServer.substring(0,fromServer.indexOf("#"));
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        date = fromServer;
        
        result = login + " (" + date + ")_\t" + newMessage + "\t☑\n";
        return result;
    }
        
    public String getUserOrig_Chat(String fromServer){
        String result = new String();
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        fromServer = fromServer.substring(fromServer.indexOf("#") + 1,fromServer.length());
        result = fromServer.substring(0,fromServer.indexOf("#"));
        return result;
    }

}
