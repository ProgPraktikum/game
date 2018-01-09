package GUI;


import data.DataContainer;
import network.Network;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class selectNetwork {

    JDialog nw;
    JLabel displayIp;
    JCheckBox isHost;
    JCheckBox isClient;


    public selectNetwork() throws SocketException {

        /**
         * Neuer JDialog wird erstellt und auf Modal gesetzt. Des Weiteren wird undecorated gesetzt.
         */
        nw = new JDialog();
        nw.setModal(true);
        nw.setUndecorated(true);
        nw.setBackground(Color.BLACK);
        nw.setPreferredSize(new Dimension(400, 400));
        nw.setContentPane(Box.createVerticalBox());

        /**
         * Boxn um die JCheckBox'n und JButtons und JTextField aufzunehmen.
         */
        Box horizontalBox = Box.createHorizontalBox();
        Box horizontalBox1 = Box.createHorizontalBox();
        Box tf = Box.createHorizontalBox();

        /**
         * JCheckBox für isHost
         */
        isHost = new JCheckBox("Host");
        isHost.setAlignmentX(Component.LEFT_ALIGNMENT);
        isHost.setBackground(Color.BLACK);
        isHost.setForeground(Color.WHITE);
        isHost.addActionListener(
                (e) -> {

                    if (isHost.isSelected() == true) {
                        isClient.setSelected(false);
                    }
                }
        );


        /**
         * JCheckBox für isClient
         */
        isClient = new JCheckBox("Client");
        isClient.setAlignmentX(Component.LEFT_ALIGNMENT);
        isClient.setBackground(Color.BLACK);
        isClient.setForeground(Color.WHITE);
        isClient.addActionListener(
                (e) -> {

                    if (isClient.isSelected() == true) {
                        isHost.setSelected(false);
                    }
                }
        );


        /**
         * JTextField für die Eingabe einer IP
         */
        JTextField field = new JTextField("IP des Host eingeben");
        field.setMaximumSize(new Dimension(200, 30));
        field.setMinimumSize(new Dimension(200, 30));
        field.setPreferredSize(new Dimension(200, 30));

        /**
         * eigene IP
         */

        String myIp = null;
        System.out.print("My IP address(es):");

        Enumeration nis =  NetworkInterface.getNetworkInterfaces();
        while (nis.hasMoreElements()) {
            NetworkInterface ni =(NetworkInterface) nis.nextElement();
            Enumeration ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress ia = (InetAddress)ias.nextElement();
                if (!ia.isLoopbackAddress()) {
                   System.out.println(" " + ia.getHostAddress());
                    myIp = ia.getHostAddress();
                }
            }

        }


        /**
         * DisplayIp zeigt die eigene Ip Adresse wenn man hosted. wird nur für die
         * Clientverindung benötigt
         */
        displayIp = new JLabel("My IP: "+ myIp);
        displayIp.setForeground(Color.WHITE);


        /**
         * OK Button, der bei Benutzung das Fenster schließt und falls man Host ist
         * select FieldSize öffnet.
         * Ist man Client wird das PlaceShips View geöffnet.
         */
        JButton ok = new JButton("OK");
        ok.setBackground(Color.BLACK);
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("Tahoma", Font.PLAIN, 20));
        ok.addActionListener(
                (e) -> {
                    if(isHost.isSelected()){
                        DataContainer.setIsHost(true);
                    }else{
                        DataContainer.setIsClient(true);
                    }

                    if(isClient.isSelected()){
                        if(field.getText() != null)
                        DataContainer.setNetworkIP(field.getText());
                        System.out.println("dc " +DataContainer.getNetworkIP());
                        System.out.println("tf " +field.getText());
                    }

                    nw.setVisible(false);
                    if(DataContainer.getIsHost()){
                        /**
                         * Aufbau HostVerindung
                         */
                        Network.createHostConnection();
                        new SelectFieldSize();
                    }else{
                        /**
                         * TODO vor Aufruf new PlaceShip() muss der stack ShipLengths im DataContainer
                         * TODO befüllt werden. Die notwendigen Daten müsen von dem Host übermittelt werden.
                         */

                        /**
                         * Aufbau einer ClientConnection
                         */
                        Network.createClientConnection(DataContainer.getNetworkIP());
                        /**
                         * Empfangen der übermittelten StartDaten
                         */
                        Network.recieveStartData();
                      //  new PlaceShips();
                    }
                }
        );

        /**
         * abbrechen Button (schließt den JDialog)
         */
        JButton abbrechen = new JButton("abbrechen");
        abbrechen.setBackground(Color.BLACK);
        abbrechen.setForeground(Color.WHITE);
        abbrechen.setFont(new Font("Tahoma", Font.PLAIN, 20));
        abbrechen.addActionListener(
                (e) -> {
                    nw.dispose(); }
        );

        /**
         * Box welche die JCheckBox isHost und isClient aufnimmt
         */
        horizontalBox1.add(Box.createHorizontalStrut(10));
        horizontalBox1.add(isHost);
        horizontalBox1.add(Box.createHorizontalStrut(10));
        horizontalBox1.add(isClient);

        /**
         * Box welche die JButtons OK und abbrechen aufnimmt
         */
        horizontalBox.add(Box.createHorizontalStrut(20));
        horizontalBox.add(ok);
        horizontalBox.add(abbrechen);

        /**
         * Box welche das JTextField aufnimmt
         */
        tf.add(field);

        nw.add(horizontalBox1);
        nw.add(tf);
        nw.add(displayIp);
        nw.add(horizontalBox);
        nw.pack();
        nw.setLocationRelativeTo(null);
        nw.setVisible(true);

    }


}
