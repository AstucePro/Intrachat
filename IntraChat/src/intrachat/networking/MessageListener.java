/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intrachat.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import intrachat.Contact;
import intrachat.gui.ChatScreen;

/**
 *
 * @author gabi.adotevi
 */

public class MessageListener extends Thread {

    private ServerSocket server;
    private int port;
    private WritableGUI gui;
    // Chat screens
    private ArrayList<ChatScreen> chatScreens;
    // Contacts lists
    private ArrayList<Contact> contacts;
    private DefaultListModel messagesList;

    public MessageListener(WritableGUI gui, int port) {
        this.port = port;
        this.gui = gui;
        try {
            server = new ServerSocket(port);
            gui.writeStatus("Chat server Listening on port " + port);
        } catch (IOException ex) {
            gui.writeStatus("IO Exception: Could not listen on port: " + port);
        }
    }

    public MessageListener(int port) {
        this.port = port;

        try {
            server = new ServerSocket(port);
            System.out.println("Chat server Listening on port " + port);
        } catch (IOException ex) {
            System.out.println("Could not listen on port: " + port);
        }
    }

    public MessageListener() {
        try {
            server = new ServerSocket(port);
            System.out.println("Listening...");
        } catch (IOException ex) {
            System.out.println("Could not listen on port: " + port);
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        Socket clientSocket;
        try {
            while ((clientSocket = server.accept()) != null) {
                // Where is message coming from
                System.out.println("Message from: " + clientSocket.getInetAddress().getHostAddress());

                InputStream is = clientSocket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = br.readLine();
                if (line != null) {
                    
                    ChatScreen chatScreen = ChatScreen.findChatScreenByReceiverContactIpAddress(
                            chatScreens,
                            clientSocket.getInetAddress().getHostAddress());
                    if (chatScreen != null) {
                        chatScreen.setVisible(true);
                        chatScreen.write(line);
                        messagesList.addElement(line);
                    } else {
                        // Create new chat screen and display it since none was found
                        Contact receiverContact = 
                                Contact.findContactByIpAddress(contacts, 
                                        clientSocket.getInetAddress().getHostAddress());
                        Contact myContact = Contact.findContactByIpAddress(contacts, Contact.getMyIpAddress());

                        chatScreen = ChatScreen.createChatScreen(receiverContact, myContact);
                        chatScreen.write(line);
                        messagesList.addElement(line);
                        chatScreens.add(chatScreen);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<ChatScreen> getChatScreens() {
        return chatScreens;
    }

    public void setChatScreens(ArrayList<ChatScreen> chatScreens) {
        this.chatScreens = chatScreens;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public DefaultListModel getMessagesList() {
        return messagesList;
    }

    public void setMessageslist(DefaultListModel messagesList) {
        this.messagesList = messagesList;
    }

}
