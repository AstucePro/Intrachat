/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intrachat.networking;

import CBFSMS.*;

/**
 *
 * @author gabi.adotevi
 */
public class SMSMessaging {

    private static SMSMessaging instance = null;

    private SMSMessaging(String username, String password) throws SMSClientException {
        SendSMS.initialise(username, password);
        SendSMS.setURL("http://sms2.cardboardfish.com:9001/HTTPSMS");
    }

    public static SMSMessaging getInstance(String username, String password) {
        if (instance == null) {
            try {
                instance = new SMSMessaging(username, password);
            } catch (SMSClientException ex) {
                System.out.println(ex);                
                return null;
            }
        }
        
        return instance;
    }

    public String sendSMS(
            String source,
            String[] destination,
            String message,
            String reference) throws SMSClientException {
        String response = "";

        /* Construct an SMSMessaging object */
        SMS sms = new SMS();

        /* Set the destination address */
        sms.setDestination(destination);

        /* Set the source address */
        sms.setSourceAddr(source);

        /* Set the user reference */
        sms.setUserReference(reference);

        /* Set delivery receipts to 'on' */
        sms.setDR("1");

        /* Set the message content */
        sms.setMessage(message);

        int[] responses = SendSMS.sendSMS(sms);

        for (int i = 0; i < responses.length; i++) {
            //System.out.println("\t" + responses[i]);
            switch (responses[i]) {
                case SendSMS.INVALID_DEST:
                    response = response + " " + "Phone number is invalid";
                    break;
                case SendSMS.RETRIED_AND_FAILED:
                    response = response + " " + "The message could not be sent";
                    break;
                default:
                    response = response + " " + "The message was be sent";
                    break;
            }
        }

        return response;
    }

    public static void main(String[] args) {
        try {
            SMSMessaging e = new SMSMessaging("gabikevin", "intrachat2016");
            String source = "18163288652";
            String destination = "18162779015";

            e.sendSMS(source, new String[]{destination}, "Hello", "MyChatApp");
        } catch (SMSClientException e) {
            System.err.println(e.getMessage());
        }
    }

}
