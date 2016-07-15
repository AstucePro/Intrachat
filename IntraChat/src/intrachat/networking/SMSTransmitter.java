/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intrachat.networking;

import CBFSMS.SMSClientException;
import java.util.logging.Level;
import java.util.logging.Logger;
import intrachat.Contact;

/**
 *
 * @author gabi.adotevi
 */

public class SMSTransmitter extends Thread {

    private final Contact receiverContact;
    private final Contact senderContact;
    private final String message;
    private final SMSMessaging sms;
    private final WritableGUI gui;

    public SMSTransmitter(SMSMessaging sms, 
            Contact senderContact, 
            Contact receiverContact,
            String message,
            WritableGUI gui) {
        this.sms = sms;
        this.gui = gui;
        this.senderContact = senderContact;
        this.receiverContact = receiverContact;   
        this.message = message;
    }

    @Override
    public void run() {
        try {
            sms.sendSMS(senderContact.getMobilePhone(),
                    new String[]{receiverContact.getMobilePhone()},
                    message,
                    "MyChatApp");
            gui.write("Vous (SMS): " + message);
        } catch (SMSClientException ex) {
            gui.write("Error : SMS message could not be sent; " + ex.getLocalizedMessage());
        }
                
    }

}
