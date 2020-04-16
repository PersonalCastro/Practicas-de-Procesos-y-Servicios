/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Listener_thread;
import Controller.ClickFriend_thread;
import Controller.ClientSocket;
import Controller.UserController_cliente;
import Controller.FriendState_thread;
import Controller.MessageController_cliente;
import Model.Message;
import Model.User;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.DefaultListModel;

/**
 *
 * @author PersonalCastro
 */
public class ProgramFrame extends javax.swing.JFrame {

    /**
     * Creates new form ProgramFrame
     */
    
    
    private ClientSocket cliente;
    private Lock mutex_view_cleanFriends;
    
    private UserController_cliente userController;
    private MessageController_cliente messageController;
    
    private FriendState_thread friendStateThread;
    private ClickFriend_thread clickFriendThread;
    private Listener_thread chatListener_thread;
    
    public User userLogin;
    public String loginFriend;
    public ArrayList<User> friends;
    public List<Integer> newMessages;
    public ArrayList<Message> conversation;

    
    
    public ProgramFrame(String login, ClientSocket cliente, UserController_cliente userController, ArrayList<User> friends) {
        initComponents();
        ProgramFrame.addDebugStringToDebugSection("Logging successfully completed!\n");
        this.friends = friends;
        this.newMessages = new ArrayList();
        this.conversation = new ArrayList();
        this.userController = userController;
        this.messageController = new MessageController_cliente();
        this.cliente = cliente;
        this.mutex_view_cleanFriends = new ReentrantLock();
        

        rsscalelabel.RSScaleLabel.setScaleLabel(jLabelLogoImage, "img/CristoMessengerIcon.png");
        
        File aux = new File("photo_login/Login.jpg");
        if(aux.exists()){
            System.out.println("(Debug) Eliminando imagen no necesaria");
            aux.delete();
        }
        
        for (User aux_makeCounts: this.friends){
            this.newMessages.add(0);
        }

        this.chatListener_thread = new Listener_thread(this, this.messageController, this.cliente);
        this.chatListener_thread.start();
        
        this.setUpMainUserData(login);
        
        this.friendStateThread = new FriendState_thread(this, this.cliente, this.userController);
        this.friendStateThread.start();

    }
    
    public void setUpMainUserData(String login){
        this.userLogin = new User();
        //System.out.println("a");
        this.userLogin.setLogin(login);
        //System.out.println("b");
        try{

            this.userController.createAllInformationUser(cliente.getAllInformationUser(userLogin.getLogin()), userLogin);
            this.userController.generatePhotoUser(cliente.getPhotoUser(userLogin.getLogin()), userLogin);

        }catch(Exception e){
            System.out.println("fail: " + e);
        }
        
        this.generateMainUserDataViewComponents(userLogin);
    }
    
    private void generateMainUserDataViewComponents(User userAux){
    
        this.jLabelNameUser.setText( userAux.getName() + " " + userAux.getSurname1() + " " + userAux.getSurname2());
        
        File tryFile = new File("photo_login/Login.jpg");
        
        if(tryFile.exists()){
            rsscalelabel.RSScaleLabel.setScaleLabel(jLabelUserImage, "photo_login/Login.jpg");
        }else{
            rsscalelabel.RSScaleLabel.setScaleLabel(jLabelUserImage, "img/Logo_Messenger_Mal.png");
        }
        
        mainUserFriendsListActualization();
       
    }
    
    public void mainUserFriendsListActualization(){
        
        this.mutex_view_cleanFriends.lock();
        if(!this.friends.isEmpty()){
            this.limpiarJlist();
            DefaultListModel lista = (DefaultListModel) jListFriends.getModel();
            
            int i = 0;
            for (User aux: this.friends){
                String countMesages = new String();
                if(newMessages.get(i) == 0){
                    countMesages = "";
                }else{
                    countMesages = " (" +String.valueOf(newMessages.get(i))+ ")";
                }
                i++;              
                
                try{
                    if(aux.isState()){
                        try{
                            jListFriends.setCellRenderer(new ListWithImage("img/Logo_Messenger_Mal.png"));
                            lista.addElement("🔵  " + aux.getLogin() + countMesages);
                        }catch(Exception e){
                            lista.addElement("fallo");
                        }
                    }else{
                        try{
                            jListFriends.setCellRenderer(new ListWithImage("img/Logo_Messenger_Mal.png"));
                            lista.addElement("〇  " + aux.getLogin() + countMesages);
                        }catch(Exception e){
                            lista.addElement("fallo");
                        }
                    }
                }catch(Exception e){
                    lista.addElement("fallo");
                }

            }
        }
        this.mutex_view_cleanFriends.unlock();
    }
    
