package intrachat.gui;

import CBFSMS.SMSClientException;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import intrachat.Contact;
import intrachat.networking.MessageTransmitter;
import intrachat.networking.SMSMessaging;
import intrachat.networking.SMSTransmitter;
import intrachat.networking.WritableGUI;

public class ChatScreen extends javax.swing.JFrame implements WritableGUI {

    private Contact receiverContact;
    private Contact senderContact;

    // Creates new form MainScreen
    public ChatScreen() throws IOException {
        initComponents();
    }

    public Contact getSenderContact() {
        return senderContact;
    }

    public void setSenderContact(Contact senderContact) {
        this.senderContact = senderContact;
    }

    public Contact getReceiverContact() {
        return receiverContact;
    }

    public void setReceiverContact(Contact receiverContact) {
        this.receiverContact = receiverContact;
    }

    //Do Not modify what follows
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        messagePanel = new javax.swing.JPanel();
        sendButton = new javax.swing.JButton();
        SMSButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        message = new javax.swing.JTextArea();
        chatViewPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        chatView = new javax.swing.JTextPane();

        setTitle("Local Chat System");

        messagePanel.setPreferredSize(new java.awt.Dimension(651, 50));
        messagePanel.setLayout(new java.awt.BorderLayout());

        sendButton.setText("Send");
        sendButton.setToolTipText("Send chat message");
        sendButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        messagePanel.add(sendButton, java.awt.BorderLayout.EAST);

        SMSButton.setText("SMS");
        SMSButton.setToolTipText("Send SMS message");
        SMSButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SMSButtonMouseClicked(evt);
            }
        });
        messagePanel.add(SMSButton, java.awt.BorderLayout.WEST);

        message.setColumns(20);
        message.setRows(5);
        jScrollPane1.setViewportView(message);

        messagePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(messagePanel, java.awt.BorderLayout.SOUTH);

        chatViewPanel.setPreferredSize(new java.awt.Dimension(651, 300));
        chatViewPanel.setLayout(new java.awt.BorderLayout());

        chatView.setEditable(false);
        chatView.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(chatView);

        chatViewPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(chatViewPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //################################################################ Methode de formatage de texte
    private void appendText(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setEditable(true);
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
        tp.setEditable(false);
    }

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed

        int port = Integer.parseInt(receiverContact.getPort());
        MessageTransmitter transmitter
                = new MessageTransmitter(
                        senderContact.getName() + " : " + message.getText(),
                        receiverContact.getIpAddress(),
                        port, this);

        transmitter.start();

        writeSend("Vous : " + message.getText());
        message.setText(null);
    }//GEN-LAST:event_sendButtonActionPerformed

    private void SMSButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SMSButtonMouseClicked
        // NB: username and password should be obtained from a config file
        SMSMessaging sms = SMSMessaging.getInstance("desbenn1", "6g2nA1jW");
        if (sms != null) {
            writeSend("Sending SMS...");
            SMSTransmitter smsTransmitter
                    = new SMSTransmitter(sms, senderContact, receiverContact, message.getText(), this);
            smsTransmitter.start();
        } else {
            writeSend("Error : SMS messaging could not be initialized");
        }
    }//GEN-LAST:event_SMSButtonMouseClicked

    public static void main(String args[]) {


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new ChatScreen().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ChatScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SMSButton;
    private javax.swing.JTextPane chatView;
    private javax.swing.JPanel chatViewPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea message;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void write(String s) {
        // chatView.append(s + System.lineSeparator());        
        appendText(chatView, s + System.lineSeparator(), Color.darkGray);
    }

    @Override
    public void writePopUp(String s) {
        if (this.isShowing()) {
            write(s);
        } else {
            write(s);
            ImageIcon img = new ImageIcon("images/information.png");
            JOptionPane.showMessageDialog(null, s, "Message reçu !", JOptionPane.INFORMATION_MESSAGE, img);
        }

    }

    public void writeSend(String s) {
        // chatView.append(s + System.lineSeparator());
        appendText(chatView, s + System.lineSeparator(), Color.BLUE);
    }

    @Override
    public void writeStatus(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static ChatScreen findChatScreenByReceiverContactName(ArrayList<ChatScreen> chatScreens, String contactName) {
        for (ChatScreen chatScreen : chatScreens) {
            if (chatScreen.getReceiverContact().getName().equals(contactName)) {
                return chatScreen;
            }
        }

        return null;
    }

    public static ChatScreen findChatScreenByReceiverContactIpAddress(ArrayList<ChatScreen> chatScreens, String ipAddress) {
        for (ChatScreen chatScreen : chatScreens) {
            if (chatScreen.getReceiverContact().getIpAddress().equals(ipAddress)) {
                return chatScreen;
            }
        }

        return null;
    }

    public static ChatScreen findChatScreenBySenderContactName(ArrayList<ChatScreen> chatScreens, String contactName) {
        for (ChatScreen chatScreen : chatScreens) {
            if (chatScreen.getSenderContact().getName().equals(contactName)) {
                return chatScreen;
            }
        }

        return null;
    }

    public static ChatScreen findChatScreenBySenderContactIpAddress(ArrayList<ChatScreen> chatScreens, String ipAddress) {
        for (ChatScreen chatScreen : chatScreens) {
            if (chatScreen.getSenderContact().getIpAddress().equals(ipAddress)) {
                return chatScreen;
            }
        }

        return null;
    }

    public static ChatScreen createChatScreen(Contact receiverContact,
            Contact senderContact) {
        ChatScreen chatScreen;

        try {
            System.out.println("Creating new screen");
            chatScreen = new ChatScreen();
            chatScreen.setReceiverContact(receiverContact);
            chatScreen.setSenderContact(senderContact);
            chatScreen.setTitle("You are chatting with " + receiverContact.getName());
            chatScreen.setLocationRelativeTo(null);
            chatScreen.setVisible(true);

            return chatScreen;
            //messageslistModel.addElement(contactName);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return null;
    }

}
