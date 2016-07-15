/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intrachat.networking;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author gabi.adotevi
 */

public class MessageTransmitter extends Thread {

    private final String message, hostname;
    private final int port;
    private final WritableGUI gui;
    
    public MessageTransmitter(String message, String hostname, int port, WritableGUI gui) {
        this.message = message;
        this.hostname = hostname;
        this.port = port;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            System.out.println("Message Sent!");
            Socket s = new Socket(hostname, port); 
            s.getOutputStream().write(message.getBytes());
            s.close();
        } catch (IOException ex) {            
            gui.write("Could not send message to: " + hostname + " on port: " + port);         
        }
    }
}
