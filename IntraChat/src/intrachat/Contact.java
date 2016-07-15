/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intrachat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author gabi.adotevi
 */

public class Contact {
    private String name;
    private String port;
    private String ipAddress;
    private String mobilePhone;

    public Contact() {
        this.name = "";
        this.port = "";
        this.ipAddress = "";
        this.mobilePhone = "";
    }

    public Contact(String name, 
            String port, 
            String ipAddress,
            String mobilePhone) {
        this.name = name;
        this.port = port;
        this.ipAddress = ipAddress;
        this.mobilePhone = mobilePhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    public static Contact findContactByName(ArrayList<Contact> contacts, String name) {
        for (Contact contact : contacts) {
            if (contact.getName().trim().equals(name.trim())) {
                return contact;
            }
        }

        return null;
    }

    public static Contact findContactByIpAddress(ArrayList<Contact> contacts, String ipAddress) {
        for (Contact contact : contacts) {
            if (contact.getIpAddress().trim().equals(ipAddress.trim())) {
                return contact;
            }
        }

        return null;
    }
    
    public static String getMyIpAddress() {
        String ipAddress;

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ipAddress = inetAddress.getHostAddress();

            return ipAddress;

        } catch (UnknownHostException ex) {
            System.out.println(ex);
        }

        return null;
    }
    
    
    
}