    private void limpiarJlist(){
        DefaultListModel model = new DefaultListModel();
        this.jListFriends.setModel(model);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jTextField1 = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelChats = new javax.swing.JPanel();
        jPanelActualUserInformation = new javax.swing.JPanel();
        jLabelLogoImage = new javax.swing.JLabel();
        jLabelUserImage = new javax.swing.JLabel();
        jLabelNameUser = new javax.swing.JLabel();
        jPanelActualUserFriends = new javax.swing.JPanel();
        jScrollPaneFriendList = new javax.swing.JScrollPane();
        jListFriends = new javax.swing.JList<>();
        jPanelSearch = new javax.swing.JPanel();
        jTextFieldSearch = new javax.swing.JTextField();
        jPanelFriendsChat = new javax.swing.JPanel();
        jPanelFriendInformation = new javax.swing.JPanel();
        jLabelFriendPicture = new javax.swing.JLabel();
        jLabelFriendName = new javax.swing.JLabel();
        jPanelChat = new javax.swing.JPanel();
        jScrollPanelChat = new javax.swing.JScrollPane();
        jTextArea_chat = new javax.swing.JTextArea();
        jPanelSendMessage = new javax.swing.JPanel();
        jScrollPaneTextMessage = new javax.swing.JScrollPane();
        jTextArea_newMessage = new javax.swing.JTextArea();
        jButtonSendMessage = new javax.swing.JButton();
        jPanelDebug = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        debugJTextArea = new javax.swing.JTextArea();
        jPanelSingUp = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jTextField_FirstSurnameSingUp = new javax.swing.JTextField();
        jTextField_SecondSurnameSingUp = new javax.swing.JTextField();
        jTextField_UsernameSingUp = new javax.swing.JTextField();
        jTextField_PasswordSingUp = new javax.swing.JTextField();
        jTextField_RepeatPasswordSingUp = new javax.swing.JTextField();
        jTextField_NameSingUp = new javax.swing.JTextField();
        jLabel_UsernameSingUp = new javax.swing.JLabel();
        jLabel_PasswordSingUp = new javax.swing.JLabel();
        jLabel_RepeatPasswordSingUp = new javax.swing.JLabel();
        jLabel_NameSingUp = new javax.swing.JLabel();
        jLabel_FirstSurnameSingUp = new javax.swing.JLabel();
        jLabel_SecondSurnameSingUp = new javax.swing.JLabel();
        jButton_SendDataSingUp = new javax.swing.JButton();
        jLabelNewUserCreatedSuccessfully = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelActualUserInformation.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelNameUser.setToolTipText("");

        javax.swing.GroupLayout jPanelActualUserInformationLayout = new javax.swing.GroupLayout(jPanelActualUserInformation);
        jPanelActualUserInformation.setLayout(jPanelActualUserInformationLayout);
        jPanelActualUserInformationLayout.setHorizontalGroup(
            jPanelActualUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActualUserInformationLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabelLogoImage, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelUserImage, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanelActualUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelActualUserInformationLayout.createSequentialGroup()
                    .addContainerGap(676, Short.MAX_VALUE)
                    .addComponent(jLabelNameUser, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(86, 86, 86)))
        );
        jPanelActualUserInformationLayout.setVerticalGroup(
            jPanelActualUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelActualUserInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelActualUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelActualUserInformationLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelUserImage, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelLogoImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanelActualUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelActualUserInformationLayout.createSequentialGroup()
                    .addContainerGap(38, Short.MAX_VALUE)
                    .addComponent(jLabelNameUser, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(19, 19, 19)))
        );

        jPanelActualUserFriends.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jListFriends.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListFriendsMouseClicked(evt);
            }
        });
        jScrollPaneFriendList.setViewportView(jListFriends);

        jTextFieldSearch.setText("Search");
        jTextFieldSearch.setForeground(Color.LIGHT_GRAY);
        jTextFieldSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldSearchFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanelSearchLayout = new javax.swing.GroupLayout(jPanelSearch);
        jPanelSearch.setLayout(jPanelSearchLayout);
        jPanelSearchLayout.setHorizontalGroup(
            jPanelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldSearch)
                .addContainerGap())
        );
        jPanelSearchLayout.setVerticalGroup(
            jPanelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSearchLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelActualUserFriendsLayout = new javax.swing.GroupLayout(jPanelActualUserFriends);
        jPanelActualUserFriends.setLayout(jPanelActualUserFriendsLayout);
        jPanelActualUserFriendsLayout.setHorizontalGroup(
            jPanelActualUserFriendsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneFriendList, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
            .addComponent(jPanelSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelActualUserFriendsLayout.setVerticalGroup(
            jPanelActualUserFriendsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelActualUserFriendsLayout.createSequentialGroup()
                .addComponent(jPanelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneFriendList))
        );

        jPanelFriendsChat.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelFriendName.setAlignmentX(Component.RIGHT_ALIGNMENT);
        jLabelFriendName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelFriendNameMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelFriendInformationLayout = new javax.swing.GroupLayout(jPanelFriendInformation);
        jPanelFriendInformation.setLayout(jPanelFriendInformationLayout);
        jPanelFriendInformationLayout.setHorizontalGroup(
            jPanelFriendInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelFriendInformationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelFriendName, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelFriendPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelFriendInformationLayout.setVerticalGroup(
            jPanelFriendInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFriendInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelFriendPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelFriendInformationLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabelFriendName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTextArea_chat.setEditable(false);
        jTextArea_chat.setColumns(20);
        jTextArea_chat.setRows(5);
        jScrollPanelChat.setViewportView(jTextArea_chat);

        javax.swing.GroupLayout jPanelChatLayout = new javax.swing.GroupLayout(jPanelChat);
        jPanelChat.setLayout(jPanelChatLayout);
        jPanelChatLayout.setHorizontalGroup(
            jPanelChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPanelChat)
        );
        jPanelChatLayout.setVerticalGroup(
            jPanelChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPanelChat, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
        );

        jTextArea_newMessage.setEditable(false);
        jTextArea_newMessage.setColumns(20);
        jTextArea_newMessage.setRows(10);
        jScrollPaneTextMessage.setViewportView(jTextArea_newMessage);

        jButtonSendMessage.setText(">>");
        jButtonSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendMessageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSendMessageLayout = new javax.swing.GroupLayout(jPanelSendMessage);
        jPanelSendMessage.setLayout(jPanelSendMessageLayout);
        jPanelSendMessageLayout.setHorizontalGroup(
            jPanelSendMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSendMessageLayout.createSequentialGroup()
                .addComponent(jScrollPaneTextMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSendMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
        );
        jPanelSendMessageLayout.setVerticalGroup(
            jPanelSendMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneTextMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jButtonSendMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelFriendsChatLayout = new javax.swing.GroupLayout(jPanelFriendsChat);
        jPanelFriendsChat.setLayout(jPanelFriendsChatLayout);
        jPanelFriendsChatLayout.setHorizontalGroup(
            jPanelFriendsChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelFriendsChatLayout.createSequentialGroup()
                .addGroup(jPanelFriendsChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelFriendInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelSendMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
            .addComponent(jPanelChat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelFriendsChatLayout.setVerticalGroup(
            jPanelFriendsChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFriendsChatLayout.createSequentialGroup()
                .addComponent(jPanelFriendInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jPanelSendMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelChatsLayout = new javax.swing.GroupLayout(jPanelChats);
        jPanelChats.setLayout(jPanelChatsLayout);
        jPanelChatsLayout.setHorizontalGroup(
            jPanelChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChatsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelActualUserInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelChatsLayout.createSequentialGroup()
                        .addComponent(jPanelActualUserFriends, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelFriendsChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelChatsLayout.setVerticalGroup(
            jPanelChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChatsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelActualUserInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelActualUserFriends, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelFriendsChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Chats", jPanelChats);

        debugJTextArea.setColumns(20);
        debugJTextArea.setRows(5);
        debugJTextArea.setEditable(false);
        jScrollPane1.setViewportView(debugJTextArea);

        javax.swing.GroupLayout jPanelDebugLayout = new javax.swing.GroupLayout(jPanelDebug);
        jPanelDebug.setLayout(jPanelDebugLayout);
        jPanelDebugLayout.setHorizontalGroup(
            jPanelDebugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDebugLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelDebugLayout.setVerticalGroup(
            jPanelDebugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDebugLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Debug", jPanelDebug);

        jTextField_FirstSurnameSingUp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_FirstSurnameSingUpFocusGained(evt);
            }
        });

        jTextField_SecondSurnameSingUp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_SecondSurnameSingUpFocusGained(evt);
            }
        });

        jTextField_UsernameSingUp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_UsernameSingUpFocusGained(evt);
            }
        });
        jTextField_UsernameSingUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_UsernameSingUpActionPerformed(evt);
            }
        });

        jTextField_PasswordSingUp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_PasswordSingUpFocusGained(evt);
            }
        });

        jTextField_RepeatPasswordSingUp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_RepeatPasswordSingUpFocusGained(evt);
            }
        });

        jTextField_NameSingUp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_NameSingUpFocusGained(evt);
            }
        });

        jLabel_UsernameSingUp.setText("Username");

        jLabel_PasswordSingUp.setText("Password");

        jLabel_RepeatPasswordSingUp.setText("Repeat password");

        jLabel_NameSingUp.setText("Name");

        jLabel_FirstSurnameSingUp.setText("First Surname");

        jLabel_SecondSurnameSingUp.setText("Second Surname");

        jButton_SendDataSingUp.setText("Send Data");
        jButton_SendDataSingUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SendDataSingUpActionPerformed(evt);
            }
        });

        jLabelNewUserCreatedSuccessfully.setForeground(new java.awt.Color(0, 102, 0));
        jLabelNewUserCreatedSuccessfully.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNewUserCreatedSuccessfully.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jLabelNewUserCreatedSuccessfullyFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(234, 234, 234)
                .addComponent(jButton_SendDataSingUp)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(126, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelNewUserCreatedSuccessfully, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_SecondSurnameSingUp, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_SecondSurnameSingUp)
                            .addComponent(jLabel_FirstSurnameSingUp)
                            .addComponent(jLabel_NameSingUp)
                            .addComponent(jLabel_RepeatPasswordSingUp)
                            .addComponent(jLabel_PasswordSingUp)
                            .addComponent(jLabel_UsernameSingUp)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField_UsernameSingUp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField_FirstSurnameSingUp, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                                    .addComponent(jTextField_NameSingUp)
                                    .addComponent(jTextField_RepeatPasswordSingUp)
                                    .addComponent(jTextField_PasswordSingUp))))
                        .addGap(135, 135, 135))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel_UsernameSingUp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_UsernameSingUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel_PasswordSingUp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_PasswordSingUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLabel_RepeatPasswordSingUp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_RepeatPasswordSingUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jLabel_NameSingUp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_NameSingUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel_FirstSurnameSingUp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_FirstSurnameSingUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_SecondSurnameSingUp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_SecondSurnameSingUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(jLabelNewUserCreatedSuccessfully)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_SendDataSingUp)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanelSingUpLayout = new javax.swing.GroupLayout(jPanelSingUp);
        jPanelSingUp.setLayout(jPanelSingUpLayout);
        jPanelSingUpLayout.setHorizontalGroup(
            jPanelSingUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSingUpLayout.createSequentialGroup()
                .addGap(241, 241, 241)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(266, Short.MAX_VALUE))
        );
        jPanelSingUpLayout.setVerticalGroup(
            jPanelSingUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSingUpLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jTabbedPane1.addTab("Sing up", jPanelSingUp);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_UsernameSingUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_UsernameSingUpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_UsernameSingUpActionPerformed

    private void jButton_SendDataSingUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SendDataSingUpActionPerformed

    }//GEN-LAST:event_jButton_SendDataSingUpActionPerformed

    private void jTextField_UsernameSingUpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_UsernameSingUpFocusGained
        if(jTextField_UsernameSingUp.getForeground().equals(Color.red)){
            jTextField_UsernameSingUp.setForeground(Color.BLACK);
            jTextField_UsernameSingUp.setText("");
        }
    }//GEN-LAST:event_jTextField_UsernameSingUpFocusGained

    private void jTextField_PasswordSingUpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_PasswordSingUpFocusGained
        if(jTextField_PasswordSingUp.getForeground().equals(Color.red)){
            jTextField_PasswordSingUp.setForeground(Color.BLACK);
            jTextField_PasswordSingUp.setText("");
        }
    }//GEN-LAST:event_jTextField_PasswordSingUpFocusGained

    private void jTextField_RepeatPasswordSingUpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_RepeatPasswordSingUpFocusGained
        if(jTextField_RepeatPasswordSingUp.getForeground().equals(Color.red)){
            jTextField_RepeatPasswordSingUp.setForeground(Color.BLACK);
            jTextField_RepeatPasswordSingUp.setText("");
        }
    }//GEN-LAST:event_jTextField_RepeatPasswordSingUpFocusGained

    private void jTextField_NameSingUpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_NameSingUpFocusGained
        if(jTextField_NameSingUp.getForeground().equals(Color.red)){
            jTextField_NameSingUp.setForeground(Color.BLACK);
            jTextField_NameSingUp.setText("");
        }     
    }//GEN-LAST:event_jTextField_NameSingUpFocusGained

    private void jTextField_FirstSurnameSingUpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_FirstSurnameSingUpFocusGained
        if(jTextField_FirstSurnameSingUp.getForeground().equals(Color.red)){
            jTextField_FirstSurnameSingUp.setForeground(Color.BLACK);
            jTextField_FirstSurnameSingUp.setText("");
        }
    }//GEN-LAST:event_jTextField_FirstSurnameSingUpFocusGained

    private void jTextField_SecondSurnameSingUpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_SecondSurnameSingUpFocusGained
        if(jTextField_SecondSurnameSingUp.getForeground().equals(Color.red)){
            jTextField_SecondSurnameSingUp.setForeground(Color.BLACK);
            jTextField_SecondSurnameSingUp.setText("");
        }

    }//GEN-LAST:event_jTextField_SecondSurnameSingUpFocusGained

    private void jLabelNewUserCreatedSuccessfullyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabelNewUserCreatedSuccessfullyFocusLost

        jLabelNewUserCreatedSuccessfully.setText("");
    }//GEN-LAST:event_jLabelNewUserCreatedSuccessfullyFocusLost

    private void jTextFieldSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldSearchFocusGained
        if(jTextFieldSearch.getText().equals("Search")){
        
            jTextFieldSearch.setText("");
            jTextFieldSearch.setForeground(Color.black);
        }
    }//GEN-LAST:event_jTextFieldSearchFocusGained

    private void jTextFieldSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldSearchFocusLost
        if(jTextFieldSearch.getText().equals("")){
        
            jTextFieldSearch.setText("Search");
            jTextFieldSearch.setForeground(Color.LIGHT_GRAY);
        }
    }//GEN-LAST:event_jTextFieldSearchFocusLost

    private void jListFriendsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListFriendsMouseClicked

        this.loginFriend = this.friends.get(this.jListFriends.getSelectedIndex()).getLogin();
        System.out.println("(Debug) Click on:" + loginFriend);
        if(this.jListFriends.getSelectedIndex() >= 0){

            this.clickFriendThread = new ClickFriend_thread(userLogin.getLogin(), loginFriend, this, userController, messageController, cliente);
            this.clickFriendThread.start();
            
        }
    }//GEN-LAST:event_jListFriendsMouseClicked
    
    private void jLabelFriendNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelFriendNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelFriendNameMouseClicked

    private void jButtonSendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendMessageActionPerformed

        String getNewMessage = this.jTextArea_newMessage.getText();
        
        if(getNewMessage.length() > 0 && getNewMessage.length() <= 1000){
            String newMessage = this.userLogin.getLogin() + " (" + this.cliente.getActualDate()+ ") _ \t" + getNewMessage + "\t☐\n";
            this.jTextArea_chat.append(newMessage);
        
            try{
                this.cliente.sendMessage(this.userLogin.getLogin(), loginFriend, getNewMessage);
            }catch(Exception e){
            }
            
            
            this.jTextArea_newMessage.setText("");
        }        
        
    }//GEN-LAST:event_jButtonSendMessageActionPerformed

    private void printMissingDataSingUp(){
            
        if(jTextField_UsernameSingUp.getText().equals("") || jTextField_UsernameSingUp.getForeground().equals(Color.red)){
            jTextField_UsernameSingUp.setForeground(Color.red);
            jTextField_UsernameSingUp.setText("Empty clause not available");
            
        }
        if (jTextField_PasswordSingUp.getText().equals("") || jTextField_PasswordSingUp.getForeground().equals(Color.red)){
            jTextField_PasswordSingUp.setForeground(Color.red);
            jTextField_PasswordSingUp.setText("Empty clause not available");
            
        }
        if (jTextField_RepeatPasswordSingUp.getText().equals("") || jTextField_RepeatPasswordSingUp.getForeground().equals(Color.red)){
            jTextField_RepeatPasswordSingUp.setForeground(Color.red);
            jTextField_RepeatPasswordSingUp.setText("Empty clause not available");
            
        }
        if (jTextField_NameSingUp.getText().equals("") || jTextField_NameSingUp.getForeground().equals(Color.red)){
            jTextField_NameSingUp.setForeground(Color.red);
            jTextField_NameSingUp.setText("Empty clause not available");
            
        }
        if (jTextField_FirstSurnameSingUp.getText().equals("") || jTextField_FirstSurnameSingUp.getForeground().equals(Color.red)){
            jTextField_FirstSurnameSingUp.setForeground(Color.red);
            jTextField_FirstSurnameSingUp.setText("Empty clause not available");
            
        }
        if (jTextField_SecondSurnameSingUp.getText().equals("") || jTextField_SecondSurnameSingUp.getForeground().equals(Color.red)){
            jTextField_SecondSurnameSingUp.setForeground(Color.red);
            jTextField_SecondSurnameSingUp.setText("Empty clause not available");
            
        }
    }
    
    private void printUnrepeatedPasswordSingUp() {
        
        jTextField_RepeatPasswordSingUp.setForeground(Color.red);
        jTextField_RepeatPasswordSingUp.setText("Wrong password");        
    }
    
    private void printRepeatedLogin() {
        
        jTextField_UsernameSingUp.setForeground(Color.red);
        jTextField_UsernameSingUp.setText("This Username has already exist");  
    }
    
    private void printNewUserCreatedSuccessfully() {
    
        jLabelNewUserCreatedSuccessfully.setText("The new user was created successfully");
        jTextField_UsernameSingUp.setText("");
        jTextField_PasswordSingUp.setText("");
        jTextField_RepeatPasswordSingUp.setText("");
        jTextField_NameSingUp.setText("");
        jTextField_FirstSurnameSingUp.setText("");
        jTextField_SecondSurnameSingUp.setText("");
    }

    public static void addDebugStringToDebugSection(String debug){
    
        debugJTextArea.append(debug);
    }
    
    
    public static void main(String args[]) {
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextArea debugJTextArea;
    private javax.swing.JButton jButtonSendMessage;
    private javax.swing.JButton jButton_SendDataSingUp;
    private javax.swing.JFrame jFrame1;
    public javax.swing.JLabel jLabelFriendName;
    public javax.swing.JLabel jLabelFriendPicture;
    private javax.swing.JLabel jLabelLogoImage;
    private javax.swing.JLabel jLabelNameUser;
    private javax.swing.JLabel jLabelNewUserCreatedSuccessfully;
    private javax.swing.JLabel jLabelUserImage;
    private javax.swing.JLabel jLabel_FirstSurnameSingUp;
    private javax.swing.JLabel jLabel_NameSingUp;
    private javax.swing.JLabel jLabel_PasswordSingUp;
    private javax.swing.JLabel jLabel_RepeatPasswordSingUp;
    private javax.swing.JLabel jLabel_SecondSurnameSingUp;
    private javax.swing.JLabel jLabel_UsernameSingUp;
    public javax.swing.JList<String> jListFriends;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelActualUserFriends;
    private javax.swing.JPanel jPanelActualUserInformation;
    private javax.swing.JPanel jPanelChat;
    private javax.swing.JPanel jPanelChats;
    private javax.swing.JPanel jPanelDebug;
    private javax.swing.JPanel jPanelFriendInformation;
    private javax.swing.JPanel jPanelFriendsChat;
    private javax.swing.JPanel jPanelSearch;
    private javax.swing.JPanel jPanelSendMessage;
    private javax.swing.JPanel jPanelSingUp;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneFriendList;
    private javax.swing.JScrollPane jScrollPaneTextMessage;
    private javax.swing.JScrollPane jScrollPanelChat;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTextArea jTextArea_chat;
    public javax.swing.JTextArea jTextArea_newMessage;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextField_FirstSurnameSingUp;
    private javax.swing.JTextField jTextField_NameSingUp;
    private javax.swing.JTextField jTextField_PasswordSingUp;
    private javax.swing.JTextField jTextField_RepeatPasswordSingUp;
    private javax.swing.JTextField jTextField_SecondSurnameSingUp;
    private javax.swing.JTextField jTextField_UsernameSingUp;
    // End of variables declaration//GEN-END:variables
}
