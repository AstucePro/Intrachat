/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intrachat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import intrachat.gui.MainScreen;

/**
 *
 * @author gabi.adotevi
 */
public class IntraChat {

    private static MainScreen mainScreen;

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        // MainScreen screen = new MainScreen();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    System.out.println(e);
                }

                try {
                    mainScreen = new MainScreen();
                    mainScreen.setLocationRelativeTo(null);
                    mainScreen.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(IntraChat.class.getName()).log(Level.SEVERE, null, ex);
                }
                // Centre screen

            }
        });

    }
}
